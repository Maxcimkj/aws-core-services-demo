package com.example.note_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    private UUID id;
    private String creatorName;
    private String creatorEmail;
    private String content;
    private LocalDateTime created_at;
}
