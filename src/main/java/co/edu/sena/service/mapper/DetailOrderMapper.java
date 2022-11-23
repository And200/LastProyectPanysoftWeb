package co.edu.sena.service.mapper;

import co.edu.sena.domain.DetailOrder;
import co.edu.sena.domain.OrderPlaced;
import co.edu.sena.domain.Product;
import co.edu.sena.domain.Provider;
import co.edu.sena.service.dto.DetailOrderDTO;
import co.edu.sena.service.dto.OrderPlacedDTO;
import co.edu.sena.service.dto.ProductDTO;
import co.edu.sena.service.dto.ProviderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DetailOrder} and its DTO {@link DetailOrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface DetailOrderMapper extends EntityMapper<DetailOrderDTO, DetailOrder> {
    @Mapping(target = "provider", source = "provider", qualifiedByName = "providerName")
    @Mapping(target = "orderPlaced", source = "orderPlaced", qualifiedByName = "orderPlacedOrderPlacedState")
    @Mapping(target = "product", source = "product", qualifiedByName = "productProductName")
    DetailOrderDTO toDto(DetailOrder s);

    @Named("providerName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProviderDTO toDtoProviderName(Provider provider);

    @Named("orderPlacedOrderPlacedState")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "orderPlacedState", source = "orderPlacedState")
    OrderPlacedDTO toDtoOrderPlacedOrderPlacedState(OrderPlaced orderPlaced);

    @Named("productProductName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "productName", source = "productName")
    ProductDTO toDtoProductProductName(Product product);
}
