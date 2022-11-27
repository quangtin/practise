package com.pm.tin.util.exception;

import java.util.Map;

public record ErrorDto(String code, String msg, Object... params) {
}
