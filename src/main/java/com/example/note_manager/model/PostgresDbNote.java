package com.example.note_manager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostgresDbNote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String creatorName;
    private String creatorEmail;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt;
}
