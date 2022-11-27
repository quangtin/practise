package com.pm.tin.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageUtil {
    private static ApplicationContext applicationContext;
    private final MessageSource messageSource;

    public static String getMessage(String key, Object... args) {
        return applicationContext.getBean(MessageSource.class).getMessage(key, args, Locale.getDefault());
    }

    @Autowired
    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

}
