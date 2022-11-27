package com.pm.tin.util.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class DataNotFoundException extends PMRuntimeException {
    public DataNotFoundException(String key, Object... params) {
        super(key, params);
    }
}
