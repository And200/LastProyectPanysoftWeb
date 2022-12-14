package co.edu.sena.service.dto;

import co.edu.sena.domain.enumeration.StateDocument;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.DocumentType} entity.
 */
public class DocumentTypeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 30)
    private String documentName;

    @NotNull
    @Size(max = 10)
    private String initials;

    @NotNull
    private StateDocument stateDocumentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public StateDocument getStateDocumentType() {
        return stateDocumentType;
    }

    public void setStateDocumentType(StateDocument stateDocumentType) {
        this.stateDocumentType = stateDocumentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentTypeDTO)) {
            return false;
        }

        DocumentTypeDTO documentTypeDTO = (DocumentTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, documentTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentTypeDTO{" +
            "id=" + getId() +
            ", documentName='" + getDocumentName() + "'" +
            ", initials='" + getInitials() + "'" +
            ", stateDocumentType='" + getStateDocumentType() + "'" +
            "}";
    }
}
