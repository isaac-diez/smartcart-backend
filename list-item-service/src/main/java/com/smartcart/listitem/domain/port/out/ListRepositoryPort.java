package com.smartcart.listitem.domain.port.out;

import com.smartcart.listitem.domain.model.ShoppingList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ListRepositoryPort {
    ShoppingList save(ShoppingList list);
    Optional<ShoppingList> findById(UUID id);
    List<ShoppingList> findByGroupId(UUID groupId);
}
