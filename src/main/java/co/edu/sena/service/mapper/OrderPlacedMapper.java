package co.edu.sena.service.mapper;

import co.edu.sena.domain.OrderPlaced;
import co.edu.sena.service.dto.OrderPlacedDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderPlaced} and its DTO {@link OrderPlacedDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderPlacedMapper extends EntityMapper<OrderPlacedDTO, OrderPlaced> {}
