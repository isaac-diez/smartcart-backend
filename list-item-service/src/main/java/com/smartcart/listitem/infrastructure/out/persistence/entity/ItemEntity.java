package com.smartcart.listitem.infrastructure.out.persistence.entity;

import com.smartcart.listitem.domain.model.ItemStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "items", schema = "listitem_svc")
public class ItemEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id", nullable = false)
    private ShoppingListEntity list;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(nullable = false, length = 10)
    private Integer quantity;

    @Column(length = 20)
    private String unit;

    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @Column(name = "added_by", nullable = false, columnDefinition = "uuid")
    private UUID addedBy;

    @Column(name = "status_owner", columnDefinition = "uuid")
    private UUID statusOwner;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "purchased_at")
    private Instant purchasedAt;

    @Column(name = "stored_at")
    private Instant storedAt;

    @Column(name = "wanted_at")
    private Instant wantedAt;

    public UUID getId()                              { return id; }
    public void setId(UUID id)                       { this.id = id; }
    public ShoppingListEntity getList()              { return list; }
    public void setList(ShoppingListEntity list)     { this.list = list; }
    public String getName()                          { return name; }
    public void setName(String name)                 { this.name = name; }
    public String getCategory()                      { return category;}
    public void setCategory(String category)         { this.category = category; }
    public Integer getQuantity()                     { return quantity;}
    public void setQuantity(Integer quantity)        { this.quantity = quantity; }
    public String getUnit()                          { return unit; }
    public void setUnit(String unit)                 { this.unit = unit; }
    public ItemStatus getStatus()                    { return status; }
    public void setStatus(ItemStatus status)         { this.status = status; }
    public UUID getAddedBy()                         { return addedBy; }
    public void setAddedBy(UUID addedBy)             { this.addedBy = addedBy;}
    public Instant getPurchasedAt()                     { return purchasedAt; }
    public void setPurchasedAt(Instant purchasedAt)     { this.purchasedAt = purchasedAt; }
    public Instant getCreatedAt()                    { return createdAt; }
    public void setCreatedAt(Instant createdAt)      { this.createdAt = createdAt; }
    public Instant getStoredAt()                     { return storedAt; }
    public void setStoredAt(Instant storedAt)        { this.storedAt = storedAt; }
    public Instant getWantedAt()                     { return wantedAt; }
    public void setWantedAt(Instant wantedAt)        { this.wantedAt = createdAt; }
    public UUID getStatusOwner ()                    { return statusOwner; }
    public void setStatusOwner(UUID statusOwner)     { this.statusOwner = statusOwner; }
}
