package br.com.azship.admazsshipping.infrastructure.adapters.repository;

import br.com.azship.admazsshipping.api.dto.PageRequest;
import br.com.azship.admazsshipping.domain.Shipment;
import br.com.azship.admazsshipping.domain.ports.ShipmentRepositoryPort;
import br.com.azship.admazsshipping.infrastructure.adapters.entity.ShipmentEntity;
import br.com.azship.admazsshipping.infrastructure.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ShipmentRepository implements ShipmentRepositoryPort {

    private static final String NOT_FOUND_MESSAGE = "Shipment not found!";

    private final MongoDbRepository mongoRepository;

    @Override
    public void save(ShipmentEntity shipmentEntity) {
        this.mongoRepository.insert(shipmentEntity);
    }

    @Override
    public void save(Shipment shipment) {
        var entity = ShipmentEntity.from(shipment);
        this.save(entity);
    }

    @Override
    public Page<Shipment> findAll(PageRequest pageRequest) {
        return this.mongoRepository.findAll(pageRequest).map(Shipment::from);
    }

    @Override
    public Shipment findById(String id) {
        return this.mongoRepository.findById(id).map(Shipment::from)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND_MESSAGE));
    }

    @Override
    public void update(String id, Shipment shipment) {
        if (!this.mongoRepository.existsById(id)) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        shipment.setId(id);
        this.save(ShipmentEntity.from(shipment));
    }
}
