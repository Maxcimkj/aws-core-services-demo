package com.example.note_manager.service;

import com.example.note_manager.model.DynamoDbNote;
import com.example.note_manager.repository.DynamoDbNoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DynamoDbNote1ServiceTest {

    @Mock
    private DynamoDbNoteRepository noteRepository;

    @InjectMocks
    private DynamoDbNoteService noteService;

    @Test
    void testCreateNote_SetsIdAndCreatedAt() {
        // Given
        DynamoDbNote inputNote = new DynamoDbNote();
        inputNote.setCreatorName("John Doe");
        inputNote.setCreatorEmail("john.doe@example.com");
        inputNote.setContent("Test note content");
        
        when(noteRepository.save(any(DynamoDbNote.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        DynamoDbNote createdNote = noteService.createNote(inputNote);

        // Then
        assertNotNull(createdNote.getId(), "Note ID should be set");
        assertNotNull(createdNote.getCreated_at(), "Created_at timestamp should be set");
        assertEquals("John Doe", createdNote.getCreatorName());
        assertEquals("john.doe@example.com", createdNote.getCreatorEmail());
        assertEquals("Test note content", createdNote.getContent());
        verify(noteRepository).save(inputNote);
    }

    @Test
    void testCreateNote_SetsCreatedAtTimestamp() {
        // Given
        DynamoDbNote inputNote = new DynamoDbNote();
        inputNote.setCreatorName("Test User");
        inputNote.setCreatorEmail("test@example.com");
        inputNote.setContent("Test content");
        
        when(noteRepository.save(any(DynamoDbNote.class))).thenAnswer(invocation -> invocation.getArgument(0));
        LocalDateTime beforeCreation = LocalDateTime.now();

        // When
        DynamoDbNote createdNote = noteService.createNote(inputNote);
        
        LocalDateTime afterCreation = LocalDateTime.now();

        // Then
        assertNotNull(createdNote.getCreated_at(), "Created_at should not be null");
        assertTrue(
            (createdNote.getCreated_at().isAfter(beforeCreation) || createdNote.getCreated_at().isEqual(beforeCreation)) &&
            (createdNote.getCreated_at().isBefore(afterCreation) || createdNote.getCreated_at().isEqual(afterCreation)),
            "Created_at should be set to current time"
        );
    }

    @Test
    void testGetAllNotes_ReturnsEmptyListWhenNoNotes() {
        // Given
        when(noteRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<DynamoDbNote> notes = noteService.getAllNotes();

        // Then
        assertNotNull(notes, "Should return a non-null list");
        assertTrue(notes.isEmpty(), "Should return an empty list when no notes exist");
    }

    @Test
    void testGetAllNotes_ReturnsAllStoredNotes() {
        // Given
        DynamoDbNote note1 = new DynamoDbNote();
        note1.setContent("First note");
        DynamoDbNote note2 = new DynamoDbNote();
        note2.setContent("Second note");
        
        List<DynamoDbNote> expectedNotes = Arrays.asList(note1, note2);
        when(noteRepository.findAll()).thenReturn(expectedNotes);

        // When
        List<DynamoDbNote> notes = noteService.getAllNotes();

        // Then
        assertEquals(2, notes.size(), "Should return all notes");
        assertTrue(notes.contains(note1));
        assertTrue(notes.contains(note2));
    }

    @Test
    void testCreateNote_ReturnsSavedNoteInstance() {
        // Given
        DynamoDbNote inputNote = new DynamoDbNote();
        inputNote.setContent("Test content");
        
        when(noteRepository.save(any(DynamoDbNote.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        DynamoDbNote createdNote = noteService.createNote(inputNote);

        // Then
        assertSame(inputNote, createdNote, "Should return the same note instance that was passed in");
        assertNotNull(createdNote.getId(), "Returned note should have ID set");
    }
}
