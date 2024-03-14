package br.com.gabrielmonteiromotta.shippingapi.domain.ports;

import br.com.gabrielmonteiromotta.shippingapi.api.dto.PageRequest;
import br.com.gabrielmonteiromotta.shippingapi.domain.Shipment;
import br.com.gabrielmonteiromotta.shippingapi.infrastructure.adapters.entity.ShipmentEntity;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;

public interface ShipmentRepositoryPort {

    void save(ShipmentEntity shipmentEntity);

    void save(Shipment shipment);

    Page<Shipment> findAll(Predicate predicate, PageRequest pageRequest);

    Shipment findById(String id);

    void update(String id, Shipment shipment);
}
