package br.com.azship.admazsshipping.api.dto;

import br.com.azship.admazsshipping.domain.enums.Status;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static br.com.azship.admazsshipping.helper.TestsHelper.shipment;
import static org.assertj.core.api.Assertions.assertThat;

class ShipmentResponseTest {

    @Test
    void from_shouldReturnShipmentResponse() {
        assertThat(ShipmentResponse.from(shipment()))
            .extracting("id", "electronicInvoiceNumber", "recipientName", "recipientAddress", "shippingDate", "deliveryDate",
                "cancelationDate", "weight", "dimensions", "cubage", "description", "carrier", "status", "tracking", "costs")
            .containsExactly("1", 1234, "Client 1", "Av. dos Pombos, 32", LocalDateTime.of(2024, 3, 5, 8, 0), null, null,
                20.0, "H: 200.0mm, W: 150.0mm, D: 220.0mm", null, "Box", "GLog", Status.ON_ROUTE, "ABC1234", 5400.0);
    }
}