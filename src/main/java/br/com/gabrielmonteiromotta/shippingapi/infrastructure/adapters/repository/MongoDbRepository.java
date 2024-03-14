package br.com.gabrielmonteiromotta.shippingapi.infrastructure.adapters.repository;

import br.com.gabrielmonteiromotta.shippingapi.infrastructure.adapters.entity.ShipmentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MongoDbRepository extends MongoRepository<ShipmentEntity, String>,
    QuerydslPredicateExecutor<ShipmentEntity> {

    boolean existsById(String id);
}
