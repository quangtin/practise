package com.pm.tin.product.video;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpHeaders.ACCEPT_RANGES;
import static org.springframework.http.HttpHeaders.CONTENT_LENGTH;
import static org.springframework.http.HttpHeaders.CONTENT_RANGE;
import static org.springframework.http.HttpStatus.PARTIAL_CONTENT;

@RestController
@RequestMapping("videos")
@RequiredArgsConstructor
public class VideoController {
    
    private final VideoMapper mapper;
    public static final String READ = "r";
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private byte[] BUFFER = new byte[1024];
    
    @PostMapping(value = "/uploads")
    ResponseEntity<Void> upLoadVideo(@RequestBody VideoRequest req) {
        
        return ResponseEntity.ok(null);
    }
    
    @GetMapping(
            value = "/play",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<StreamingResponseBody> play(@RequestParam PlayVideoReq req) throws FileNotFoundException {
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
    
    @GetMapping(
            value = "/play/example",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<StreamingResponseBody> playI() throws FileNotFoundException {
        // read file
        VideoDto response = playVideoHandler(PlayVideoReq.builder().build());
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
    
    private ExecutorService nonBlockingService = Executors.newCachedThreadPool();
    
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
