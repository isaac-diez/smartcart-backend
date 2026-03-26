package com.smartcart.listitem.infrastructure.in.rest;

import com.smartcart.listitem.application.dto.CreateListRequest;
import com.smartcart.listitem.application.dto.ListResponse;
import com.smartcart.listitem.domain.port.in.ManageListUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/lists")
public class ShoppingListController {

    private final ManageListUseCase manageList;

    public ShoppingListController(ManageListUseCase manageList) {
        this.manageList = manageList;
    }

    @PostMapping("/create")
    public ResponseEntity<ListResponse> createList(
            @Valid @RequestBody CreateListRequest request,
            @AuthenticationPrincipal UUID createdBy) {

        var list = manageList.createList(request.groupId(), request.name(), createdBy);
        return ResponseEntity.status(HttpStatus.CREATED).body(ListResponse.from(list));
    }

    @GetMapping("/{listId}")
    public ResponseEntity<ListResponse> getListById(
            @PathVariable UUID listId) {
        var list = manageList.getListById(listId);
        return ResponseEntity.status(HttpStatus.OK).body(ListResponse.from(list));
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<ListResponse>> getListsByGroup(
            @PathVariable UUID groupId) {
        var lists = manageList.getListsByGroup(groupId);
        return ResponseEntity.ok(lists.stream().map(ListResponse::from).toList());
    }

    @PutMapping("/{listId}/archive")
    public ResponseEntity<Void> archiveList(
            @PathVariable UUID listId,
            @AuthenticationPrincipal UUID userId) {

        manageList.archiveList(listId, userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{listId}/unarchive")
    public ResponseEntity<Void> unarchiveList(
            @PathVariable UUID listId,
            @AuthenticationPrincipal UUID userId) {

        manageList.unarchiveList(listId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("List-service OK");
    }
}