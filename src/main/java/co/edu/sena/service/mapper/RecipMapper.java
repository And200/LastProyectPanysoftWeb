package co.edu.sena.service.mapper;

import co.edu.sena.domain.Recip;
import co.edu.sena.service.dto.RecipDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Recip} and its DTO {@link RecipDTO}.
 */
@Mapper(componentModel = "spring")
public interface RecipMapper extends EntityMapper<RecipDTO, Recip> {}
