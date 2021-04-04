package com.spring.hrmanagement.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Configuration
@EnableConfigurationProperties(AwsS3Config.class)
@ConfigurationProperties(prefix = "s3")
public class AwsS3Config {

    @NotNull
    private String accessKeyId;
    @NotNull
    private String secretKey;
    @NotNull
    private String region;

    /*final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKeyId, secretKey);

    @Bean
    public AmazonS3 getAmazonS3Client() {
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .build();
    }*/
}
