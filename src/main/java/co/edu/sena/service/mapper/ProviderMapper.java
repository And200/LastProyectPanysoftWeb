package co.edu.sena.service.mapper;

import co.edu.sena.domain.Provider;
import co.edu.sena.service.dto.ProviderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Provider} and its DTO {@link ProviderDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProviderMapper extends EntityMapper<ProviderDTO, Provider> {}
