package com.example.note_manager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
@Slf4j
public class SqsService {

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;
    private final String queueUrl;

    public SqsService(SqsClient sqsClient, ObjectMapper objectMapper, @Value("${aws.sqs.queue-url}") String queueUrl) {
        this.sqsClient = sqsClient;
        this.objectMapper = objectMapper;
        this.queueUrl = queueUrl;
    }

    public void sendNoteNotification(Object note) {
        try {
            String messageBody = objectMapper.writeValueAsString(note);
            SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(messageBody)
                    .build();
            sqsClient.sendMessage(sendMsgRequest);
            log.info("Sent note notification to SQS: {}", queueUrl);
        } catch (Exception e) {
            log.error("Failed to send message to SQS", e);
        }
    }
}
