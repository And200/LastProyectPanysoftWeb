package co.edu.sena.service.mapper;

import co.edu.sena.domain.MeasureUnit;
import co.edu.sena.service.dto.MeasureUnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MeasureUnit} and its DTO {@link MeasureUnitDTO}.
 */
@Mapper(componentModel = "spring")
public interface MeasureUnitMapper extends EntityMapper<MeasureUnitDTO, MeasureUnit> {}
