package co.edu.sena.service.mapper;

import co.edu.sena.domain.MeasureUnit;
import co.edu.sena.domain.Presentation;
import co.edu.sena.service.dto.MeasureUnitDTO;
import co.edu.sena.service.dto.PresentationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Presentation} and its DTO {@link PresentationDTO}.
 */
@Mapper(componentModel = "spring")
public interface PresentationMapper extends EntityMapper<PresentationDTO, Presentation> {
    @Mapping(target = "measureUnit", source = "measureUnit", qualifiedByName = "measureUnitNameUnit")
    PresentationDTO toDto(Presentation s);

    @Named("measureUnitNameUnit")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nameUnit", source = "nameUnit")
    MeasureUnitDTO toDtoMeasureUnitNameUnit(MeasureUnit measureUnit);
}
