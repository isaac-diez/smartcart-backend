package com.smartcart.listitem.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateListRequest (
        @NotNull
        UUID groupId,
        @NotBlank
        String name
        ) {
}
