package co.edu.sena.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.sena.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderPlacedDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderPlacedDTO.class);
        OrderPlacedDTO orderPlacedDTO1 = new OrderPlacedDTO();
        orderPlacedDTO1.setId(1L);
        OrderPlacedDTO orderPlacedDTO2 = new OrderPlacedDTO();
        assertThat(orderPlacedDTO1).isNotEqualTo(orderPlacedDTO2);
        orderPlacedDTO2.setId(orderPlacedDTO1.getId());
        assertThat(orderPlacedDTO1).isEqualTo(orderPlacedDTO2);
        orderPlacedDTO2.setId(2L);
        assertThat(orderPlacedDTO1).isNotEqualTo(orderPlacedDTO2);
        orderPlacedDTO1.setId(null);
        assertThat(orderPlacedDTO1).isNotEqualTo(orderPlacedDTO2);
    }
}
