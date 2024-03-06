package br.com.azship.admazsshipping.api.dto;

import br.com.azship.admazsshipping.domain.enums.Status;
import br.com.azship.admazsshipping.infrastructure.adapters.repository.ShipmentPredicate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ShipmentFilters {

    private static final String DATE_PATTERN = "dd/MM/yyyy";

    private Integer electronicInvoiceNumber;
    private String recipientName;
    private String recipientAddress;
    @DateTimeFormat(pattern = DATE_PATTERN)
    private LocalDate shippingStartDate;
    @DateTimeFormat(pattern = DATE_PATTERN)
    private LocalDate shippingEndDate;
    @DateTimeFormat(pattern = DATE_PATTERN)
    private LocalDate deliveryStartDate;
    @DateTimeFormat(pattern = DATE_PATTERN)
    private LocalDate deliveryEndDate;
    @DateTimeFormat(pattern = DATE_PATTERN)
    private LocalDate cancelationStartDate;
    @DateTimeFormat(pattern = DATE_PATTERN)
    private LocalDate cancelationEndDate;
    private Double startWeight;
    private Double endWeight;
    private Double cubageFactor;
    private String description;
    private String carrier;
    private Status status;
    private String tracking;
    private Double startCost;
    private Double endCost;

    public ShipmentPredicate shipmentPredicate() {
        return new ShipmentPredicate()
            .electronicInvoicePredicate(electronicInvoiceNumber)
            .recipientNamePredicate(recipientName)
            .recipientAddressPredicate(recipientAddress)
            .shippingDatePredicate(shippingStartDate, shippingEndDate)
            .deliveryDatePredicate(deliveryStartDate, deliveryEndDate)
            .cancelationDatePredicate(cancelationStartDate, cancelationEndDate)
            .betweenWeightsPredicate(startWeight, endWeight)
            .cubageFactorPredicate(cubageFactor)
            .descriptionPredicate(description)
            .carrierPredicate(carrier)
            .statusPredicate(status)
            .trackingPredicate(tracking)
            .betweenCostsPredicate(startCost, endCost);
    }
}
