package com.example.note_manager.service;

import com.example.note_manager.model.PostgresDbNote;
import com.example.note_manager.repository.PostgresDbNoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostgresDbNoteServiceTest {

    @Mock
    private PostgresDbNoteRepository noteRepository;

    @Mock
    private SqsService sqsService;

    @InjectMocks
    private PostgresDbNoteService noteService;

    private PostgresDbNote testNote;

    @BeforeEach
    void setUp() {
        testNote = new PostgresDbNote();
        testNote.setId(UUID.randomUUID());
        testNote.setCreatorName("Tester");
        testNote.setCreatorEmail("tester@example.com");
        testNote.setContent("Mocked content");
    }

    @Test
    void testCreateNote_SetsCreatedAtAndSaves() {
        // Given
        when(noteRepository.save(any(PostgresDbNote.class))).thenReturn(testNote);

        // When
        PostgresDbNote created = noteService.createNote(new PostgresDbNote());

        // Then
        assertThat(created).isNotNull();
        assertThat(created.getCreatorName()).isEqualTo("Tester");
        verify(noteRepository, times(1)).save(any(PostgresDbNote.class));
        verify(sqsService, times(1)).sendNoteNotification(created);
    }

    @Test
    void testGetAllNotes_ReturnsListFromRepository() {
        // Given
        List<PostgresDbNote> notes = Arrays.asList(testNote, new PostgresDbNote());
        when(noteRepository.findAll()).thenReturn(notes);

        // When
        List<PostgresDbNote> result = noteService.getAllNotes();

        // Then
        assertThat(result).hasSize(2);
        verify(noteRepository, times(1)).findAll();
    }
}
