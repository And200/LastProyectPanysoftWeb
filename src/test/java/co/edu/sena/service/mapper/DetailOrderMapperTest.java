package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DetailOrderMapperTest {

    private DetailOrderMapper detailOrderMapper;

    @BeforeEach
    public void setUp() {
        detailOrderMapper = new DetailOrderMapperImpl();
    }
}
