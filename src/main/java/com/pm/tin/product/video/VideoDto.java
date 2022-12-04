package com.pm.tin.product.video;

import lombok.Builder;
import lombok.Value;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Builder
@Value
class VideoDto {
    long length;
    long start;
    long end;
    StreamingResponseBody body;
}
