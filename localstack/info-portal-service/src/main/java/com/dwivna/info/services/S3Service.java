package com.dwivna.info.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface S3Service {
    void uploadFile(MultipartFile file) throws IOException;

    byte[] downloadFile(String fileName) throws IOException;

    void deleteFile(String fileName);

    List<String> listFiles();
}
