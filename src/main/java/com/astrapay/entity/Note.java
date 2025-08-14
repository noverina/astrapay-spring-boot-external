package com.astrapay.entity;

import com.astrapay.util.UuidV6Generator;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    private String id;
    private String title;
    private String content;
    private ZonedDateTime createdAt;


    public Note(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = ZonedDateTime.now(ZoneId.of("UTC"));
    }
}
