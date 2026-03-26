package com.smartcart.listitem.domain.port.in;

import com.smartcart.listitem.domain.model.ShoppingList;
import java.util.List;
import java.util.UUID;

public interface ManageListUseCase {
    ShoppingList createList(UUID groupId, String name, UUID createdBy);
    ShoppingList getListById(UUID listId);
    List<ShoppingList> getListsByGroup(UUID groupId);
    void archiveList(UUID listId, UUID requestedBy);
    void unarchiveList(UUID listId, UUID requestedBy);
}
