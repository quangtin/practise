package com.pm.tin.product.video;

import com.pm.tin.util.aws.s3.S3Util;
import com.pm.tin.util.aws.s3.UploadUrlResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitFailureHandler;
import reactor.core.publisher.Sinks.Many;

import java.io.*;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.PARTIAL_CONTENT;

@RestController
@RequestMapping("videos")
@RequiredArgsConstructor
public class VideoController {

    public static final String READ = "r";
    private final VideoMapper mapper;
    private final S3Util s3Util;
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final Many<Integer> sink = Sinks.many().multicast().onBackpressureBuffer(100);
    private byte[] BUFFER = new byte[1024];
    private ExecutorService nonBlockingService = Executors.newCachedThreadPool();

    @PostMapping(value = "/uploads")
    ResponseEntity<UploadUrlResult> upLoadVideo(@RequestBody VideoRequest req) {
        UploadUrlResult result = s3Util.uploadUrl(mapper.toURReq(req));
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/play", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<StreamingResponseBody> play(PlayVideoReq req) throws FileNotFoundException {
        // read file
        VideoDto response = playVideoHandler(req);
        return ResponseEntity
                .status(PARTIAL_CONTENT)
                .header(CONTENT_TYPE, "videos/mp4")
                .header(ACCEPT_RANGES, "bytes")
                .header(CONTENT_LENGTH, String.valueOf(response.getLength()))
                .header(CONTENT_RANGE,
                        "bytes" + " " + response.getStart() + "-" + response.getEnd() + "/" + response.getLength())
                .header(CONTENT_LENGTH, String.valueOf(response.getLength()))
                .body(response.getBody());
    }

    VideoDto playVideoHandler(PlayVideoReq req) throws FileNotFoundException {
        // from video id -> path videos (sample get on s3)
        String path = "classpath:/videos/example.mp4";
        File f = ResourceUtils.getFile("classpath:videos/example.mp4");
        long start = ofNullable(req.getStart()).orElse(0L);
        long end = ofNullable(req.getEnd()).filter(e -> e < f.length()).orElse(f.length());

        StreamingResponseBody response = (os) -> {

            try (RandomAccessFile file = new RandomAccessFile(f, READ)) {
                long pos = start;
                file.seek(pos);
                while (pos < end) {
                    file.read(BUFFER);
                    os.write(BUFFER);
                    pos += BUFFER.length;
                }
                os.flush();
            }
        };

        return mapper.toVDto(response, start, end, f.length());
    }

    VideoDto playVideoHandlerWithS3(PlayVideoReq req) throws FileNotFoundException {
        // from video id -> path videos (sample get on s3)
        String path = "classpath:/videos/example.mp4";
        File f = ResourceUtils.getFile("classpath:videos/example.mp4");
        long start = ofNullable(req.getStart()).orElse(0L);
        long end = ofNullable(req.getEnd()).filter(e -> e < f.length()).orElse(f.length());

        StreamingResponseBody response = (os) -> {
            InputStream inputStream = s3Util.download(req);
            ByteArrayOutputStream bufferedOutputStream = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int nRead;
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                bufferedOutputStream.write(data, 0, nRead);
            }
            bufferedOutputStream.flush();
            byte[] result = new byte[(int) (end - start)];
            System.arraycopy(bufferedOutputStream.toByteArray(), (int) start, result, 0, (int) (end - start));
            return result;

        };

        return mapper.toVDto(response, start, end, f.length());
    }

    @GetMapping(value = "/stream/flux", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Integer> testStream() {
        return sink.asFlux();
    }

    @PostMapping(value = "/stream/flux/push", produces = MediaType.APPLICATION_JSON_VALUE)
    public void pushData() {
        sink.emitNext(1, EmitFailureHandler.FAIL_FAST);
    }

    @RequestMapping("/stream/response-body")
    public ResponseEntity<StreamingResponseBody> handleRequest() {

        StreamingResponseBody stream = out -> {
            String msg = "/srb" + " @ " + LocalDate.now();
            out.write(msg.getBytes());
        };
        return new ResponseEntity(stream, HttpStatus.OK);
    }

    @GetMapping("/stream/response-body-emmitter")
    public ResponseEntity<ResponseBodyEmitter> handleRbe() {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        executor.execute(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    emitter.send("/rbe" + " @ " + LocalDate.now(), MediaType.TEXT_PLAIN);
                    Thread.sleep(100);
                }
                emitter.complete();
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });
        return new ResponseEntity(emitter, HttpStatus.OK);
    }

    @GetMapping("/stream/sse-emitter")
    public SseEmitter handleSse() {
        SseEmitter emitter = new SseEmitter();
        nonBlockingService.execute(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    emitter.send("/sse" + " @ " + LocalDate.now(), MediaType.TEXT_PLAIN);
                    Thread.sleep(5);
                }
                emitter.complete();
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });
        return emitter;
    }
}
