package com.pm.tin.product.video;

import com.pm.tin.util.CommonMapperConfig;
import org.mapstruct.Mapper;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Mapper(config = CommonMapperConfig.class)
interface VideoMapper {
    VideoDto toVDto(StreamingResponseBody body, long start, long end, long length);
}
