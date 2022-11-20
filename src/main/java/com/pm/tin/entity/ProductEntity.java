package com.pm.tin.entity;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
//@Document("product")
//@TypeAlias("ProductFund")
@FieldNameConstants
public class ProductEntity extends BaseEntity {
  private String id;
  private String name;
  private String description;
}
