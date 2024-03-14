package br.com.gabrielmonteiromotta.shippingapi.infrastructure.adapters.entity;

import br.com.gabrielmonteiromotta.shippingapi.domain.Shipment;
import br.com.gabrielmonteiromotta.shippingapi.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Document(value = "SHIPMENT")
public class ShipmentEntity {

    @Id
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

    public static ShipmentEntity from(Shipment shipment) {
        var entity = new ShipmentEntity();
        BeanUtils.copyProperties(shipment, entity);
        return entity;
    }
}
