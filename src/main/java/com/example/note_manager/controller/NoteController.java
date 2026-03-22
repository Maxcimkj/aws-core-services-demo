package com.example.note_manager.controller;

import com.example.note_manager.model.PostgresDbNote;
import com.example.note_manager.service.PostgresDbNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final PostgresDbNoteService noteService;

    @Autowired
    public NoteController(PostgresDbNoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public ResponseEntity<PostgresDbNote> createNote(@RequestBody PostgresDbNote note) {
        PostgresDbNote createdNote = noteService.createNote(note);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }

    @GetMapping
    public ResponseEntity<List<PostgresDbNote>> getAllNotes() {
        List<PostgresDbNote> notes = noteService.getAllNotes();
        return ResponseEntity.ok(notes);
    }
}
