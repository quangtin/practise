package com.pm.tin.product;

import com.pm.tin.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ProductService {
    private final ProductMapper mapper;
    ProductEntity create(ProductReq req) {
        ProductEntity pe = mapper.toPE(req);
        return pe;
    }
}
