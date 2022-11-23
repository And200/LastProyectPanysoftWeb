package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetailAmountRecipDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailAmountRecipDTO.class);
        DetailAmountRecipDTO detailAmountRecipDTO1 = new DetailAmountRecipDTO();
        detailAmountRecipDTO1.setId(1L);
        DetailAmountRecipDTO detailAmountRecipDTO2 = new DetailAmountRecipDTO();
        assertThat(detailAmountRecipDTO1).isNotEqualTo(detailAmountRecipDTO2);
        detailAmountRecipDTO2.setId(detailAmountRecipDTO1.getId());
        assertThat(detailAmountRecipDTO1).isEqualTo(detailAmountRecipDTO2);
        detailAmountRecipDTO2.setId(2L);
        assertThat(detailAmountRecipDTO1).isNotEqualTo(detailAmountRecipDTO2);
        detailAmountRecipDTO1.setId(null);
        assertThat(detailAmountRecipDTO1).isNotEqualTo(detailAmountRecipDTO2);
    }
}
