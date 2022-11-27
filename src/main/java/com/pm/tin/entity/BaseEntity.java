package com.pm.tin.entity;

import org.springframework.data.annotation.*;

import java.time.ZonedDateTime;

public interface BaseEntity {
    @Id
    String getId();

    void setId(String id);

    @Version
    Long getVersion();

    //    @CreatedBy
    String getCreatedBy();

    //    @LastModifiedBy
    String getUpdatedBy();

    @CreatedDate
    ZonedDateTime getCreatedTime();

    @LastModifiedDate
    ZonedDateTime getUpdatedTime();
}
