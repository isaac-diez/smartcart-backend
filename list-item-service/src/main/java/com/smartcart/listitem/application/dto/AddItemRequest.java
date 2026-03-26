package com.smartcart.listitem.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddItemRequest (
    @NotNull
    UUID listId,
    @NotBlank
    String name,
    String category,
    Integer quantity,
    String unit
) {}

