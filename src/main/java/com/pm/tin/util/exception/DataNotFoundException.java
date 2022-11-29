package com.pm.tin.util.exception;

public class DataNotFoundException extends PMRuntimeException {
    public DataNotFoundException(String key, Object... params) {
        super(key, params);
    }
}
