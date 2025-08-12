package com.astrapay.controller;

import com.astrapay.dto.HttpResponseDto;
import com.astrapay.dto.UpsertNoteDto;
import com.astrapay.entity.Note;
import com.astrapay.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private NoteService service;

    @Operation(summary = "Insert a note")
    @PostMapping
    public ResponseEntity<HttpResponseDto<?>> insert(@RequestBody UpsertNoteDto dto) {
        service.insert(dto);
        HttpResponseDto<String> httpRes = new HttpResponseDto<>(false, "Note successfully created", null);
        return new ResponseEntity<>(httpRes, HttpStatus.OK);
    }

    @Operation(summary = "Get all notes")
    @GetMapping
    public ResponseEntity<HttpResponseDto<?>> getAll(@Parameter(description = "IANA format") @RequestParam String timezone) {
        List<Note> result = service.getAll(timezone);
        HttpResponseDto<List<Note>> httpRes = new HttpResponseDto<>(false, "", result);
        return new ResponseEntity<>(httpRes, HttpStatus.OK);
    }

    @Operation(summary = "Delete a note")
    @DeleteMapping
    public ResponseEntity<HttpResponseDto<?>> delete(@RequestParam String id) {
        service.delete(id);
        HttpResponseDto<String> httpRes = new HttpResponseDto<>(false, "Note successfully deleted", null);
        return new ResponseEntity<>(httpRes, HttpStatus.OK);
    }
}
