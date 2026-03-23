package com.example.note_manager.service;

import com.example.note_manager.config.RdsCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Service
@Profile("!test")
public class RdsSecretsService {
    private static final Logger log = LoggerFactory.getLogger(RdsSecretsService.class);

    @Value("${aws.secrets.postgres.secret-id}")
    private String secretId;

    @Value("${aws.region}")
    private String awsRegion;

    private SecretsManagerClient secretsManagerClient;

    private SecretsManagerClient getSecretsManagerClient() {
        if (secretsManagerClient == null) {
            try {
                secretsManagerClient = SecretsManagerClient.builder()
                        .region(Region.of(awsRegion))
                        .credentialsProvider(DefaultCredentialsProvider.create())
                        .build();
                log.info("Created AWS Secrets Manager client for region: {}", awsRegion);
            } catch (Exception e) {
                log.error("Failed to create AWS Secrets Manager client", e);
                throw new RuntimeException("Unable to create AWS Secrets Manager client", e);
            }
        }
        return secretsManagerClient;
    }

    public RdsCredentials getRdsCredentials() {
        try {
            log.info("Fetching RDS credentials from AWS Secrets Manager with secretId: {}", secretId);

            GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                    .secretId(secretId)
                    .build();

            GetSecretValueResponse getSecretValueResponse = getSecretsManagerClient()
                    .getSecretValue(getSecretValueRequest);

            String secretString = getSecretValueResponse.secretString();
            log.debug("Retrieved secret string: {}", secretString);

            ObjectMapper objectMapper = new ObjectMapper();
            // Parse into a map to handle the new jdbcUrl field
            return objectMapper.readValue(secretString, RdsCredentials.class);
        } catch (Exception e) {
            log.error("Failed to retrieve or parse RDS credentials from AWS Secrets Manager", e);
            return null;
        }
    }
}
