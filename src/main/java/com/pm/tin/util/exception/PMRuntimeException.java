package com.pm.tin.util.exception;

import com.pm.tin.util.MessageUtil;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class PMRuntimeException extends RuntimeException {
    private ErrorDto errorDto;


    public PMRuntimeException(String key, Object... params) {
        this(StringUtils.joinWith(":", key, MessageUtil.getMessage(key, params)));
        this.errorDto = new ErrorDto(key, getMessage(), params);
    }

    private PMRuntimeException(String msg) {
        super(msg);
    }

    public PMRuntimeException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
