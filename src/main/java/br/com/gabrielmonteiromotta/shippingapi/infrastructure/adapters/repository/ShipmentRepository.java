package br.com.gabrielmonteiromotta.shippingapi.infrastructure.adapters.repository;

import br.com.gabrielmonteiromotta.shippingapi.api.dto.PageRequest;
import br.com.gabrielmonteiromotta.shippingapi.domain.Shipment;
import br.com.gabrielmonteiromotta.shippingapi.domain.ports.ShipmentRepositoryPort;
import br.com.gabrielmonteiromotta.shippingapi.infrastructure.adapters.entity.ShipmentEntity;
import br.com.gabrielmonteiromotta.shippingapi.infrastructure.exception.NotFoundException;
import com.querydsl.core.types.Predicate;
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
        this.mongoRepository.save(shipmentEntity);
    }

    @Override
    public void save(Shipment shipment) {
        var entity = ShipmentEntity.from(shipment);
        this.save(entity);
    }

    @Override
    public Page<Shipment> findAll(Predicate predicate, PageRequest pageRequest) {
        return this.mongoRepository.findAll(predicate, pageRequest).map(Shipment::from);
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
        this.mongoRepository.save(ShipmentEntity.from(shipment));
    }
}
