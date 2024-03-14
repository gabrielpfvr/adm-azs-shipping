package br.com.gabrielmonteiromotta.shippingapi.domain.ports;

import br.com.gabrielmonteiromotta.shippingapi.api.dto.PageRequest;
import br.com.gabrielmonteiromotta.shippingapi.api.dto.ShipmentFilters;
import br.com.gabrielmonteiromotta.shippingapi.api.dto.ShipmentRequest;
import br.com.gabrielmonteiromotta.shippingapi.api.dto.ShipmentResponse;
import org.springframework.data.domain.Page;

public interface ShipmentServicePort {

    void createShipment(ShipmentRequest request);

    Page<ShipmentResponse> findAll(ShipmentFilters filters, PageRequest pageRequest);

    void updateShipment(String id, ShipmentRequest request);

    void deleteShipment(String id);
}
