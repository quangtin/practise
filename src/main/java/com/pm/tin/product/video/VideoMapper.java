package com.pm.tin.product.video;

import com.pm.tin.util.CommonMapperConfig;
import com.pm.tin.util.aws.s3.DownloadUrlReq;
import com.pm.tin.util.aws.s3.UploadUrlReq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Mapper(config = CommonMapperConfig.class)
interface VideoMapper {
    VideoDto toVDto(StreamingResponseBody body, long start, long end, long length);
    
    @Mapping(target = "type", constant = "VIDEO")
    UploadUrlReq toURReq(VideoRequest req);
    
    @Mapping(target = "type", constant = "VIDEO")
    DownloadUrlReq toDR(PlayVideoReq req);
    
    @Mapping(target = "type", constant = "VIDEO")
    DownloadUrlReq toDUReq(VideoRequest req);
}
