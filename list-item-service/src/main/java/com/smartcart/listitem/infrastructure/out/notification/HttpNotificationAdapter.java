package com.smartcart.listitem.infrastructure.out.notification;

import com.smartcart.listitem.domain.model.Item;
import com.smartcart.listitem.domain.port.out.NotificationPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HttpNotificationAdapter implements NotificationPort {

    private static final Logger log = LoggerFactory.getLogger(HttpNotificationAdapter.class);

    @Override
    public void notifyItemPurchased(UUID groupId, Item item, UUID purchasedBy) {
        log.info(
                "TODO notify - item purchased: {} in group: {} by: {}",
                item.getId(),
                groupId,
                purchasedBy);
    }

    @Override
    public void notifyItemCreated(UUID groupId, Item item, UUID createdBy) {
        log.info(
                "TODO notify - item added: {} in group: {} by: {}",
                item.getId(),
                groupId,
                createdBy
        );
    }

    @Override
    public void notifyItemReturned(UUID groupId, Item item, UUID returnedBy) {
        log.info(
                "TODO notify - item returned to wanted: {} in group: {} by: {}",
                item.getId(),
                groupId,
                returnedBy
        );
    }

    @Override
    public void notifyItemStored(UUID groupId, Item item, UUID storedBy) {
        log.info(
                "TODO notify - item stored: {} in group: {} by: {}",
                item.getId(),
                groupId,
                storedBy
        );
    }

    @Override
    public void notifyItemWanted(UUID groupId, Item item, UUID wantedBy) {
        log.info(
                "TODO notify - item wanted from stored: {} in group: {} by: {}",
                item.getId(),
                groupId,
                wantedBy
        );
    }

    @Override
    public void notifyItemDeleted(UUID groupId, Item item, UUID deletedBy) {
        log.info(
                "TODO notify - item deleted: {} in group: {} by: {}",
                item.getId(),
                groupId,
                deletedBy
        );
    }
}
