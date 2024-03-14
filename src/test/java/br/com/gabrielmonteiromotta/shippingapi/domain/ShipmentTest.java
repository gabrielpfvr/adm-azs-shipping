package br.com.gabrielmonteiromotta.shippingapi.domain;

import br.com.gabrielmonteiromotta.shippingapi.domain.enums.Status;
import org.junit.jupiter.api.Test;

import java.time.temporal.ChronoUnit;

import static br.com.gabrielmonteiromotta.shippingapi.helper.TestsHelper.*;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.*;

class ShipmentTest {

    @Test
    void from_shouldReturnShipmentFromRequest() {
        var shipment = Shipment.from(shipmentRequest());
        assertThat(shipment)
            .extracting("id", "electronicInvoiceNumber", "recipientName", "recipientAddress", "deliveryDate", "cancelationDate",
                "weight", "height", "width", "depth", "cubageFactor", "cubage", "description", "carrier", "status", "tracking",
                "costs")
            .containsExactly(null, 12345, "Josivaldo", "Rua das Flores, 15 - Sao Paulo - SP", null, null, 10.0, 100.5, 200.0,
                25.2, null, null, "Package", "XYZ Logistics", Status.CREATED, null, 2000.0);

        assertThat(shipment.getShippingDate()).isCloseTo(now(), within(1, ChronoUnit.MINUTES));
    }

    @Test
    void from_shouldReturnShipmentFromEntity() {
        assertThat(Shipment.from(shipmentEntity()))
            .isEqualTo(shipment());
    }

    @Test
    void setCubage_shouldCalculateCubageBasedOnDimensionsAndCubageFactor() {
        var shipment = shipment();
        shipment.setCubageFactor(300.0);
        shipment.setCubage();

        assertEquals(1.98, shipment.getCubage());
    }

    @Test
    void softDelete_shouldSetCancelationDateAndChangeStatus() {
        var shipment = shipment();
        shipment.softDelete();

        assertThat(shipment.getCancelationDate()).isCloseTo(now(), within(1, ChronoUnit.MINUTES));
        assertEquals(Status.CANCELLED, shipment.getStatus());
    }
}