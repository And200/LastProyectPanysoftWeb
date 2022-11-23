package co.edu.sena.service.dto;

import co.edu.sena.domain.enumeration.StateOrder;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.OrderPlaced} entity.
 */
public class OrderPlacedDTO implements Serializable {

    private Long id;

    @NotNull
    private StateOrder orderPlacedState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StateOrder getOrderPlacedState() {
        return orderPlacedState;
    }

    public void setOrderPlacedState(StateOrder orderPlacedState) {
        this.orderPlacedState = orderPlacedState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderPlacedDTO)) {
            return false;
        }

        OrderPlacedDTO orderPlacedDTO = (OrderPlacedDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderPlacedDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderPlacedDTO{" +
            "id=" + getId() +
            ", orderPlacedState='" + getOrderPlacedState() + "'" +
            "}";
    }
}
