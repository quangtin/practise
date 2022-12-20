package com.pm.tin.util.aws.s3;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class UploadUrlReq {
  SignedType type;
  String path;
}
