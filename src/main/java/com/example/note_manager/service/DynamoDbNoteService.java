package com.example.note_manager.service;

import com.example.note_manager.model.DynamoDbNote;
import com.example.note_manager.repository.DynamoDbNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DynamoDbNoteService {

    private final DynamoDbNoteRepository noteRepository;
    private final SqsService sqsService;

    @Autowired
    public DynamoDbNoteService(DynamoDbNoteRepository noteRepository, SqsService sqsService) {
        this.noteRepository = noteRepository;
        this.sqsService = sqsService;
    }

    public DynamoDbNote createNote(DynamoDbNote note) {
        UUID id = UUID.randomUUID();
        note.setId(id);
        note.setCreated_at(LocalDateTime.now());
        DynamoDbNote savedNote = noteRepository.save(note);
        sqsService.sendNoteNotification(savedNote);
        return savedNote;
    }

    public List<DynamoDbNote> getAllNotes() {
        return noteRepository.findAll();
    }
}
