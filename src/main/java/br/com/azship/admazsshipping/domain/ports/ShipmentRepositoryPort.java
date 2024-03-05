package br.com.azship.admazsshipping.domain.ports;

import br.com.azship.admazsshipping.api.dto.PageRequest;
import br.com.azship.admazsshipping.domain.Shipment;
import br.com.azship.admazsshipping.infrastructure.adapters.entity.ShipmentEntity;
import org.springframework.data.domain.Page;

public interface ShipmentRepositoryPort {

    void save(ShipmentEntity shipmentEntity);

    void save(Shipment shipment);

    Page<Shipment> findAll(PageRequest pageRequest);

    Shipment findById(String id);

    void update(String id, Shipment shipment);
}
