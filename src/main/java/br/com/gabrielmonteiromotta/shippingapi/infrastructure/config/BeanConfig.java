package br.com.gabrielmonteiromotta.shippingapi.infrastructure.config;

import br.com.gabrielmonteiromotta.shippingapi.domain.adapters.ShipmentServiceImpl;
import br.com.gabrielmonteiromotta.shippingapi.domain.ports.ShipmentRepositoryPort;
import br.com.gabrielmonteiromotta.shippingapi.domain.ports.ShipmentServicePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ShipmentServicePort shipmentService(ShipmentRepositoryPort shipmentRepositoryPort) {
        return new ShipmentServiceImpl(shipmentRepositoryPort);
    }
}
