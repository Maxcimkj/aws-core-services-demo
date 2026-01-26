package com.example.note_manager.service;

import com.example.note_manager.model.Note;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NoteService {
    private static final Map<UUID, Note> noteStorage = new ConcurrentHashMap<>();

    public Note createNote(Note note) {
        UUID id = UUID.randomUUID();
        note.setId(id);
        note.setCreated_at(LocalDateTime.now());
        noteStorage.put(id, note);
        return note;
    }

    public List<Note> getAllNotes() {
        return new ArrayList<>(noteStorage.values());
    }
}
