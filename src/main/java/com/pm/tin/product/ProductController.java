package com.pm.tin.product;

import com.pm.tin.entity.Product;
import com.pm.tin.util.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Validated
class ProductController {
    private final ProductService service;
    private final ProductMapper mapper;
    
    @PostMapping(
            value = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Mono<ResponseEntity<ProductDto>> createProduct(@RequestBody ProductReq req) {
        Product pe = service.create(req);
//        return ResponseEntity.ok(mapper.toPDto)(pe);
        return Mono.just(ResponseEntity.ok(mapper.toPDto(pe)));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProductDto> getProduct(@PathVariable("id") String id) {
        throw new DataNotFoundException("0000001", id);
    }


}
