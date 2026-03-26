package com.smartcart.listitem.infrastructure.in.rest;

import com.smartcart.listitem.application.dto.AddItemRequest;
import com.smartcart.listitem.application.dto.ItemResponse;
import com.smartcart.listitem.domain.model.ItemStatus;
import com.smartcart.listitem.domain.port.in.ManageItemUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ManageItemUseCase manageItem;

    public ItemController(ManageItemUseCase manageItem) {
        this.manageItem = manageItem;
    }

    @PostMapping("/add")
    public ResponseEntity<ItemResponse> addItem(
            @Valid @RequestBody AddItemRequest request,
            @RequestHeader("X-User-Id") UUID addedBy) {

        var item = manageItem.addItem(
                request.listId(),
                request.name(),
                request.category(),
                request.quantity(),
                request.unit(),
                addedBy);

        return ResponseEntity.status(HttpStatus.CREATED).body(ItemResponse.from(item));
    }

    @PutMapping("/{itemId}/purchase")
    public ResponseEntity<Void> markAsPurchased(
            @PathVariable UUID itemId,
            @RequestHeader("X-User-Id") UUID userId) {

        manageItem.markAsPurchased(itemId, userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{itemId}/want")
    public ResponseEntity<Void> returnToWanted(
            @PathVariable UUID itemId,
            @RequestHeader("X-User-Id") UUID userId) {

        manageItem.returnToWanted(itemId, userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{itemId}/store")
    public ResponseEntity<Void> putInStorage(
            @PathVariable UUID itemId,
            @RequestHeader("X-User-Id") UUID userId) {

        manageItem.putInStorage(itemId, userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{itemId}/delete")
    public ResponseEntity<Void> deleteItem(
            @PathVariable UUID itemId,
            @RequestHeader("X-User-Id") UUID userId) {

        manageItem.deleteItem(itemId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getbylistandstatus")
    public ResponseEntity<List<ItemResponse>> getItemsByListAndStatus(
            @RequestParam UUID listId,
            @RequestParam ItemStatus status) {

        var items = manageItem.getItemsByListAndStatus(listId, status);
        return ResponseEntity.ok(items.stream().map(ItemResponse::from).toList());
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Item-service OK");
    }

}
