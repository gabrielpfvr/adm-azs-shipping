package br.com.gabrielmonteiromotta.shippingapi.infrastructure.adapters.repository;

import br.com.gabrielmonteiromotta.shippingapi.domain.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ShipmentPredicateTest {

    private static final LocalDate startDate = LocalDate.of(2024, 3, 4);
    private static final LocalDate endDate = LocalDate.of(2024, 3, 6);

    private ShipmentPredicate shipmentPredicate;

    @BeforeEach
    void setUp() {
        shipmentPredicate = new ShipmentPredicate();
    }

    @Test
    void electronicInvoicePredicate_shouldReturnPredicate() {
        assertThat(shipmentPredicate.electronicInvoicePredicate(123).build())
            .hasToString("shipmentEntity.electronicInvoiceNumber = 123");
    }

    @Test
    void recipientNamePredicate_shouldReturnPredicate() {
        assertThat(shipmentPredicate.recipientNamePredicate("Vasco").build())
            .hasToString("containsIc(shipmentEntity.recipientName,Vasco)");
    }

    @Test
    void shippingDatePredicate_shouldReturnPredicate() {
        assertThat(shipmentPredicate.shippingDatePredicate(startDate, endDate).build())
            .hasToString("shipmentEntity.shippingDate between 2024-03-04T00:00 and 2024-03-06T23:59:59.999999999");
    }

    @Test
    void betweenWeightsPredicate_shouldReturnPredicate() {
        assertThat(shipmentPredicate.betweenWeightsPredicate(10.0, 20.0).build())
            .hasToString("shipmentEntity.weight between 10.0 and 20.0");
    }

    @Test
    void cubageFactorPredicate_shouldReturnPredicate() {
        assertThat(shipmentPredicate.cubageFactorPredicate(300.0).build())
            .hasToString("shipmentEntity.cubageFactor = 300.0");
    }

    @Test
    void statusPredicate_shouldReturnPredicate() {
        assertThat(shipmentPredicate.statusPredicate(Status.ON_ROUTE).build())
            .hasToString("shipmentEntity.status = ON_ROUTE");
    }

    @Test
    void trackingPredicateshouldReturnPredicate() {
        assertThat(shipmentPredicate.trackingPredicate("AB12C3").build())
            .hasToString("eqIc(shipmentEntity.tracking,AB12C3)");
    }
}