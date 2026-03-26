package com.smartcart.listitem.domain.port.in;

import com.smartcart.listitem.domain.model.Item;
import com.smartcart.listitem.domain.model.ItemStatus;
import java.util.List;
import java.util.UUID;

public interface ManageItemUseCase {
    Item addItem(UUID listId, String name, String category, Integer quantity, String unit, UUID addedBy);
    Item markAsPurchased(UUID itemId, UUID userId);
    Item returnToWanted(UUID itemId, UUID userId);
    Item putInStorage(UUID itemId, UUID userId);
    void deleteItem(UUID itemId, UUID userId);
    List<Item> getItemsByListAndStatus(UUID listId, ItemStatus status);
}
