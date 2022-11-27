package com.pm.tin.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
class MongoDBConfiguration {

    @Bean
    public BeforeConvertCallback<BaseEntity> beforeSaveCallback() {

        return (entity, collection) -> {

            if (entity.getId() == null) {
                entity.setId(UUID.randomUUID().toString());
            }
            return entity;
        };
    }
}
