package com.pm.tin.product;

import com.pm.tin.entity.ProductEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-21T05:13:12+0700",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductEntity toPE(ProductReq req) {
        if ( req == null ) {
            return null;
        }

        ProductEntity productEntity = new ProductEntity();

        return productEntity;
    }

    @Override
    public ProductDto toPDto(ProductEntity pe) {
        if ( pe == null ) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        return productDto;
    }
}
