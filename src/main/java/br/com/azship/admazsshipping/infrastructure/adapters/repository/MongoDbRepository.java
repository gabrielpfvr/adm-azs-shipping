package br.com.azship.admazsshipping.infrastructure.adapters.repository;

import br.com.azship.admazsshipping.infrastructure.adapters.entity.ShipmentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoDbRepository extends MongoRepository<ShipmentEntity, String> {

    boolean existsById(String id);
}
