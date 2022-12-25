package com.pm.tin.util.aws.s3;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.ServerSideEncryption;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

import static com.pm.tin.util.aws.s3.SignedType.VIDEO;
import static java.util.Optional.ofNullable;

@Component
public class S3Util {
    public static final long THIRTY_MINUTES = 30l;
    public static final long SIXTY_MINUTES = 60l;
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final String defaultBucket;
    private final Map<SignedType, String> bucketNameMap;
    private final long timeoutUpload;
    private final long timeoutDownload;

    S3Util(S3PropertyConfiguration s3Property) {
        s3Client = getS3Client(s3Property);
        s3Presigner = getS3Presigner(s3Property);
        defaultBucket = s3Property.getBucketVideo();
        timeoutUpload = ofNullable(s3Property.getTimeoutUpload()).orElse(THIRTY_MINUTES);
        timeoutDownload = ofNullable(s3Property.getTimeoutDownload()).orElse(SIXTY_MINUTES);
        bucketNameMap = ImmutableMap.of(VIDEO, s3Property.getBucketVideo());
    }

    private S3Presigner getS3Presigner(S3PropertyConfiguration s3Property) {
        S3Presigner.Builder builder = S3Presigner.builder().region(Region.of(s3Property.getRegion()));
        ofNullable(s3Property.getEndpoint())
                .filter(StringUtils::isNotBlank)
                .map(URI::create)
                .ifPresent(builder::endpointOverride);
        return builder.build();
    }

    private S3Client getS3Client(S3PropertyConfiguration s3Property) {
        S3ClientBuilder builder = S3Client.builder().region(Region.of(s3Property.getRegion()));
        ofNullable(s3Property.getEndpoint())
                .filter(StringUtils::isNotBlank)
                .map(URI::create)
                .ifPresent(builder::endpointOverride);
        return builder.build();
    }

    public UploadUrlResult uploadUrl(UploadUrlReq req) {
        String key = req.getPath();
        String path = generateUrl(bucketNameMap.getOrDefault(req.getType(), defaultBucket), key);
        return new UploadUrlResult(path, key);

    }

    private String generateUrl(String bucketName, String path) {
        PutObjectRequest getObjectRequest = PutObjectRequest
                .builder()
                .serverSideEncryption(ServerSideEncryption.AES256)
                .bucket(bucketName)
                .key(path)
                .build();

        PutObjectPresignRequest getObjectPresignRequest = PutObjectPresignRequest
                .builder()
                .signatureDuration(Duration.ofMinutes(timeoutUpload))
                .putObjectRequest(getObjectRequest)

                .build();
        URL url = s3Presigner.presignPutObject(getObjectPresignRequest).url();
        return url.toString();
    }

    public String downloadUrl(DownloadUrlReq req) {
        String bucketName = bucketNameMap.getOrDefault(req.getType(), defaultBucket);
        String path = req.getPath();
        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(path).build();

        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest
                .builder()
                .signatureDuration(Duration.ofMinutes(timeoutDownload))
                .getObjectRequest(getObjectRequest)
                .build();
        URL url = s3Presigner.presignGetObject(getObjectPresignRequest).url();
        return url.toString();
    }
    
    public InputStream download(DownloadUrlReq req) {
        String bucketName = bucketNameMap.getOrDefault(req.getType(), defaultBucket);
        String path = req.getPath();
        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(path).build();
        return s3Client.getObject(getObjectRequest, ResponseTransformer.toBytes()).asInputStream();
    }
    
    public Long getLengthFile(DownloadUrlReq req) {
        String bucketName = bucketNameMap.getOrDefault(req.getType(), defaultBucket);
        String path = req.getPath();
        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(path).build();
        return s3Client.getObject(getObjectRequest).response().contentLength();
    }
}
