package com.dwivna.info.controllers;


import com.dwivna.info.services.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    private final S3Service s3Service;

    @Autowired
    public FileController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @Operation(
            summary = "Upload a file to S3",
            description = "Upload a file to the configured S3 bucket on LocalStack."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid file format")
    })
    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        s3Service.uploadFile(file);
        return "File uploaded successfully!";
    }

    @Operation(
            summary = "Download a file from S3",
            description = "Download a file from the configured S3 bucket on LocalStack."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File downloaded successfully"),
            @ApiResponse(responseCode = "404", description = "File not found")
    })
    @GetMapping(value = "/download/{fileName}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) throws IOException {
        byte[] data = s3Service.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                .body(resource);
    }

    @Operation(
            summary = "List all files in S3",
            description = "Retrieve a list of all files stored in the configured S3 bucket on LocalStack."
    )
    @ApiResponse(responseCode = "200", description = "List of files retrieved successfully")
    @GetMapping("/list")
    public List<String> listFiles() {
        return s3Service.listFiles();
    }

    @Operation(
            summary = "Delete a file from S3",
            description = "Delete a file from the configured S3 bucket on LocalStack."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File deleted successfully"),
            @ApiResponse(responseCode = "404", description = "File not found")
    })
    @DeleteMapping("/delete/{fileName}")
    public String deleteFile(@PathVariable String fileName) {
        s3Service.deleteFile(fileName);
        return "File deleted successfully!";
    }
}
