package com.smartcart.listitem.infrastructure.out.persistence;

import com.smartcart.listitem.domain.model.Item;
import com.smartcart.listitem.domain.model.ItemStatus;
import com.smartcart.listitem.domain.port.out.ItemRepositoryPort;
import com.smartcart.listitem.infrastructure.out.persistence.entity.ItemEntity;
import com.smartcart.listitem.infrastructure.out.persistence.entity.ShoppingListEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ItemPersistenceAdapter implements ItemRepositoryPort {

    private final ItemJpaRepository itemJpaRepository;
    private final ListJpaRepository listJpaRepository;

    public ItemPersistenceAdapter(ItemJpaRepository itemJpaRepository, ListJpaRepository listJpaRepository) {
        this.itemJpaRepository = itemJpaRepository;
        this.listJpaRepository = listJpaRepository;
    }

    @Override
    public Item save(Item item) {
        ShoppingListEntity list = listJpaRepository.findById(item.getListId())
                .orElseThrow(() -> new IllegalArgumentException("Lista no encontrada con id: " + item.getListId()));
        ItemEntity entity = toEntity(item, list);
        ItemEntity savedEntity = itemJpaRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Item> findById(UUID id) {
        return itemJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Item> findByListIdAndStatus(UUID listId, ItemStatus status) {
        return itemJpaRepository.findByListIdAndStatus(listId, status).stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        itemJpaRepository.deleteById(id);
    }

    private ItemEntity toEntity(Item item, ShoppingListEntity list) {
        ItemEntity e = new ItemEntity();
        e.setId(item.getId());
        e.setName(item.getName());
        e.setCreatedAt(item.getCreatedAt());
        e.setCategory(item.getCategory());
        e.setAddedBy(item.getAddedBy());
        e.setList(list);
        e.setPurchasedAt(item.getPurchasedAt());
        e.setStatusOwner(item.getStatusOwner());
        e.setQuantity(item.getQuantity());
        e.setUnit(item.getUnit());
        e.setStatus(item.getStatus());
        return e;
    }

    private Item toDomain(ItemEntity e) {
        Item item = new Item();
        item.setId(e.getId());
        item.setName(e.getName());
        item.setCreatedAt(e.getCreatedAt());
        item.setCategory(e.getCategory());
        item.setAddedBy(e.getAddedBy());
        item.setListId(e.getList().getId());
        item.setPurchasedAt(e.getPurchasedAt());
        item.setStatusOwner(e.getStatusOwner());
        item.setQuantity(e.getQuantity());
        item.setUnit(e.getUnit());
        item.setStatus(e.getStatus());
        return item;
    }
}
