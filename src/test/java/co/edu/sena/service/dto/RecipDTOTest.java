package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecipDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipDTO.class);
        RecipDTO recipDTO1 = new RecipDTO();
        recipDTO1.setId(1L);
        RecipDTO recipDTO2 = new RecipDTO();
        assertThat(recipDTO1).isNotEqualTo(recipDTO2);
        recipDTO2.setId(recipDTO1.getId());
        assertThat(recipDTO1).isEqualTo(recipDTO2);
        recipDTO2.setId(2L);
        assertThat(recipDTO1).isNotEqualTo(recipDTO2);
        recipDTO1.setId(null);
        assertThat(recipDTO1).isNotEqualTo(recipDTO2);
    }
}
