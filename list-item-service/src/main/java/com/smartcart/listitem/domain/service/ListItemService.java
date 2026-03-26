package com.smartcart.listitem.domain.service;

import com.smartcart.listitem.domain.model.Item;
import com.smartcart.listitem.domain.model.ItemStatus;
import com.smartcart.listitem.domain.model.ShoppingList;
import com.smartcart.listitem.domain.port.in.ManageItemUseCase;
import com.smartcart.listitem.domain.port.in.ManageListUseCase;
import com.smartcart.listitem.domain.port.out.ItemRepositoryPort;
import com.smartcart.listitem.domain.port.out.ListRepositoryPort;
import com.smartcart.listitem.domain.port.out.NotificationPort;

import java.util.List;
import java.util.UUID;

public class ListItemService implements ManageItemUseCase, ManageListUseCase {

    private final ItemRepositoryPort itemRepository;
    private final ListRepositoryPort listRepository;
    private final NotificationPort notifications;

    public ListItemService(ItemRepositoryPort itemRepository,
                           ListRepositoryPort listRepository,
                           NotificationPort notifications) {
        this.itemRepository = itemRepository;
        this.listRepository = listRepository;
        this.notifications = notifications;
    }

    // ── ManageListUseCase ──

    @Override
    public ShoppingList createList(UUID groupId, String name, UUID createdBy) {
        ShoppingList list = ShoppingList.create(groupId, name, createdBy);
        return listRepository.save(list);
    }

    @Override
    public ShoppingList getListById(UUID listId) {

        return listRepository.findById(listId)
                .orElseThrow(() -> new IllegalArgumentException("Lista no encontrada: " + listId));
    }

    @Override
    public List<ShoppingList> getListsByGroup(UUID groupId) {
        return listRepository.findByGroupId(groupId);
    }

    @Override
    public void archiveList(UUID listId, UUID requestedBy) {
        ShoppingList list = listRepository.findById(listId)
                .orElseThrow(() -> new IllegalArgumentException("Lista no encontrada: " + listId));
        list.setArchived(true);
        listRepository.save(list);
    }

    @Override
    public void unarchiveList(UUID listId, UUID requestedBy) {
        ShoppingList list = listRepository.findById(listId)
                .orElseThrow(() -> new IllegalArgumentException("Lista no encontrada: " + listId));
        list.setArchived(false);
        listRepository.save(list);
    }

    // ── ManageItemUseCase ──

    @Override
    public Item addItem(UUID listId, String name, String category,
                        Integer quantity, String unit, UUID addedBy) {
        listRepository.findById(listId)
                .orElseThrow(() -> new IllegalArgumentException("Lista no encontrada: " + listId));

        Item item = Item.create(listId, name, category, quantity, unit, addedBy);
        Item saved = itemRepository.save(item);
        notifications.notifyItemCreated(getGroupId(listId), saved, addedBy);
        return saved;
    }

    @Override
    public Item markAsPurchased(UUID itemId, UUID userId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Artículo no encontrado: " + itemId));
        item.markAsPurchased(userId);
        Item saved = itemRepository.save(item);
        notifications.notifyItemPurchased(getGroupId(item.getListId()), saved, userId);
        return saved;
    }

    @Override
    public Item returnToWanted(UUID itemId, UUID userId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Artículo no encontrado: " + itemId));
        item.returnToWanted(userId);
        Item saved = itemRepository.save(item);
        notifications.notifyItemReturned(getGroupId(item.getListId()), saved, userId);
        return saved;
    }

    @Override
    public Item putInStorage(UUID itemId, UUID userId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException(("Articulo no encontrado: " + itemId)));
        item.putInStorage(userId);
        Item saved = itemRepository.save(item);
        notifications.notifyItemStored(getGroupId(item.getListId()), saved, userId);
        return saved;
    }

    @Override
    public void deleteItem(UUID itemId, UUID userId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Artículo no encontrado: " + itemId));
        itemRepository.deleteById(itemId);
        notifications.notifyItemDeleted(getGroupId(item.getListId()), item, userId);

    }

    @Override
    public List<Item> getItemsByListAndStatus(UUID listId, ItemStatus status) {
        return itemRepository.findByListIdAndStatus(listId, status);
    }

    private UUID getGroupId(UUID listId) {
        return listRepository.findById(listId)
                .map(ShoppingList::getGroupId)
                .orElseThrow(() -> new IllegalStateException("Lista no encontrada al buscar groupId"));
    }
}
