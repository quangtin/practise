package com.pm.tin.product;

import com.pm.tin.entity.Product;
import com.pm.tin.util.CommonMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
interface ProductMapper {
    Product toPE(ProductReq req);

    ProductDto toPDto(Product pe);
}
