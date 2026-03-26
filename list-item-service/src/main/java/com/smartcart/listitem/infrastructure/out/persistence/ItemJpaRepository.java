package com.smartcart.listitem.infrastructure.out.persistence;

import com.smartcart.listitem.domain.model.ItemStatus;
import com.smartcart.listitem.infrastructure.out.persistence.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ItemJpaRepository extends JpaRepository<ItemEntity, UUID> {
    List<ItemEntity> findByListIdAndStatus(UUID listId, ItemStatus status);
}
