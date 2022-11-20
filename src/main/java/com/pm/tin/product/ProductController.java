package com.pm.tin.product;

import com.pm.tin.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
class ProductController {
    private final ProductService service;
    private final ProductMapper mapper;
    @PostMapping("/create")
    ResponseEntity<ProductDto> createProduct(@RequestBody ProductReq req) {
        ProductEntity pe = service.create(req);
        return ResponseEntity.ok(mapper.toPDto(pe));
    }
}
