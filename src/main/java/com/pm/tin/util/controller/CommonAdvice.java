package com.pm.tin.util.controller;

import com.pm.tin.util.exception.DataNotFoundException;
import com.pm.tin.util.exception.ErrorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
class CommonAdvice {
    private final MessageSource messageSource;
    private final ExceptionMapper mapper;

    @ResponseBody
    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorDto dataNotFoundHandler(DataNotFoundException ex) {
        String message = messageSource.getMessage(ex.getData().code(), ex.getData().params(), Locale.getDefault());
        log.error(message, ex);

        return mapper.toErrDto(ex.getData(), message);
    }

}
