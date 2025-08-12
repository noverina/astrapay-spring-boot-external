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
@RequestMapping
public class HomeController {
    @Operation(summary = "This is a controller to test if the service is working correctly")
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return new ResponseEntity<>("pong", HttpStatus.OK);
    }
}
