package com.dwivna.info.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

@Configuration
public class DynamoDbConfig {

    @Value("${aws.dynamodb.endpoint}")
    private String endpoint;

    @Value("${aws.region}")
    private String region;

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create("access-key", "secret-key")))
                .region(Region.of(region))
                .build();
    }

}
