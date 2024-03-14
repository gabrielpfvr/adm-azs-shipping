package br.com.gabrielmonteiromotta.shippingapi.infrastructure.adapters.repository;

import br.com.gabrielmonteiromotta.shippingapi.domain.enums.Status;
import br.com.gabrielmonteiromotta.shippingapi.infrastructure.adapters.entity.QShipmentEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ShipmentPredicate {

    private static final QShipmentEntity shipment = QShipmentEntity.shipmentEntity;

    private final BooleanBuilder booleanBuilder;

    public ShipmentPredicate() {
        this.booleanBuilder = new BooleanBuilder();
    }

    public BooleanBuilder build() {
        return this.booleanBuilder;
    }

    public ShipmentPredicate electronicInvoicePredicate(Integer electronicInvoiceNumber) {
        if (electronicInvoiceNumber != null) {
            booleanBuilder.and(shipment.electronicInvoiceNumber.eq(electronicInvoiceNumber));
        }
        return this;
    }

    public ShipmentPredicate recipientNamePredicate(String recipientName) {
        if (StringUtils.isNotBlank(recipientName)) {
            booleanBuilder.and(shipment.recipientName.containsIgnoreCase(recipientName));
        }
        return this;
    }

    public ShipmentPredicate recipientAddressPredicate(String recipientAddress) {
        if (StringUtils.isNotBlank(recipientAddress)) {
            booleanBuilder.and(shipment.recipientAddress.containsIgnoreCase(recipientAddress));
        }
        return this;
    }

    public ShipmentPredicate shippingDatePredicate(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            booleanBuilder.and(this.betweenDates(shipment.shippingDate, startDate, endDate));
        }
        return this;
    }

    public ShipmentPredicate deliveryDatePredicate(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            booleanBuilder.and(this.betweenDates(shipment.deliveryDate, startDate, endDate));
        }
        return this;
    }

    public ShipmentPredicate cancelationDatePredicate(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            booleanBuilder.and(this.betweenDates(shipment.cancelationDate, startDate, endDate));
        }
        return this;
    }

    public ShipmentPredicate betweenWeightsPredicate(Double startWeight, Double endWeight) {
        if (startWeight != null && endWeight != null) {
            booleanBuilder.and(shipment.weight.between(startWeight, endWeight));
        }
        return this;
    }

    public ShipmentPredicate cubageFactorPredicate(Double cubageFactor) {
        if (cubageFactor != null) {
            booleanBuilder.and(shipment.cubageFactor.eq(cubageFactor));
        }
        return this;
    }

    public ShipmentPredicate descriptionPredicate(String description) {
        if (StringUtils.isNotBlank(description)) {
            booleanBuilder.and(shipment.description.containsIgnoreCase(description));
        }
        return this;
    }

    public ShipmentPredicate carrierPredicate(String carrier) {
        if (StringUtils.isNotBlank(carrier)) {
            booleanBuilder.and(shipment.carrier.equalsIgnoreCase(carrier));
        }
        return this;
    }

    public ShipmentPredicate statusPredicate(Status status) {
        if (status != null) {
            booleanBuilder.and(shipment.status.eq(status));
        }
        return this;
    }

    public ShipmentPredicate trackingPredicate(String trackingCode) {
        if (StringUtils.isNotBlank(trackingCode)) {
            booleanBuilder.and(shipment.tracking.equalsIgnoreCase(trackingCode));
        }
        return this;
    }

    public ShipmentPredicate betweenCostsPredicate(Double startCost, Double endCost) {
        if (startCost != null && endCost != null) {
            booleanBuilder.and(shipment.costs.between(startCost, endCost));
        }
        return this;
    }

    private BooleanExpression betweenDates(DateTimePath<LocalDateTime> dateField, LocalDate startDate, LocalDate endDate) {
        return dateField.between(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
    }
}
