package co.edu.sena.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.Recip} entity.
 */
public class RecipDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 30)
    private String nameRecip;

    @NotNull
    private Double estimatedPricePreparation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameRecip() {
        return nameRecip;
    }

    public void setNameRecip(String nameRecip) {
        this.nameRecip = nameRecip;
    }

    public Double getEstimatedPricePreparation() {
        return estimatedPricePreparation;
    }

    public void setEstimatedPricePreparation(Double estimatedPricePreparation) {
        this.estimatedPricePreparation = estimatedPricePreparation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecipDTO)) {
            return false;
        }

        RecipDTO recipDTO = (RecipDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, recipDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecipDTO{" +
            "id=" + getId() +
            ", nameRecip='" + getNameRecip() + "'" +
            ", estimatedPricePreparation=" + getEstimatedPricePreparation() +
            "}";
    }
}
