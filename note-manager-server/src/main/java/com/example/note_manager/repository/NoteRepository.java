package com.example.note_manager.repository;

import com.example.note_manager.model.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ResourceInUseException;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbWaiter;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class NoteRepository {

    private static final Logger logger = LoggerFactory.getLogger(NoteRepository.class);
    private final DynamoDbTable<Note> noteTable;
    private final DynamoDbClient dynamoDbClient;

    @Autowired
    public NoteRepository(DynamoDbEnhancedClient enhancedClient, DynamoDbClient dynamoDbClient) {
        this.noteTable = enhancedClient.table("Notes", TableSchema.fromBean(Note.class));
        this.dynamoDbClient = dynamoDbClient;
    }

    @PostConstruct
    public void initializeTable() {
        try {
            // Try to describe the table to check if it exists
            noteTable.describeTable();
            logger.info("Table 'Notes' already exists and is accessible");
        } catch (ResourceNotFoundException e) {
            // Table doesn't exist, create it
            logger.info("Table 'Notes' not found. Creating table...");
            createTable();
        } catch (Exception e) {
            logger.error("Error checking table existence: {}", e.getMessage());
            throw new RuntimeException("Failed to initialize DynamoDB table", e);
        }
    }

    public Note save(Note note) {
        noteTable.putItem(note);
        return note;
    }

    public List<Note> findAll() {
        return noteTable.scan()
                .items()
                .stream()
                .collect(Collectors.toList());
    }

    private void createTable() {
        try {
            noteTable.createTable(builder -> builder
                    .provisionedThroughput(b -> b
                            .readCapacityUnits(5L)
                            .writeCapacityUnits(5L)
                            .build())
            );

            // Wait for table to become active using DynamoDbWaiter
            try (DynamoDbWaiter waiter = DynamoDbWaiter.create()) {
                waiter.waitUntilTableExists(builder -> builder.tableName("Notes"));
            }
            
            logger.info("Table 'Notes' created successfully");

        } catch (ResourceInUseException e) {
            // Table already exists (race condition)
            logger.info("Table 'Notes' already exists (created by another process)");
        } catch (Exception e) {
            logger.error("Failed to create table 'Notes': {}", e.getMessage());
            throw new RuntimeException("Could not create DynamoDB table", e);
        }
    }
}
