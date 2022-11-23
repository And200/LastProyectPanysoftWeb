package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetailOrderDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailOrderDTO.class);
        DetailOrderDTO detailOrderDTO1 = new DetailOrderDTO();
        detailOrderDTO1.setId(1L);
        DetailOrderDTO detailOrderDTO2 = new DetailOrderDTO();
        assertThat(detailOrderDTO1).isNotEqualTo(detailOrderDTO2);
        detailOrderDTO2.setId(detailOrderDTO1.getId());
        assertThat(detailOrderDTO1).isEqualTo(detailOrderDTO2);
        detailOrderDTO2.setId(2L);
        assertThat(detailOrderDTO1).isNotEqualTo(detailOrderDTO2);
        detailOrderDTO1.setId(null);
        assertThat(detailOrderDTO1).isNotEqualTo(detailOrderDTO2);
    }
}
