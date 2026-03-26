package com.smartcart.listitem.domain.model;

import com.smartcart.listitem.infrastructure.out.persistence.entity.ShoppingListEntity;

import java.time.Instant;
import java.util.UUID;

public class Item {

    private UUID id;
    private UUID listId;
    private String name;
    private String category;
    private Integer quantity;
    private String unit;
    private ItemStatus status;
    private UUID addedBy;
    private UUID statusOwner;
    private Instant createdAt;
    private Instant purchasedAt;
    private Instant storedAt;
    private Instant wantedAt;

    public static Item create(UUID listId, String name, String category,
                               Integer quantity, String unit, UUID addedBy) {
        Item item = new Item();
        item.id = UUID.randomUUID();
        item.listId = listId;
        item.name = name;
        item.category = category;
        item.quantity = quantity != null ? quantity : 1;
        item.unit = unit;
        item.status = ItemStatus.WANTED;
        item.addedBy = addedBy;
        item.statusOwner = addedBy;
        item.createdAt = Instant.now();
        return item;
    }

    public void markAsPurchased(UUID statusOwner) {
        if (this.status != ItemStatus.WANTED) {
            throw new IllegalStateException("Solo se pueden comprar artículos en estado WANTED");
        }
        this.status = ItemStatus.PURCHASED;
        this.statusOwner = statusOwner;
        this.purchasedAt = Instant.now();
    }

    public void returnToWanted(UUID statusOwner) {
        if (this.status == ItemStatus.WANTED) {
            throw new IllegalStateException("Solo se pueden devolver artículos en estado PURCHASED");
        }
        this.status = ItemStatus.WANTED;
        this.statusOwner = statusOwner;
        this.wantedAt = Instant.now();
    }

    public void putInStorage(UUID statusOwner) {
        this.status = ItemStatus.STORED;
        this.statusOwner = statusOwner;
        this.storedAt = Instant.now();
    }

    // Getters & Setters
    public UUID getId()              { return id; }
    public UUID getListId()          { return listId; }
    public String getName()          { return name; }
    public String getCategory()      { return category; }
    public Integer getQuantity()     { return quantity; }
    public String getUnit()          { return unit; }
    public ItemStatus getStatus()    { return status; }
    public UUID getAddedBy()         { return addedBy; }
    public UUID getStatusOwner()     { return statusOwner; }
    public Instant getCreatedAt()    { return createdAt; }
    public Instant getPurchasedAt()  { return purchasedAt; }
    public Instant getStoredAt()     { return storedAt; }
    public Instant getWantedAt()     { return wantedAt; }

    public void setId(UUID id)              { this.id = id; }
    public void setListId(UUID listId)      { this.listId = listId; }
    public void setName(String name)        { this.name = name; }
    public void setCategory(String cat)     { this.category = cat; }
    public void setQuantity(Integer qty)    { this.quantity = qty; }
    public void setUnit(String unit)        { this.unit = unit; }
    public void setStatus(ItemStatus s)     { this.status = s; }
    public void setAddedBy(UUID id)         { this.addedBy = id; }
    public void setStatusOwner(UUID id)     { this.statusOwner = id; }
    public void setCreatedAt(Instant t)     { this.createdAt = t; }
    public void setPurchasedAt(Instant t)   { this.purchasedAt = t; }
    public void setStoredAt(Instant t)      { this.storedAt = t; }
    public void setWantedAt(Instant t)      { this.wantedAt = t; }
}
