package com.astrapay.controller.advice;

import com.astrapay.dto.HttpResponseDto;
import com.astrapay.exception.EntityNotFoundException;
import com.astrapay.exception.InvalidClassException;
import com.astrapay.exception.InvalidFieldException;
import com.astrapay.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private LogUtil logUtil;

    @ExceptionHandler(InvalidClassException.class)
    public ResponseEntity<HttpResponseDto<String>> handleInvalidClassException(Exception ex) {
        HttpResponseDto<String> resEntity = new HttpResponseDto<String>(true, ex.getMessage(), null);
        logUtil.error(ex);
        return new ResponseEntity<>(resEntity, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<HttpResponseDto<String>> handleEntityNotFoundException(Exception ex) {
        HttpResponseDto<String> resEntity = new HttpResponseDto<String>(true, "The requested entity doesn't exist", null);
        logUtil.error(ex);
        return new ResponseEntity<>(resEntity, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponseDto<String>> handleAllException(Exception ex) {
        HttpResponseDto<String> resEntity = new HttpResponseDto<String>(true, ex.getMessage(), null);
        logUtil.error(ex);
        return new ResponseEntity<>(resEntity, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<HttpResponseDto<String>> handleInvalidFieldException(Exception ex) {
        HttpResponseDto<String> resEntity = new HttpResponseDto<String>(true, ex.getMessage(), null);
        logUtil.error(ex);
        return new ResponseEntity<>(resEntity, HttpStatus.BAD_REQUEST);
    }
}
