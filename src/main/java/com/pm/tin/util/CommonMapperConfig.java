package com.pm.tin.util;

import org.mapstruct.MapperConfig;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.CollectionMappingStrategy.ADDER_PREFERRED;
import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG;
import static org.mapstruct.ReportingPolicy.IGNORE;

@SuppressWarnings("rawtypes")
@MapperConfig(componentModel = "spring",
        unmappedTargetPolicy = IGNORE,
        injectionStrategy = CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = ADDER_PREFERRED,
        mappingInheritanceStrategy = AUTO_INHERIT_FROM_CONFIG)
public interface CommonMapperConfig {
}
