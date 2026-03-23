package com.example.note_manager;

import com.example.note_manager.config.RdsCredentials;
import com.example.note_manager.service.RdsSecretsService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TestConfig {

    @Bean
    @Primary
    public RdsSecretsService rdsSecretsService() {
        RdsSecretsService mockService = Mockito.mock(RdsSecretsService.class);
        RdsCredentials testCredentials = new RdsCredentials(
                "sa",                    // username
                "",                      // password
                "localhost",             // host
                5432,                    // port
                "testdb",                // dbName
                "test-instance",         // dbInstanceIdentifier
                "postgres",              // engine
                "org.h2.Driver",         // driver
                "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL" // jdbcUrl
        );
        Mockito.when(mockService.getRdsCredentials()).thenReturn(testCredentials);
        return mockService;
    }
}
