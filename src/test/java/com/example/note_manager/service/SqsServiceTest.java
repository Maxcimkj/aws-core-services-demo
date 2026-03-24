package com.example.note_manager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SqsServiceTest {

    @Mock
    private SqsClient sqsClient;

    private SqsService sqsService;
    private ObjectMapper objectMapper;
    private final String queueUrl = "https://test-queue-url";

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        sqsService = new SqsService(sqsClient, objectMapper, queueUrl);
    }

    @Test
    void testSendNoteNotification_SendsCorrectJson() throws Exception {
        // Given
        Map<String, String> note = Map.of("content", "test content", "creator", "tester");
        String expectedJson = objectMapper.writeValueAsString(note);

        // When
        sqsService.sendNoteNotification(note);

        // Then
        ArgumentCaptor<SendMessageRequest> requestCaptor = ArgumentCaptor.forClass(SendMessageRequest.class);
        verify(sqsClient).sendMessage(requestCaptor.capture());

        SendMessageRequest capturedRequest = requestCaptor.getValue();
        assertEquals(queueUrl, capturedRequest.queueUrl());
        assertEquals(expectedJson, capturedRequest.messageBody());
    }
}
