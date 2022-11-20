package com.pm.tin.entity;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("product")
@BsonDiscriminator
public interface IProductEntity {
}
