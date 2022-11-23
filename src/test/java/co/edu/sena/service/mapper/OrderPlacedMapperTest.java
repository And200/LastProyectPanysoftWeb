package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderPlacedMapperTest {

    private OrderPlacedMapper orderPlacedMapper;

    @BeforeEach
    public void setUp() {
        orderPlacedMapper = new OrderPlacedMapperImpl();
    }
}
