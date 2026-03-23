package com.example.note_manager.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RdsCredentials(
        @JsonProperty("username") String username,
        @JsonProperty("password") String password,
        @JsonProperty("host") String host,
        @JsonProperty("port") Integer port,
        @JsonProperty("dbname") String dbName,
        @JsonProperty("dbInstanceIdentifier") String dbInstanceIdentifier,
        @JsonProperty("engine") String engine,
        @JsonProperty("driver") String driver,
        @JsonProperty("jdbcUrl") String jdbcUrl
) {
}
