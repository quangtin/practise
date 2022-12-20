package com.pm.tin.util.aws.s3;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DownloadUrlReq {
    SignedType type;
    String path;
}
