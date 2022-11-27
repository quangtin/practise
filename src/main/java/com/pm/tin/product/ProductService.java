package com.pm.tin.product;

import com.pm.tin.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ProductService {
    private final ProductMapper mapper;
    private final MongoTemplate template;

    Product create(ProductReq req) {
        return template.insert(mapper.toPE(req));
    }
}
