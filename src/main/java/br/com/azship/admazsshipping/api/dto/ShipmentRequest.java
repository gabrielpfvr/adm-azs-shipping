package br.com.azship.admazsshipping.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ShipmentRequest(
    @NotNull
    Integer electronicInvoiceNumber,
    @NotEmpty
    String recipientName,
    @NotEmpty
    String recipientAddress,
    Double weight,
    @NotNull
    Double height,
    @NotNull
    Double width,
    @NotNull
    Double depth,
    Double cubageFactor,
    @NotEmpty
    String description,
    @NotEmpty
    String carrier,
    @NotNull
    Double costs
) {
}
