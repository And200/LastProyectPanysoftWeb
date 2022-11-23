package co.edu.sena.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.MeasureUnit} entity.
 */
public class MeasureUnitDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 30)
    private String nameUnit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameUnit() {
        return nameUnit;
    }

    public void setNameUnit(String nameUnit) {
        this.nameUnit = nameUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MeasureUnitDTO)) {
            return false;
        }

        MeasureUnitDTO measureUnitDTO = (MeasureUnitDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, measureUnitDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MeasureUnitDTO{" +
            "id=" + getId() +
            ", nameUnit='" + getNameUnit() + "'" +
            "}";
    }
}
