package com.pm.tin.product.video;

import jakarta.validation.constraints.Max;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("videos")
public class VideoController {

    @PostMapping(value = "/uploads")
    ResponseEntity<Void> upLoadVideo(@RequestBody VideoRequest req) {
        return ResponseEntity.ok(null);
    }
}
