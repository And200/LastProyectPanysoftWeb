package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PurchaseReceiptDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseReceiptDTO.class);
        PurchaseReceiptDTO purchaseReceiptDTO1 = new PurchaseReceiptDTO();
        purchaseReceiptDTO1.setId(1L);
        PurchaseReceiptDTO purchaseReceiptDTO2 = new PurchaseReceiptDTO();
        assertThat(purchaseReceiptDTO1).isNotEqualTo(purchaseReceiptDTO2);
        purchaseReceiptDTO2.setId(purchaseReceiptDTO1.getId());
        assertThat(purchaseReceiptDTO1).isEqualTo(purchaseReceiptDTO2);
        purchaseReceiptDTO2.setId(2L);
        assertThat(purchaseReceiptDTO1).isNotEqualTo(purchaseReceiptDTO2);
        purchaseReceiptDTO1.setId(null);
        assertThat(purchaseReceiptDTO1).isNotEqualTo(purchaseReceiptDTO2);
    }
}
