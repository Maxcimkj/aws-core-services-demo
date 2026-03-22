package com.example.note_manager;

import com.example.note_manager.repository.DynamoDbNoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class NoteManagerApplicationTests {
	
    @MockitoBean
    private DynamoDbNoteRepository noteRepository;

    @Test
    void contextLoads() {
    }
}
