package co.edu.sena.service.mapper;

import co.edu.sena.domain.Inventory;
import co.edu.sena.domain.Product;
import co.edu.sena.service.dto.InventoryDTO;
import co.edu.sena.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Inventory} and its DTO {@link InventoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface InventoryMapper extends EntityMapper<InventoryDTO, Inventory> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productProductName")
    InventoryDTO toDto(Inventory s);

    @Named("productProductName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "productName", source = "productName")
    ProductDTO toDtoProductProductName(Product product);
}
