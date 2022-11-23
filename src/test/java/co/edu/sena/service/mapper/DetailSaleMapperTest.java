package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DetailSaleMapperTest {

    private DetailSaleMapper detailSaleMapper;

    @BeforeEach
    public void setUp() {
        detailSaleMapper = new DetailSaleMapperImpl();
    }
}
