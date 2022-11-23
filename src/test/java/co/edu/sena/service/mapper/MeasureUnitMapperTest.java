package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MeasureUnitMapperTest {

    private MeasureUnitMapper measureUnitMapper;

    @BeforeEach
    public void setUp() {
        measureUnitMapper = new MeasureUnitMapperImpl();
    }
}
