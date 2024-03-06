package br.com.azship.admazsshipping.api.adapters;

import br.com.azship.admazsshipping.api.dto.PageRequest;
import br.com.azship.admazsshipping.api.dto.ShipmentFilters;
import br.com.azship.admazsshipping.api.dto.ShipmentRequest;
import br.com.azship.admazsshipping.api.dto.ShipmentResponse;
import br.com.azship.admazsshipping.domain.ports.ShipmentServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/shippings")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentServicePort service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createShipment(@Valid @RequestBody ShipmentRequest request) {
        this.service.createShipment(request);
    }

    @GetMapping
    public Page<ShipmentResponse> getAll(ShipmentFilters filters, PageRequest pageable) {
        return this.service.findAll(filters, pageable);
    }

    @PutMapping("{id}/update")
    public void updateShipment(@PathVariable String id, @RequestBody ShipmentRequest request) {
        this.service.updateShipment(id, request);
    }

    @DeleteMapping("{id}/delete")
    public void deleteShipment(@PathVariable String id) {
        this.service.deleteShipment(id);
    }
}
