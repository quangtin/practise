package com.pm.tin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

public class MessageUtil {
    @Autowired
    private static MessageSource messageSource;

    public static String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }

    @Autowired
    public void setApplicationContext(MessageSource context) {
        messageSource = context;
    }

}
