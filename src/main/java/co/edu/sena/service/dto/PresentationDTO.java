package co.edu.sena.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.Presentation} entity.
 */
public class PresentationDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 30)
    private String presentation;

    private MeasureUnitDTO measureUnit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public MeasureUnitDTO getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(MeasureUnitDTO measureUnit) {
        this.measureUnit = measureUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PresentationDTO)) {
            return false;
        }

        PresentationDTO presentationDTO = (PresentationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, presentationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PresentationDTO{" +
            "id=" + getId() +
            ", presentation='" + getPresentation() + "'" +
            ", measureUnit=" + getMeasureUnit() +
            "}";
    }
}
