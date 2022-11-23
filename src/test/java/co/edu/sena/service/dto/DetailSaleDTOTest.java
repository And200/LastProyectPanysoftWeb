package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetailSaleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailSaleDTO.class);
        DetailSaleDTO detailSaleDTO1 = new DetailSaleDTO();
        detailSaleDTO1.setId(1L);
        DetailSaleDTO detailSaleDTO2 = new DetailSaleDTO();
        assertThat(detailSaleDTO1).isNotEqualTo(detailSaleDTO2);
        detailSaleDTO2.setId(detailSaleDTO1.getId());
        assertThat(detailSaleDTO1).isEqualTo(detailSaleDTO2);
        detailSaleDTO2.setId(2L);
        assertThat(detailSaleDTO1).isNotEqualTo(detailSaleDTO2);
        detailSaleDTO1.setId(null);
        assertThat(detailSaleDTO1).isNotEqualTo(detailSaleDTO2);
    }
}
