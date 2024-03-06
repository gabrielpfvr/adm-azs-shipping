package br.com.azship.admazsshipping.api.adapters;

import br.com.azship.admazsshipping.api.dto.PageRequest;
import br.com.azship.admazsshipping.api.dto.ShipmentFilters;
import br.com.azship.admazsshipping.api.dto.ShipmentRequest;
import br.com.azship.admazsshipping.domain.ports.ShipmentServicePort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static br.com.azship.admazsshipping.helper.TestsHelper.nullShipmentRequest;
import static br.com.azship.admazsshipping.helper.TestsHelper.shipmentRequest;
import static org.hamcrest.Matchers.containsInRelativeOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShipmentController.class)
@ActiveProfiles("test")
class ShipmentControllerTest {

    private static final String API_URL = "/api/v1/shippings";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ShipmentServicePort service;

    @Test
    @SneakyThrows
    void createShipment_shouldReturn201IfValidRequest() {
        mockMvc.perform(post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToBytes(shipmentRequest())))
            .andExpect(status().isCreated());

        verify(service).createShipment(shipmentRequest());
    }

    @Test
    @SneakyThrows
    void createShipment_shouldReturn400IfMissingRequest() {
        mockMvc.perform(post(API_URL))
            .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @Test
    @SneakyThrows
    void createShipment_shouldReturn400IfInvalidRequest() {
        mockMvc.perform(post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToBytes(nullShipmentRequest())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$[*]message", containsInRelativeOrder("The field electronicInvoiceNumber must not be null")))
            .andExpect(jsonPath("$[*]message", containsInRelativeOrder("The field recipientName must not be empty")))
            .andExpect(jsonPath("$[*]message", containsInRelativeOrder("The field recipientAddress must not be empty")))
            .andExpect(jsonPath("$[*]message", containsInRelativeOrder("The field height must not be null")))
            .andExpect(jsonPath("$[*]message", containsInRelativeOrder("The field width must not be null")))
            .andExpect(jsonPath("$[*]message", containsInRelativeOrder("The field depth must not be null")))
            .andExpect(jsonPath("$[*]message", containsInRelativeOrder("The field description must not be empty")))
            .andExpect(jsonPath("$[*]message", containsInRelativeOrder("The field carrier must not be empty")))
            .andExpect(jsonPath("$[*]message", containsInRelativeOrder("The field costs must not be null")));

        verifyNoInteractions(service);
    }

    @Test
    @SneakyThrows
    void getAll_shouldReturn200() {
        mockMvc.perform(get(API_URL)
                .param("shippingStartDate", "05/03/2024")
                .param("shippingEndDate", "06/03/2024"))
            .andExpect(status().isOk());

        var filters = new ShipmentFilters();
        filters.setShippingStartDate(LocalDate.of(2024, 3, 5));
        filters.setShippingEndDate(LocalDate.of(2024, 3, 6));

        verify(service).findAll(filters, new PageRequest());
    }

    @Test
    @SneakyThrows
    void updateShipment_shouldReturn400IfNoRequest() {
        mockMvc.perform(put(API_URL + "/{id}/update", 1))
            .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @Test
    @SneakyThrows
    void updateShipment_shouldReturn200() {
        mockMvc.perform(put(API_URL + "/{id}/update", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToBytes(shipmentRequest())))
            .andExpect(status().isOk());

        verify(service).updateShipment("1", shipmentRequest());
    }

    @Test
    @SneakyThrows
    void deleteShipment_shouldReturn204() {
        mockMvc.perform(delete(API_URL + "/{id}/delete", 1))
            .andExpect(status().isNoContent());

        verify(service).deleteShipment("1");
    }

    private static byte[] convertObjectToBytes(Object object) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsBytes(object);
    }
}