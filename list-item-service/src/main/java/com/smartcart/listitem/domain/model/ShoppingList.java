package com.smartcart.listitem.domain.model;

import java.time.Instant;
import java.util.UUID;

public class ShoppingList {

    private UUID id;
    private UUID groupId;
    private String name;
    private UUID createdBy;
    private Instant createdAt;
    private boolean archived;

    public static ShoppingList create(UUID groupId, String name, UUID createdBy) {
        ShoppingList list = new ShoppingList();
        list.id = UUID.randomUUID();
        list.groupId = groupId;
        list.name = name;
        list.createdBy = createdBy;
        list.createdAt = Instant.now();
        list.archived = false;
        return list;
    }

    public UUID getId()           { return id; }
    public UUID getGroupId()      { return groupId; }
    public String getName()       { return name; }
    public UUID getCreatedBy()    { return createdBy; }
    public Instant getCreatedAt() { return createdAt; }
    public boolean isArchived()   { return archived; }

    public void setId(UUID id)            { this.id = id; }
    public void setGroupId(UUID gid)      { this.groupId = gid; }
    public void setName(String name)      { this.name = name; }
    public void setCreatedBy(UUID id)     { this.createdBy = id; }
    public void setCreatedAt(Instant t)   { this.createdAt = t; }
    public void setArchived(boolean a)    { this.archived = a; }
}
