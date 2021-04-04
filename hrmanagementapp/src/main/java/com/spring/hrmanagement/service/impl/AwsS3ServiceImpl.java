package com.spring.hrmanagement.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.spring.hrmanagement.service.AwsS3Service;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AwsS3ServiceImpl implements AwsS3Service {

    static final Logger log = LoggerFactory.getLogger(AwsS3ServiceImpl.class);

    AmazonS3 amazonS3;

    @Value("${s3.bucketName}")
    String bucketName;

    @Value("${s3.endpointUrl}")
    String endpointUrl;

    @Value("${s3.accessKeyId}")
    private String accessKeyId;

    @Value("${s3.secretKey}")
    private String secretKey;

    @Value("${s3.region}")
    private String region;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKeyId, this.secretKey);
        this.amazonS3 = AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    @Override
    public String uploadFile(MultipartFile multipartFile) {

        log.info("File upload in progress");
        
        try {
            String fileUrl = "";
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileToS3Bucket(fileName, file);
            file.delete();
            return fileUrl;
        } catch(AmazonServiceException ex) {
            throw new AmazonServiceException("error occured when uploading file to amazon s3");
        }
    }

    @Override
    public File convertMultiPartToFile(MultipartFile file) {
       try {
           File convFile = new File(file.getOriginalFilename());
           FileOutputStream fos = new FileOutputStream(convFile);
           fos.write(file.getBytes());
           fos.close();
           return convFile;
       } catch(IOException ex) {
            log.info("Error converting the multi-part file to file= {}",ex.getMessage());
       }

       return null;
    }

    @Override
    public void uploadFileToS3Bucket(String fileName, File file) {
        amazonS3.putObject(bucketName, fileName, file);
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }
}
