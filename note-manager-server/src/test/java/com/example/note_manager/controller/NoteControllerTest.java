package com.example.note_manager.controller;

import com.example.note_manager.model.Note;
import com.example.note_manager.service.NoteService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NoteService noteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Note testNote;
    private UUID testId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        testNote = new Note();
        testNote.setId(testId);
        testNote.setCreatorName("John Doe");
        testNote.setCreatorEmail("john.doe@example.com");
        testNote.setContent("Test note content");
        testNote.setCreated_at(LocalDateTime.now());
    }

    @Test
    void testCreateNote() throws Exception {
        // Given
        Note inputNote = new Note();
        inputNote.setCreatorName("John Doe");
        inputNote.setCreatorEmail("john.doe@example.com");
        inputNote.setContent("Test note content");

        when(noteService.createNote(any(Note.class))).thenReturn(testNote);

        // When & Then
        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputNote)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.creatorName").value("John Doe"))
                .andExpect(jsonPath("$.creatorEmail").value("john.doe@example.com"))
                .andExpect(jsonPath("$.content").value("Test note content"))
                .andExpect(jsonPath("$.created_at").exists());
    }

    @Test
    void testGetAllNotes() throws Exception {
        // Given
        List<Note> notes = new ArrayList<>();
        notes.add(testNote);

        Note anotherNote = new Note();
        anotherNote.setId(UUID.randomUUID());
        anotherNote.setCreatorName("Jane Smith");
        anotherNote.setCreatorEmail("jane.smith@example.com");
        anotherNote.setContent("Another test note");
        anotherNote.setCreated_at(LocalDateTime.now());
        notes.add(anotherNote);

        when(noteService.getAllNotes()).thenReturn(notes);

        // When & Then
        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].creatorName").value("John Doe"))
                .andExpect(jsonPath("$[0].creatorEmail").value("john.doe@example.com"))
                .andExpect(jsonPath("$[0].content").value("Test note content"))
                .andExpect(jsonPath("$[1].creatorName").value("Jane Smith"))
                .andExpect(jsonPath("$[1].creatorEmail").value("jane.smith@example.com"))
                .andExpect(jsonPath("$[1].content").value("Another test note"));
    }

    @Test
    void testGetAllNotes_EmptyList() throws Exception {
        // Given
        when(noteService.getAllNotes()).thenReturn(new ArrayList<>());

        // When & Then
        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
