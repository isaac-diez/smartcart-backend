package com.smartcart.listitem.domain.port.out;

import com.smartcart.listitem.domain.model.Item;
import com.smartcart.listitem.domain.model.ItemStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemRepositoryPort {
    Item save(Item item);
    Optional<Item> findById(UUID id);
    List<Item> findByListIdAndStatus(UUID listId, ItemStatus status);
    void deleteById(UUID id);
}
