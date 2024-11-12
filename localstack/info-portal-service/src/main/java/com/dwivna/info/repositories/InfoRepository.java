package com.dwivna.info.repositories;

import com.dwivna.info.models.Info;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Repository
public class InfoRepository {

    private final DynamoDbTable<Info> table;

    public InfoRepository(DynamoDbClient dynamoDbClient) {
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
        this.table = enhancedClient.table("info_table", TableSchema.fromBean(Info.class));
    }

    public Info save(Info info) {
        table.putItem(info);
        return info;
    }

    public Info findByIdAndSortKey(String id, String service) {
        return table.getItem(r -> r.key(k -> k.partitionValue(id).sortValue(service)));
    }
}
