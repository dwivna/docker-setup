package com.dwivna.info.services.impl;

import com.dwivna.info.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3ServiceImpl implements S3Service {

    private final S3Client s3Client;
    private final String bucketName = "dwivna-info-bucket";

    @Autowired
    public S3ServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
        createBucketIfNotExists();
    }

    private void createBucketIfNotExists() {
        try {
            s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());
        } catch (BucketAlreadyOwnedByYouException e) {
            // Bucket already exists, no need to create
        }
    }

    // Upload file to S3
    public void uploadFile(MultipartFile file) throws IOException {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(file.getOriginalFilename())
                .build();

        try (InputStream inputStream = file.getInputStream()) {
            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));
        }
    }

    // Download file from S3
    public byte[] downloadFile(String key) throws IOException {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        Path path = Path.of("/tmp/" + key);

        // Get the file from S3
        GetObjectResponse response = s3Client.getObject(getObjectRequest, path);

        // Read the file content
        byte[] data = Files.readAllBytes(path);

        // Clean up temporary file
        Files.delete(path);

        return data;
    }

    // List files in the bucket
    public List<String> listFiles() {
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(listObjectsV2Request);
        return response.contents().stream()
                .map(S3Object::key)
                .collect(Collectors.toList());
    }

    // Delete file from S3
    public void deleteFile(String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }
}
