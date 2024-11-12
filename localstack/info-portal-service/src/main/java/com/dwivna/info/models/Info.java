package com.dwivna.info.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Data
@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor
public class Info {

    private String id;
    private String service;
    private String domain;

    @DynamoDbSortKey
    @DynamoDbAttribute(value = "service")
    public String getService() {
        return service;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute(value = "id")
    public String getId() {
        return id;
    }
}
