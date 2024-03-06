package br.com.azship.admazsshipping.infrastructure.adapters.repository;

import br.com.azship.admazsshipping.api.dto.PageRequest;
import br.com.azship.admazsshipping.api.dto.ShipmentFilters;
import br.com.azship.admazsshipping.infrastructure.adapters.entity.ShipmentEntity;
import br.com.azship.admazsshipping.infrastructure.exception.NotFoundException;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Optional;

import static br.com.azship.admazsshipping.helper.TestsHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShipmentRepositoryTest {

    @InjectMocks
    private ShipmentRepository repository;
    @Mock
    private MongoDbRepository mongoDbRepository;

    @Test
    void save_shouldCallMongoInsertFromShipmentEntity() {
        repository.save(shipmentEntity());

        verify(mongoDbRepository).insert(shipmentEntity());
    }

    @Test
    void save_shouldCallMongoInsertFromShipment() {
        repository.save(shipment());

        verify(mongoDbRepository).insert(shipmentEntity());
    }

    @Test
    void findAll_shouldReturnShipmentPage() {
        when(mongoDbRepository.findAll(any(Predicate.class), any(PageRequest.class)))
            .thenReturn(new PageImpl<>(
                List.of(shipmentEntity()),
                new PageRequest(),
                1
            ));

        assertThat(repository.findAll(new ShipmentFilters().shipmentPredicate().build(), new PageRequest()))
            .hasSize(1)
            .isEqualTo(shipmentPage());
    }

    @Test
    void findById_shouldReturnShipment() {
        when(mongoDbRepository.findById("1"))
            .thenReturn(Optional.of(shipmentEntity()));

        assertThat(repository.findById("1"))
            .isEqualTo(shipment());
    }

    @Test
    void findById_shouldThrowNotFoundExceptionIfShipmentDoesntExistInDatabase() {
        assertThatExceptionOfType(NotFoundException.class)
            .isThrownBy(() -> repository.findById("1"))
            .withMessage("Shipment not found!");

        verify(mongoDbRepository).findById("1");
    }

    @Test
    void update_shouldCallSave() {
        when(mongoDbRepository.existsById("1"))
            .thenReturn(true);

        repository.update("1", shipment());

        verify(mongoDbRepository).save(shipmentEntity());
    }

    @Test
    void update_shouldThrowNotFoundExceptionIfEntityDoesntExistInDatabase() {
        var shipment = shipment();
        assertThatExceptionOfType(NotFoundException.class)
            .isThrownBy(() -> repository.update("1", shipment))
            .withMessage("Shipment not found!");

        verify(mongoDbRepository, never()).save(any(ShipmentEntity.class));
    }
}