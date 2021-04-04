package com.spring.hrmanagement.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface AwsS3Service {

    String uploadFile(final MultipartFile multipartFile);

    File convertMultiPartToFile(final MultipartFile multipartFile);

    void uploadFileToS3Bucket(final String bucketName, final File file);
}
