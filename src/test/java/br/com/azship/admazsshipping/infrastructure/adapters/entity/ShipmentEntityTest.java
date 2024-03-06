package br.com.azship.admazsshipping.infrastructure.adapters.entity;

import org.junit.jupiter.api.Test;

import static br.com.azship.admazsshipping.helper.TestsHelper.shipment;
import static br.com.azship.admazsshipping.helper.TestsHelper.shipmentEntity;
import static org.assertj.core.api.Assertions.assertThat;

class ShipmentEntityTest {

    @Test
    void from_shouldReturnShipmentEntityFromDomainClass() {
        assertThat(ShipmentEntity.from(shipment()))
            .isEqualTo(shipmentEntity());
    }
}