package br.com.azship.admazsshipping.helper;

import br.com.azship.admazsshipping.api.dto.PageRequest;
import br.com.azship.admazsshipping.api.dto.ShipmentRequest;
import br.com.azship.admazsshipping.domain.Shipment;
import br.com.azship.admazsshipping.domain.enums.Status;
import br.com.azship.admazsshipping.infrastructure.adapters.entity.ShipmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;

public class TestsHelper {

    public static ShipmentRequest shipmentRequest() {
        return new ShipmentRequest(
            12345,
            "Josivaldo",
            "Rua das Flores, 15 - Sao Paulo - SP",
            10.0,
            100.5,
            200.0,
            25.2,
            null,
            "Package",
            "XYZ Logistics",
            2000.0
        );
    }

    public static ShipmentRequest nullShipmentRequest() {
        return new ShipmentRequest(null, null, null, null, null, null, null, null, null, null, null);
    }

    public static Shipment shipment() {
        return Shipment.builder()
            .id("1")
            .electronicInvoiceNumber(1234)
            .recipientName("Client 1")
            .recipientAddress("Av. dos Pombos, 32")
            .shippingDate(LocalDateTime.of(2024, 3, 5, 8, 0))
            .weight(20.0)
            .height(200.0)
            .width(150.0)
            .depth(220.0)
            .description("Box")
            .carrier("GLog")
            .status(Status.ON_ROUTE)
            .tracking("ABC1234")
            .costs(5400.0)
            .build();
    }

    public static ShipmentEntity shipmentEntity() {
        return ShipmentEntity.builder()
            .id("1")
            .electronicInvoiceNumber(1234)
            .recipientName("Client 1")
            .recipientAddress("Av. dos Pombos, 32")
            .shippingDate(LocalDateTime.of(2024, 3, 5, 8, 0))
            .weight(20.0)
            .height(200.0)
            .width(150.0)
            .depth(220.0)
            .description("Box")
            .carrier("GLog")
            .status(Status.ON_ROUTE)
            .tracking("ABC1234")
            .costs(5400.0)
            .build();
    }

    public static Page<Shipment> shipmentPage() {
        return new PageImpl<>(
            List.of(shipment()),
            new PageRequest(),
            1
        );
    }
}
