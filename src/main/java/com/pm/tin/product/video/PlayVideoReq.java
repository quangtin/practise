package com.pm.tin.product.video;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
class PlayVideoReq {
    String id;
    Long start;
    Long end;
//    Range range;
//
//    static class Range {
//        Long start;
//        Long end;
//    }
}
