package com.astrapay;

import com.astrapay.dto.UpsertNoteDto;
import com.astrapay.entity.Note;
import com.astrapay.exception.EntityNotFoundException;
import com.astrapay.service.NoteService;
import com.astrapay.util.MemoryStoreUtil;
import com.astrapay.util.UuidV6Generator;
import com.astrapay.exception.InvalidClassException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class NoteServiceTest {
    @InjectMocks
    private NoteService noteService;
    @InjectMocks
    private MemoryStoreUtil memoryStore;
    @InjectMocks
    private UuidV6Generator idGenerator;


    @Test
    void insert_shouldStoreNoteWithGeneratedId() {
        UpsertNoteDto dto = new UpsertNoteDto("Test title", "Test content");
        when(idGenerator.generate()).thenReturn("mock-note-123");

        noteService.insert(dto);

        Object object = memoryStore.get("mock-note-123");
        assertInstanceOf(Note.class, object);
        assertEquals("Test content", ((Note) object).getContent());
    }

    @Test
    void delete_shouldThrowIfNoteNotFound() {
        assertThrows(EntityNotFoundException.class, () -> noteService.delete("missing-id"));
    }

    @Test
    void delete_shouldThrowIfStoredObjectIsNotNote() {
        memoryStore.save("weird-id", "not-a-note");
        assertThrows(InvalidClassException.class, () -> noteService.delete("weird-id"));
    }

    @Test
    void getAll_shouldConvertTimezone() {
        Note note = new Note("id-1", "title", "content");
        memoryStore.save("id-1", note);

        List<Note> notes = noteService.getAll("Asia/Jakarta");

        assertEquals(1, notes.size());
        assertEquals("Asia/Jakarta", notes.get(0).getCreatedAt().getZone().getId());
    }

    @Test
    void getAll_shouldThrowForInvalidTimezone() {
        String invalidZone = "Earth/Somewhere-1";
        assertThrows(DateTimeException.class, () -> {
            noteService.getAll(invalidZone);
        });
    }
}