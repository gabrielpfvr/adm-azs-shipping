package br.com.azship.admazsshipping.domain.ports;

import br.com.azship.admazsshipping.api.dto.PageRequest;
import br.com.azship.admazsshipping.api.dto.ShipmentFilters;
import br.com.azship.admazsshipping.api.dto.ShipmentRequest;
import br.com.azship.admazsshipping.api.dto.ShipmentResponse;
import org.springframework.data.domain.Page;

public interface ShipmentServicePort {

    void createShipment(ShipmentRequest request);

    Page<ShipmentResponse> findAll(ShipmentFilters filters, PageRequest pageRequest);

    void updateShipment(String id, ShipmentRequest request);

    void deleteShipment(String id);
}
