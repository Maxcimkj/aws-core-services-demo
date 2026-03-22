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

    @Autowired
    public PostgresDbNoteService(PostgresDbNoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public PostgresDbNote createNote(PostgresDbNote note) {
        note.setCreatedAt(LocalDateTime.now());
        return noteRepository.save(note);
    }

    public List<PostgresDbNote> getAllNotes() {
        return noteRepository.findAll();
    }
}
