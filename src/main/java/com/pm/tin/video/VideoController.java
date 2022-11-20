package com.pm.tin.video;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("videos")
public class VideoController {

    @PostMapping(value = "/uploads")
    ResponseEntity<Void> upLoadVideo(VideoRequest req) {

        return ResponseEntity.ok(null);
    }
}
