package com.example.note_manager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ImportAutoConfiguration(exclude = {
        OAuth2ResourceServerAutoConfiguration.class
})
@AutoConfigureMockMvc
@Import(TestConfig.class)
@ActiveProfiles("test")
class NoteManagerApplicationTests {

    @MockitoBean
    private DynamoDbClient dynamoDbClient;

    @MockitoBean
    private DynamoDbEnhancedClient dynamoDbEnhancedClient;

    @MockitoBean
    private com.example.note_manager.repository.DynamoDbNoteRepository dynamoDbNoteRepository;

    @MockitoBean
    private org.springframework.security.web.SecurityFilterChain securityFilterChain;

    @Test
    void contextLoads() {
    }
}
