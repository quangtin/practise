package com.pm.tin.util.controller;

import com.pm.tin.util.CommonMapperConfig;
import com.pm.tin.util.exception.ErrorDto;
import com.pm.tin.util.exception.PMRuntimeException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfig.class)
public interface ExceptionMapper {
    @Mapping(target = ".", source = "dto.")
    @Mapping(target = "msg", source = "msg")
    ErrorDto toErrDto(PMRuntimeException.ExceptionDto dto, String msg);
}
