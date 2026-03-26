package com.smartcart.listitem.infrastructure.out.persistence.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ShoppingLists", schema = "listitem_svc")
public class ShoppingListEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "group_id", nullable = false, columnDefinition = "uuid")
    private UUID groupId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "created_by", nullable = false, columnDefinition = "uuid")
    private UUID createdBy;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private boolean archived;

    @OneToMany(mappedBy = "list", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemEntity> items = new ArrayList<>();

    public UUID getId()                              { return id; }
    public void setId(UUID id)                       { this.id = id; }
    public UUID getGroupId()                         { return groupId; }
    public void setGroupId(UUID groupId)             { this.groupId = groupId; }
    public String getName()                          { return name; }
    public void setName(String name)                 { this.name = name; }
    public UUID getCreatedBy()                       { return createdBy; }
    public void setCreatedBy(UUID createdBy)         { this.createdBy = createdBy; }
    public Instant getCreatedAt()                    { return createdAt;}
    public void setCreatedAt(Instant createdAt)      { this.createdAt = createdAt; }
    public boolean isArchived()                      { return archived; }
    public void setArchived(boolean archived)        { this.archived = archived; }
    public List<ItemEntity> getItems()               { return items; }
    public void setItems()                           { this.items = items;}

}
