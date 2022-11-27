package com.pm.tin.util.controller;

import com.pm.tin.util.exception.DataNotFoundException;
import com.pm.tin.util.exception.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class CommonAdvice {
    @ResponseBody
    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorDto dataNotFoundHandler(DataNotFoundException ex) {
        return ex.getErrorDto();
    }

}
