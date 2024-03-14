package br.com.gabrielmonteiromotta.shippingapi.domain.adapters;

import br.com.gabrielmonteiromotta.shippingapi.api.dto.PageRequest;
import br.com.gabrielmonteiromotta.shippingapi.api.dto.ShipmentFilters;
import br.com.gabrielmonteiromotta.shippingapi.api.dto.ShipmentRequest;
import br.com.gabrielmonteiromotta.shippingapi.api.dto.ShipmentResponse;
import br.com.gabrielmonteiromotta.shippingapi.domain.Shipment;
import br.com.gabrielmonteiromotta.shippingapi.domain.ports.ShipmentRepositoryPort;
import br.com.gabrielmonteiromotta.shippingapi.domain.ports.ShipmentServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentServicePort {

    private final ShipmentRepositoryPort repository;

    @Override
    public void createShipment(ShipmentRequest request) {
        var shipment = Shipment.from(request);
        this.generateTrackingNumber(shipment);
        this.repository.save(shipment);
    }

    private void generateTrackingNumber(Shipment shipment) {
        log.info("Generating tracking number...");
        var epochTime = Instant.now().toEpochMilli();
        var epochEncoded = Base64.getEncoder().encodeToString(String.valueOf(epochTime).getBytes());
        var trackingNumber = epochEncoded.substring(0, 8);
        shipment.setTracking(trackingNumber);
    }

    @Override
    public Page<ShipmentResponse> findAll(ShipmentFilters filters, PageRequest pageRequest) {
        return this.repository.findAll(filters.shipmentPredicate().build(), pageRequest)
            .map(ShipmentResponse::from);
    }

    @Override
    public void updateShipment(String id, ShipmentRequest request) {
        this.repository.update(id, Shipment.from(request));
    }

    @Override
    public void deleteShipment(String id) {
        var shipment = this.repository.findById(id);
        log.info("Performing shipment soft delete...");
        shipment.softDelete();
        this.repository.save(shipment);
    }
}
