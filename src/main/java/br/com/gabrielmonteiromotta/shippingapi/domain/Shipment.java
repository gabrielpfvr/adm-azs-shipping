package br.com.gabrielmonteiromotta.shippingapi.domain;

import br.com.gabrielmonteiromotta.shippingapi.api.dto.ShipmentRequest;
import br.com.gabrielmonteiromotta.shippingapi.domain.enums.Status;
import br.com.gabrielmonteiromotta.shippingapi.infrastructure.adapters.entity.ShipmentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {

    private String id;
    private Integer electronicInvoiceNumber;
    private String recipientName;
    private String recipientAddress;
    private LocalDateTime shippingDate;
    private LocalDateTime deliveryDate;
    private LocalDateTime cancelationDate;
    private Double weight;
    private Double height;
    private Double width;
    private Double depth;
    private Double cubageFactor;
    private Double cubage;
    private String description;
    private String carrier;
    private Status status;
    private String tracking;
    private Double costs;

    public static Shipment from(ShipmentRequest request) {
        var shipment = new Shipment();
        shipment.electronicInvoiceNumber = request.electronicInvoiceNumber();
        shipment.recipientName = request.recipientName();
        shipment.recipientAddress = request.recipientAddress();
        shipment.shippingDate = LocalDateTime.now();
        shipment.weight = request.weight();
        shipment.height = request.height();
        shipment.width = request.width();
        shipment.depth = request.depth();
        shipment.cubageFactor = request.cubageFactor();
        shipment.setCubage();
        shipment.description = request.description();
        shipment.carrier = request.carrier();
        shipment.status = Status.CREATED;
        shipment.costs = request.costs();

        return shipment;
    }

    public static Shipment from(ShipmentEntity shipmentEntity) {
        var shipment = new Shipment();
        BeanUtils.copyProperties(shipmentEntity, shipment);
        return shipment;
    }

    public void setCubage() {
        if (this.cubageFactor != null) {
            this.cubage = ((this.height * this.width * this.depth) / 1000000000) * this.cubageFactor;
        }
    }

    public void softDelete() {
        this.cancelationDate = LocalDateTime.now();
        this.status = Status.CANCELLED;
    }
}
