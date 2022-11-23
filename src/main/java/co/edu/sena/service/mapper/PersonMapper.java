package co.edu.sena.service.mapper;

import co.edu.sena.domain.DocumentType;
import co.edu.sena.domain.Person;
import co.edu.sena.service.dto.DocumentTypeDTO;
import co.edu.sena.service.dto.PersonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Person} and its DTO {@link PersonDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonMapper extends EntityMapper<PersonDTO, Person> {
    @Mapping(target = "documentType", source = "documentType", qualifiedByName = "documentTypeDocumentName")
    PersonDTO toDto(Person s);

    @Named("documentTypeDocumentName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "documentName", source = "documentName")
    DocumentTypeDTO toDtoDocumentTypeDocumentName(DocumentType documentType);
}
