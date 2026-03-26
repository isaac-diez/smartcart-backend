package com.smartcart.listitem.domain.port.out;

import com.smartcart.listitem.domain.model.Item;

import java.util.UUID;

/**
 * Puerto de salida: notificar cambios a los miembros del grupo.
 * Hoy: llamada HTTP a notification-service.
 * Iteración 3: publicar evento Kafka. El dominio no cambia.
 */
public interface NotificationPort {
    void notifyItemPurchased(UUID groupId, Item item, UUID purchasedBy);
    void notifyItemCreated(UUID groupId, Item item, UUID addedBy);
    void notifyItemReturned(UUID groupId, Item item, UUID returnedBy);
    void notifyItemStored(UUID groupId, Item item, UUID returnedBy);
    void notifyItemWanted(UUID groupId, Item item, UUID returnedBy);
    void notifyItemDeleted(UUID groupId, Item item, UUID returnedBy);

}
