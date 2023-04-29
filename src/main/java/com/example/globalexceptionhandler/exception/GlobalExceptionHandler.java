package com.example.globalexceptionhandler.exception;

import com.example.globalexceptionhandler.model.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ResponseModel> handleBadRequest(RuntimeException ex, WebRequest request) {
        ResponseModel model = new ResponseModel();
        model.setMessage(ex.getMessage());
        model.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }
}
