package com.astrapay.service;

import com.astrapay.dto.NoteListDto;
import com.astrapay.dto.UpsertNoteDto;
import com.astrapay.entity.Note;
import com.astrapay.exception.EntityNotFoundException;
import com.astrapay.exception.InvalidClassException;
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
    @Autowired
    private MemoryStoreUtil memoryStore;
    @Autowired
    private UuidV6Generator idGenerator;

    public void insert(UpsertNoteDto dto) {
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
            NoteListDto dto = new NoteListDto(note.getTitle(), note.getContent(), note.getCreatedAt().withZoneSameInstant(requestedZone));
            notes.add(dto);
        }
        return notes;
    }
}
