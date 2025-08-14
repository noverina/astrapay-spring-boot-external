package com.astrapay.service;

import com.astrapay.dto.NoteListDto;
import com.astrapay.dto.UpsertNoteDto;
import com.astrapay.entity.Note;
import com.astrapay.exception.EntityNotFoundException;
import com.astrapay.exception.InvalidClassException;
import com.astrapay.exception.InvalidFieldException;
import com.astrapay.util.MemoryStoreUtil;
import com.astrapay.util.UuidV6Generator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class NoteService {
    private final UuidV6Generator idGenerator;
    private final MemoryStoreUtil memoryStore;

    @Autowired
    public NoteService(UuidV6Generator idGenerator, MemoryStoreUtil memoryStore) {
        this.idGenerator = idGenerator;
        this.memoryStore = memoryStore;
    }

    public void insert(UpsertNoteDto dto) {
        if (dto.getTitle() == null || dto.getContent() == null || dto.getTitle().length() < 3 || dto.getTitle().length()> 30 || dto.getContent().length() < 3 || dto.getContent().length() > 500) {
            throw new InvalidFieldException("Unable to create note, invalid field detected");
        }
        String id = idGenerator.generate();
        Note entity = new Note(id, dto.getTitle(), dto.getContent());
        memoryStore.save(id, entity);
    }

    public void delete(String id) {
        Object objectFromStore = memoryStore.get(id);
        if (objectFromStore == null) {
            throw new EntityNotFoundException();
        } else if (!(objectFromStore instanceof Note)) {
            throw new InvalidClassException("Expected Note.class, instead found" + objectFromStore.getClass());
        }
        memoryStore.delete(id);
    }

    public List<NoteListDto> getAll(String timezone) {
        Collection<Object> objectsFromStore = memoryStore.getAll();
        List<NoteListDto> notes = new ArrayList<>();
        for (Object obj : objectsFromStore) {
            if (!(obj instanceof Note)) {
                throw new InvalidClassException("Expected Note.class, instead found" + obj.getClass());
            }
            Note note = (Note) obj;
            ZoneId requestedZone = ZoneId.of(timezone);
            NoteListDto dto = new NoteListDto(note.getId(), note.getTitle(), note.getContent(), note.getCreatedAt().withZoneSameInstant(requestedZone));
            notes.add(dto);
        }
        return notes;
    }
}
