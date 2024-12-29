package com.mabis.controllers;

import com.mabis.domain.order.*;
import com.mabis.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController
{
    private final OrderService order_service;

    @PostMapping
    public ResponseEntity<List<OrderClientResponseDTO>> place_order(@Valid @RequestBody OrderRequestDTO dto)
    {
        return new ResponseEntity<>(order_service.place_order(dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete_order(@PathVariable UUID id)
    {
        order_service.delete_order(id);
    }

    @PatchMapping("/status")
    public ResponseEntity<String> update_status_order(@Valid @RequestBody UpdateStatusOrderRequestDTO dto)
    {
        return new ResponseEntity<>(order_service.update_status_order(dto).getStatus(), HttpStatus.OK);
    }

    @PatchMapping("/quantity")
    public ResponseEntity<ModifyOrderQuantityResponseDTO> modify_order_quantity(@Valid @RequestBody ModifyOrderQuantityRequestDTO dto)
    {
        return new ResponseEntity<>(order_service.modify_order_quantity(dto), HttpStatus.OK);
    }
}
