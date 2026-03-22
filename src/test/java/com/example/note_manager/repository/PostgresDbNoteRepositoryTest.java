package com.example.note_manager.repository;

import com.example.note_manager.model.PostgresDbNote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class PostgresDbNoteRepositoryTest {

    @Autowired
    private PostgresDbNoteRepository noteRepository;

    @Test
    void testSaveAndFindAll() {
        // Given
        PostgresDbNote note = new PostgresDbNote();
        note.setCreatorName("Tester");
        note.setCreatorEmail("test@example.com");
        note.setContent("Test content");
        note.setCreatedAt(LocalDateTime.now());

        // When
        noteRepository.save(note);
        List<PostgresDbNote> notes = noteRepository.findAll();

        // Then
        assertThat(notes).hasSize(1);
        assertThat(notes.get(0).getCreatorName()).isEqualTo("Tester");
        assertThat(notes.get(0).getId()).isNotNull();
    }

    @Test
    void testFindAll_Empty() {
        // When
        List<PostgresDbNote> notes = noteRepository.findAll();

        // Then
        assertThat(notes).isEmpty();
    }
}
