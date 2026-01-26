package com.example.note_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import com.example.note_manager.WebConfig;

@SpringBootApplication
@Import(WebConfig.class)
public class NoteManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteManagerApplication.class, args);
	}

}
