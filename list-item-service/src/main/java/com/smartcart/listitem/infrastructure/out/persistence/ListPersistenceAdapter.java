package com.smartcart.listitem.infrastructure.out.persistence;

import com.smartcart.listitem.domain.model.ShoppingList;
import com.smartcart.listitem.domain.port.out.ListRepositoryPort;
import com.smartcart.listitem.infrastructure.out.persistence.entity.ShoppingListEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ListPersistenceAdapter implements ListRepositoryPort {

    private final ListJpaRepository jpaRepository;

    public ListPersistenceAdapter(ListJpaRepository jpaRepository) { this.jpaRepository = jpaRepository; }

    @Override
    public ShoppingList save(ShoppingList list) {
        ShoppingListEntity entity = toEntity(list);
        ShoppingListEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<ShoppingList> findById(UUID id) {
        return jpaRepository.findById(id).stream().map(this::toDomain).toList().stream().findFirst();
    }

    @Override
    public List<ShoppingList> findByGroupId(UUID groupId) {
        return jpaRepository.findByGroupId(groupId).stream().map(this::toDomain).toList();
    }

    private ShoppingListEntity toEntity(ShoppingList list) {
        ShoppingListEntity e = new ShoppingListEntity();
        e.setId(list.getId());
        e.setGroupId(list.getGroupId());
        e.setName(list.getName());
        e.setCreatedBy(list.getCreatedBy());
        e.setCreatedAt(list.getCreatedAt());
        e.setArchived(list.isArchived());
        return e;
    }

    private ShoppingList toDomain(ShoppingListEntity e) {
        ShoppingList list = new ShoppingList();
        list.setId(e.getId());
        list.setGroupId(e.getGroupId());
        list.setName(e.getName());
        list.setCreatedBy(e.getCreatedBy());
        list.setCreatedAt(e.getCreatedAt());
        list.setArchived(e.isArchived());
        return list;
    }
}
