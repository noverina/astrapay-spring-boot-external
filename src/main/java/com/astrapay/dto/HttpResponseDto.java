package com.astrapay.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponseDto<T> {
    private boolean isError;
    private String message;
    private T data;
}
