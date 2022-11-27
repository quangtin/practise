package com.pm.tin.entity;

import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Document("product")
@FieldNameConstants
public class Product implements BaseEntity {
    @Id
    private String id;
    private String name;
    private String description;

    private ZonedDateTime createdTime;
    private ZonedDateTime updatedTime;
    private String createdBy;
    private String updatedBy;
    private Long version;

}
