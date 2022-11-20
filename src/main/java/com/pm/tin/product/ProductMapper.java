package com.pm.tin.product;

import com.pm.tin.entity.ProductEntity;
import com.pm.tin.util.CommonMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfig.class)
interface ProductMapper {
    ProductEntity toPE(ProductReq req);

    ProductDto toPDto(ProductEntity pe);
}
