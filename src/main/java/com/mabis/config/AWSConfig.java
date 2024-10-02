package com.mabis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AWSConfig
{
    @Bean
    public S3Client s3()
    {
        return S3Client.builder().region(Region.SA_EAST_1).build();
    }
}
