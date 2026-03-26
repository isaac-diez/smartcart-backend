package com.smartcart.listitem.infrastructure.out.persistence;

import com.smartcart.listitem.infrastructure.out.persistence.entity.ShoppingListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ListJpaRepository extends JpaRepository<ShoppingListEntity, UUID> {
    List<ShoppingListEntity> findByGroupId(UUID groupId);
}
