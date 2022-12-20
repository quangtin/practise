package com.pm.tin.util.aws.s3;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "aws.s3")
@ConfigurationPropertiesScan
@Component
@Getter
@Setter
public class S3PropertyConfiguration {
    private String region;
    private String endpoint;
    private String bucketVideo;
    private Long timeoutDownload;
    private Long timeoutUpload;
}
