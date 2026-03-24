package com.example.note_manager.service;

import com.example.note_manager.model.PostgresDbNote;
import com.example.note_manager.repository.PostgresDbNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostgresDbNoteService {

    private final PostgresDbNoteRepository noteRepository;
    private final SqsService sqsService;

    @Autowired
    public PostgresDbNoteService(PostgresDbNoteRepository noteRepository, SqsService sqsService) {
        this.noteRepository = noteRepository;
        this.sqsService = sqsService;
    }

    public PostgresDbNote createNote(PostgresDbNote note) {
        note.setCreatedAt(LocalDateTime.now());
        PostgresDbNote savedNote = noteRepository.save(note);
        sqsService.sendNoteNotification(savedNote);
        return savedNote;
    }

    public List<PostgresDbNote> getAllNotes() {
        return noteRepository.findAll();
    }
}
