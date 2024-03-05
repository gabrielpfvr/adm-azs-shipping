package br.com.azship.admazsshipping.domain.adapters;

import br.com.azship.admazsshipping.api.dto.PageRequest;
import br.com.azship.admazsshipping.api.dto.ShipmentRequest;
import br.com.azship.admazsshipping.api.dto.ShipmentResponse;
import br.com.azship.admazsshipping.domain.Shipment;
import br.com.azship.admazsshipping.domain.ports.ShipmentRepositoryPort;
import br.com.azship.admazsshipping.domain.ports.ShipmentServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.Base64;

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
        var epochTime = Instant.now().toEpochMilli();
        var epochEncoded = Base64.getEncoder().encodeToString(String.valueOf(epochTime).getBytes());
        var trackingNumber = epochEncoded.substring(0, 8);
        shipment.setTracking(trackingNumber);
    }

    @Override
    public Page<ShipmentResponse> findAll(PageRequest pageRequest) {
        return this.repository.findAll(pageRequest).map(ShipmentResponse::from);
    }

    @Override
    public void updateShipment(String id, ShipmentRequest request) {
        this.repository.update(id, Shipment.from(request));
    }

    @Override
    public void deleteShipment(String id) {
        var shipment = this.repository.findById(id);
        shipment.softDelete();
        this.repository.save(shipment);
    }
}
