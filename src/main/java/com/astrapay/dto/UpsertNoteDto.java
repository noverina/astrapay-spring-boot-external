package com.astrapay.dto;

import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpsertNoteDto {
    private String title;
    private String content;
}
