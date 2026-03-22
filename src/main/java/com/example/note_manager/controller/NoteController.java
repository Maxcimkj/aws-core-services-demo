package com.example.note_manager.controller;

import com.example.note_manager.model.DynamoDbNote;
import com.example.note_manager.service.DynamoDbNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final DynamoDbNoteService noteService;

    @Autowired
    public NoteController(DynamoDbNoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public ResponseEntity<DynamoDbNote> createNote(@RequestBody DynamoDbNote note) {
        DynamoDbNote createdNote = noteService.createNote(note);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }

    @GetMapping
    public ResponseEntity<List<DynamoDbNote>> getAllNotes() {
        List<DynamoDbNote> notes = noteService.getAllNotes();
        return ResponseEntity.ok(notes);
    }
}
