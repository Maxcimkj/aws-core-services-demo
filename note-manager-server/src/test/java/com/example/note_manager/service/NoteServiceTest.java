package com.example.note_manager.service;

import com.example.note_manager.model.Note;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

class NoteServiceTest {

    private NoteService noteService;
    private Map<UUID, Note> noteStorage;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() throws Exception {
        noteService = new NoteService();
        
        // Access the private static noteStorage field using reflection
        Field field = NoteService.class.getDeclaredField("noteStorage");
        field.setAccessible(true);
        noteStorage = (Map<UUID, Note>) field.get(null);
        
        // Clear storage before each test
        noteStorage.clear();
    }

    @AfterEach
    void tearDown() {
        // Clear storage after each test
        if (noteStorage != null) {
            noteStorage.clear();
        }
    }

    @Test
    void testCreateNote_SetsIdAndCreatedAt() {
        // Given
        Note inputNote = new Note();
        inputNote.setCreatorName("John Doe");
        inputNote.setCreatorEmail("john.doe@example.com");
        inputNote.setContent("Test note content");

        // When
        Note createdNote = noteService.createNote(inputNote);

        // Then
        assertNotNull(createdNote.getId(), "Note ID should be set");
        assertNotNull(createdNote.getCreated_at(), "Created_at timestamp should be set");
        assertEquals("John Doe", createdNote.getCreatorName());
        assertEquals("john.doe@example.com", createdNote.getCreatorEmail());
        assertEquals("Test note content", createdNote.getContent());
    }

    @Test
    void testCreateNote_StoresNoteInStorage() {
        // Given
        Note inputNote = new Note();
        inputNote.setCreatorName("Jane Smith");
        inputNote.setCreatorEmail("jane.smith@example.com");
        inputNote.setContent("Another test note");

        // When
        Note createdNote = noteService.createNote(inputNote);

        // Then
        assertEquals(1, noteStorage.size(), "Storage should contain one note");
        assertTrue(noteStorage.containsKey(createdNote.getId()), "Storage should contain the created note");
        assertEquals(createdNote, noteStorage.get(createdNote.getId()), "Stored note should match created note");
    }

    @Test
    void testCreateNote_GeneratesUniqueIds() {
        // Given
        Note note1 = new Note();
        note1.setCreatorName("User 1");
        note1.setCreatorEmail("user1@example.com");
        note1.setContent("First note");

        Note note2 = new Note();
        note2.setCreatorName("User 2");
        note2.setCreatorEmail("user2@example.com");
        note2.setContent("Second note");

        // When
        Note createdNote1 = noteService.createNote(note1);
        Note createdNote2 = noteService.createNote(note2);

        // Then
        assertNotEquals(createdNote1.getId(), createdNote2.getId(), "Each note should have a unique ID");
        assertEquals(2, noteStorage.size(), "Storage should contain two notes");
    }

    @Test
    void testCreateNote_SetsCreatedAtTimestamp() {
        // Given
        Note inputNote = new Note();
        inputNote.setCreatorName("Test User");
        inputNote.setCreatorEmail("test@example.com");
        inputNote.setContent("Test content");
        
        LocalDateTime beforeCreation = LocalDateTime.now();

        // When
        Note createdNote = noteService.createNote(inputNote);
        
        LocalDateTime afterCreation = LocalDateTime.now();

        // Then
        assertNotNull(createdNote.getCreated_at(), "Created_at should not be null");
        assertTrue(
            createdNote.getCreated_at().isAfter(beforeCreation.minusSeconds(1)) &&
            createdNote.getCreated_at().isBefore(afterCreation.plusSeconds(1)),
            "Created_at should be set to current time"
        );
    }

    @Test
    void testGetAllNotes_ReturnsEmptyListWhenNoNotes() {
        // When
        List<Note> notes = noteService.getAllNotes();

        // Then
        assertNotNull(notes, "Should return a non-null list");
        assertTrue(notes.isEmpty(), "Should return an empty list when no notes exist");
    }

    @Test
    void testGetAllNotes_ReturnsAllStoredNotes() {
        // Given
        Note note1 = new Note();
        note1.setCreatorName("User 1");
        note1.setCreatorEmail("user1@example.com");
        note1.setContent("First note");
        Note createdNote1 = noteService.createNote(note1);

        Note note2 = new Note();
        note2.setCreatorName("User 2");
        note2.setCreatorEmail("user2@example.com");
        note2.setContent("Second note");
        Note createdNote2 = noteService.createNote(note2);

        Note note3 = new Note();
        note3.setCreatorName("User 3");
        note3.setCreatorEmail("user3@example.com");
        note3.setContent("Third note");
        Note createdNote3 = noteService.createNote(note3);

        // When
        List<Note> notes = noteService.getAllNotes();

        // Then
        assertEquals(3, notes.size(), "Should return all three notes");
        assertTrue(notes.contains(createdNote1), "Should contain first note");
        assertTrue(notes.contains(createdNote2), "Should contain second note");
        assertTrue(notes.contains(createdNote3), "Should contain third note");
    }

    @Test
    void testGetAllNotes_ReturnsNewListInstance() {
        // Given
        Note note = new Note();
        note.setCreatorName("Test User");
        note.setCreatorEmail("test@example.com");
        note.setContent("Test content");
        noteService.createNote(note);

        // When
        List<Note> notes1 = noteService.getAllNotes();
        List<Note> notes2 = noteService.getAllNotes();

        // Then
        assertNotSame(notes1, notes2, "Each call should return a new list instance");
        assertEquals(notes1, notes2, "Both lists should contain the same notes");
    }

    @Test
    void testCreateNote_ReturnsSameNoteInstance() {
        // Given
        Note inputNote = new Note();
        inputNote.setCreatorName("Test User");
        inputNote.setCreatorEmail("test@example.com");
        inputNote.setContent("Test content");

        // When
        Note createdNote = noteService.createNote(inputNote);

        // Then
        assertSame(inputNote, createdNote, "Should return the same note instance that was passed in");
        assertNotNull(createdNote.getId(), "Returned note should have ID set");
        assertNotNull(createdNote.getCreated_at(), "Returned note should have created_at set");
    }
}
