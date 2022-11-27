package com.pm.tin.product;

import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
record ProductReq(@Getter String name, @Getter String description) {
}
