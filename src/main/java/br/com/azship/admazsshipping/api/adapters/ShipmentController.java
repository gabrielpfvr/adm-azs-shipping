package br.com.azship.admazsshipping.api.adapters;

import br.com.azship.admazsshipping.api.dto.PageRequest;
import br.com.azship.admazsshipping.api.dto.ShipmentFilters;
import br.com.azship.admazsshipping.api.dto.ShipmentRequest;
import br.com.azship.admazsshipping.api.dto.ShipmentResponse;
import br.com.azship.admazsshipping.domain.ports.ShipmentServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/shippings")
@Slf4j
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentServicePort service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createShipment(@Valid @RequestBody ShipmentRequest request) {
        log.info("Creating shipment...");
        this.service.createShipment(request);
        log.info("Shipment saved sucessfully.");
    }

    @GetMapping
    public Page<ShipmentResponse> getAll(ShipmentFilters filters, PageRequest pageable) {
        log.info("Listing all shipments based on filters...");
        return this.service.findAll(filters, pageable);
    }

    @PutMapping("{id}/update")
    public void updateShipment(@PathVariable String id, @RequestBody ShipmentRequest request) {
        log.info("Updating shipment...");
        this.service.updateShipment(id, request);
        log.info("Shipment updated sucessfully.");
    }

    @DeleteMapping("{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteShipment(@PathVariable String id) {
        log.info("Deleting shipment...");
        this.service.deleteShipment(id);
        log.info("Shipment deleted sucessfully.");
    }
}
