package com.smartcart.listitem.application.dto;

import com.smartcart.listitem.domain.model.ShoppingList;

import java.time.Instant;
import java.util.UUID;

public record ListResponse (
        UUID id,
        UUID groupId,
        String name,
        UUID createdBy,
        Instant createdAt,
        boolean archived
){

    public static ListResponse from(ShoppingList list) {
        return new ListResponse(
                list.getId(),
                list.getGroupId(),
                list.getName(),
                list.getCreatedBy(),
                list.getCreatedAt(),
                list.isArchived()
        );
    }
}
