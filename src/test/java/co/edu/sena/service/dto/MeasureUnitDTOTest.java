package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MeasureUnitDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeasureUnitDTO.class);
        MeasureUnitDTO measureUnitDTO1 = new MeasureUnitDTO();
        measureUnitDTO1.setId(1L);
        MeasureUnitDTO measureUnitDTO2 = new MeasureUnitDTO();
        assertThat(measureUnitDTO1).isNotEqualTo(measureUnitDTO2);
        measureUnitDTO2.setId(measureUnitDTO1.getId());
        assertThat(measureUnitDTO1).isEqualTo(measureUnitDTO2);
        measureUnitDTO2.setId(2L);
        assertThat(measureUnitDTO1).isNotEqualTo(measureUnitDTO2);
        measureUnitDTO1.setId(null);
        assertThat(measureUnitDTO1).isNotEqualTo(measureUnitDTO2);
    }
}
