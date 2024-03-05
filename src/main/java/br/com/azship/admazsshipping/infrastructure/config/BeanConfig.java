package br.com.azship.admazsshipping.infrastructure.config;

import br.com.azship.admazsshipping.domain.adapters.ShipmentServiceImpl;
import br.com.azship.admazsshipping.domain.ports.ShipmentRepositoryPort;
import br.com.azship.admazsshipping.domain.ports.ShipmentServicePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ShipmentServicePort shipmentService(ShipmentRepositoryPort shipmentRepositoryPort) {
        return new ShipmentServiceImpl(shipmentRepositoryPort);
    }
}
