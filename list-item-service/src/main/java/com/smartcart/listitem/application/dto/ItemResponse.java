package com.smartcart.listitem.application.dto;

import com.smartcart.listitem.domain.model.Item;
import com.smartcart.listitem.domain.model.ItemStatus;

import java.time.Instant;
import java.util.UUID;

public record ItemResponse (
        UUID id,
        UUID listId,
        String name,
        String category,
        Integer quantity,
        String unit,
        ItemStatus status,
        UUID addedBy,
        UUID statusOwner,
        Instant addedAt,
        Instant purchasedAt,
        Instant storedAt,
        Instant wantedAt
) {

    public static ItemResponse from(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getListId(),
                item.getName(),
                item.getCategory(),
                item.getQuantity(),
                item.getUnit(),
                item.getStatus(),
                item.getAddedBy(),
                item.getStatusOwner(),
                item.getCreatedAt(),
                item.getPurchasedAt(),
                item.getStoredAt(),
                item.getWantedAt()
        );
    }
}
