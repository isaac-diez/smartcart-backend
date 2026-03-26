package com.smartcart.listitem.infrastructure.config;

import com.smartcart.listitem.domain.port.out.ItemRepositoryPort;
import com.smartcart.listitem.domain.port.out.ListRepositoryPort;
import com.smartcart.listitem.domain.port.out.NotificationPort;
import com.smartcart.listitem.domain.service.ListItemService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Aquí Spring aprende cómo construir el dominio.
 * El dominio nunca sabe que Spring existe.
 */
@Configuration
public class DomainConfig {

    @Bean
    public ListItemService listItemService(
            ItemRepositoryPort itemRepository,
            ListRepositoryPort listRepository,
            NotificationPort notifications) {
        return new ListItemService(itemRepository, listRepository, notifications);
    }
}
