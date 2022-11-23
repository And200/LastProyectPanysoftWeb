package co.edu.sena.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecipMapperTest {

    private RecipMapper recipMapper;

    @BeforeEach
    public void setUp() {
        recipMapper = new RecipMapperImpl();
    }
}
