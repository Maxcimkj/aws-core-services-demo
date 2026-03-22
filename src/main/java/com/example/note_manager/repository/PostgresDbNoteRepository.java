package com.example.note_manager.repository;

import com.example.note_manager.model.PostgresDbNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PostgresDbNoteRepository extends JpaRepository<PostgresDbNote, UUID> {
}
