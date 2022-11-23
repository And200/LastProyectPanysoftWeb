package co.edu.sena.service.mapper;

import co.edu.sena.domain.DetailAmountRecip;
import co.edu.sena.domain.Product;
import co.edu.sena.domain.Recip;
import co.edu.sena.service.dto.DetailAmountRecipDTO;
import co.edu.sena.service.dto.ProductDTO;
import co.edu.sena.service.dto.RecipDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DetailAmountRecip} and its DTO {@link DetailAmountRecipDTO}.
 */
@Mapper(componentModel = "spring")
public interface DetailAmountRecipMapper extends EntityMapper<DetailAmountRecipDTO, DetailAmountRecip> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productProductName")
    @Mapping(target = "recip", source = "recip", qualifiedByName = "recipNameRecip")
    DetailAmountRecipDTO toDto(DetailAmountRecip s);

    @Named("productProductName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "productName", source = "productName")
    ProductDTO toDtoProductProductName(Product product);

    @Named("recipNameRecip")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nameRecip", source = "nameRecip")
    RecipDTO toDtoRecipNameRecip(Recip recip);
}
