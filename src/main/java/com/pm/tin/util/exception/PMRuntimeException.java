package com.pm.tin.util.exception;

import lombok.Getter;

@Getter
public class PMRuntimeException extends RuntimeException {
    private ExceptionDto data;

    public PMRuntimeException(String key, Object... params) {
        this(key);
        this.data = new ExceptionDto(key, params);
    }

    private PMRuntimeException(String msg) {
        super(msg);
    }

    public PMRuntimeException(String msg, Throwable ex) {
        super(msg, ex);
    }

    public record ExceptionDto(String code, Object... params) {
    }

    ;
}
