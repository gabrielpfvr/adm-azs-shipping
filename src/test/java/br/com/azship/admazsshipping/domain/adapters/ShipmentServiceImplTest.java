package br.com.azship.admazsshipping.domain.adapters;

import br.com.azship.admazsshipping.api.dto.PageRequest;
import br.com.azship.admazsshipping.api.dto.ShipmentFilters;
import br.com.azship.admazsshipping.api.dto.ShipmentResponse;
import br.com.azship.admazsshipping.domain.Shipment;
import br.com.azship.admazsshipping.domain.enums.Status;
import br.com.azship.admazsshipping.domain.ports.ShipmentRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.time.temporal.ChronoUnit;

import static br.com.azship.admazsshipping.helper.TestsHelper.*;
import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShipmentServiceImplTest {

    @InjectMocks
    private ShipmentServiceImpl service;
    @Mock
    private ShipmentRepositoryPort repositoryPort;
    @Captor
    private ArgumentCaptor<Shipment> shipmentArgumentCaptor;

    @Test
    void createShipment_shouldCallRepositoryAndGenerateTrackingNumber() {
        var request = shipmentRequest();
        var expected = Shipment.from(request);

        assertThatCode(() -> service.createShipment(request))
            .doesNotThrowAnyException();

        verify(repositoryPort).save(shipmentArgumentCaptor.capture());

        var shipment = shipmentArgumentCaptor.getValue();

        assertThat(shipment)
            .usingRecursiveComparison()
            .ignoringFields("tracking", "shippingDate")
                .isEqualTo(expected);

        assertThat(shipment)
            .extracting("tracking")
            .isNotNull();
    }

    @Test
    void findAll_shouldReturnShipmentResponsePage() {
        var filters = new ShipmentFilters();
        var pageRequest = new PageRequest();
        var expected = ShipmentResponse.from(shipment());

        when(repositoryPort.findAll(filters.shipmentPredicate().build(), pageRequest))
            .thenReturn(shipmentPage());

        assertThat(service.findAll(filters, pageRequest))
            .isExactlyInstanceOf(PageImpl.class)
            .hasSize(1)
            .containsExactly(expected);
    }

    @Test
    void updateShipment_shouldCallRepository() {
        var request = shipmentRequest();

        assertThatCode(() -> service.updateShipment("1", request))
            .doesNotThrowAnyException();

        verify(repositoryPort).update(eq("1"), any(Shipment.class));
    }

    @Test
    void deleteShipment_shouldSoftDeleteShipment() {
        when(repositoryPort.findById("1"))
            .thenReturn(shipment());

        assertThatCode(() -> service.deleteShipment("1"))
            .doesNotThrowAnyException();

        verify(repositoryPort).save(shipmentArgumentCaptor.capture());

        var shipment = shipmentArgumentCaptor.getValue();

        assertThat(shipment.getCancelationDate())
            .isCloseTo(now(), within(1, ChronoUnit.MINUTES));

        assertEquals(Status.CANCELLED, shipment.getStatus());
    }
}

