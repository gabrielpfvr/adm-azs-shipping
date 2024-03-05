package br.com.azship.admazsshipping.api.dto;

import br.com.azship.admazsshipping.domain.Shipment;
import br.com.azship.admazsshipping.domain.enums.Status;
import lombok.Data;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

@Data
public class ShipmentResponse {

    private static final String DIMENSIONS_UNIT = "mm";

    private String id;
    private Integer electronicInvoiceNumber;
    private String recipientName;
    private String recipientAddress;
    private LocalDateTime shippingDate;
    private LocalDateTime deliveryDate;
    private LocalDateTime cancelationDate;
    private Double weight;
    private String dimensions;
    private String cubage;
    private String description;
    private String carrier;
    private Status status;
    private String tracking;
    private Double costs;

    public static ShipmentResponse from(Shipment shipment) {
        var response = new ShipmentResponse();
        response.id = shipment.getId();
        response.electronicInvoiceNumber = shipment.getElectronicInvoiceNumber();
        response.recipientName = shipment.getRecipientName();
        response.recipientAddress = shipment.getRecipientAddress();
        response.shippingDate = shipment.getShippingDate();
        response.deliveryDate = shipment.getDeliveryDate();
        response.cancelationDate = shipment.getCancelationDate();
        response.weight = shipment.getWeight();
        response.description = shipment.getDescription();
        response.carrier = shipment.getCarrier();
        response.status = shipment.getStatus();
        response.tracking = shipment.getTracking();
        response.costs = shipment.getCosts();
        response.formatDimensions(shipment);
        response.setCubage(shipment.getCubage());

        return response;
    }

    private void formatDimensions(Shipment shipment) {
        this.dimensions = "H: " + shipment.getHeight() + DIMENSIONS_UNIT
            + ", W: " + shipment.getWidth() + DIMENSIONS_UNIT
            + ", D: " + shipment.getDepth() + DIMENSIONS_UNIT;
    }

    private void setCubage(Double cubage) {
        if (cubage != null) {
            var df = new DecimalFormat("#.##");
            this.cubage = df.format(cubage) + "kg/m³";
        }
    }
}
