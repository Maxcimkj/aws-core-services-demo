package com.example.note_manager.config;

import com.example.note_manager.service.RdsSecretsService;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class RdsDataSourceConfig {
    private static final Logger log = LoggerFactory.getLogger(RdsDataSourceConfig.class);

    private final RdsSecretsService rdsSecretsService;

    public RdsDataSourceConfig(RdsSecretsService rdsSecretsService) {
        this.rdsSecretsService = rdsSecretsService;
    }

    @Bean
    public DataSource dataSource() {
        // Try to get credentials from AWS Secrets Manager
        RdsCredentials credentials = rdsSecretsService.getRdsCredentials();
        // Check if credentials are null
        if (credentials == null) {
            throw new IllegalStateException(
                    "Failed to retrieve RDS credentials from AWS Secrets Manager. " +
                    "The credentials object is null. Please check your AWS configuration and secret."
            );
        }
        
        String jdbcUrl = credentials.jdbcUrl();
        String username = credentials.username();
        String password = credentials.password();
        String driver = credentials.driver();

        // Validate that jdbcUrl is not null or empty
        if (jdbcUrl == null) {
            throw new IllegalStateException(
                    "JDBC URL cannot be null or empty. Check RDS credentials: " +
                            "host=" + credentials.host() + ", port=" + credentials.port() + ", dbName=" + credentials.dbName()
            );
        }
        // Validate username and password
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalStateException("Database username cannot be null or empty");
        }
        if (password == null) {
            throw new IllegalStateException("Database password cannot be null");
        }

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);
        dataSource.setMaximumPoolSize(10);
        dataSource.setMinimumIdle(2);
        dataSource.setConnectionTimeout(30000);
        dataSource.setIdleTimeout(600000);
        dataSource.setMaxLifetime(1800000);
        dataSource.setConnectionTestQuery("SELECT 1");

        log.info("Initialized HikariDataSource with URL: {}, username: {}, maxPoolSize: {}, minIdle: {}, connectionTimeout: {} ms, idleTimeout: {} ms, maxLifetime: {} ms",
                jdbcUrl, username, dataSource.getMaximumPoolSize(), dataSource.getMinimumIdle(),
                dataSource.getConnectionTimeout(), dataSource.getIdleTimeout(), dataSource.getMaxLifetime());

        return dataSource;
    }
}

// jdbc:postgresql://note-manager-database.cjm4c0ckadut.eu-north-1.rds.amazonaws.com:5432/postgres
