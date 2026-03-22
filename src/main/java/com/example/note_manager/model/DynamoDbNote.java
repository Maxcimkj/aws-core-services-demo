package com.example.note_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class DynamoDbNote {
    private UUID id;
    private String creatorName;
    private String creatorEmail;
    private String content;
    private LocalDateTime created_at;

    @DynamoDbPartitionKey
    public UUID getId() {
        return id;
    }
}
