package co.edu.sena.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetailAmountRecipTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailAmountRecip.class);
        DetailAmountRecip detailAmountRecip1 = new DetailAmountRecip();
        detailAmountRecip1.setId(1L);
        DetailAmountRecip detailAmountRecip2 = new DetailAmountRecip();
        detailAmountRecip2.setId(detailAmountRecip1.getId());
        assertThat(detailAmountRecip1).isEqualTo(detailAmountRecip2);
        detailAmountRecip2.setId(2L);
        assertThat(detailAmountRecip1).isNotEqualTo(detailAmountRecip2);
        detailAmountRecip1.setId(null);
        assertThat(detailAmountRecip1).isNotEqualTo(detailAmountRecip2);
    }
}
