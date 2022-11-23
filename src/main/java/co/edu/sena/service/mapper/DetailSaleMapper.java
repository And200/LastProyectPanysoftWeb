package co.edu.sena.service.mapper;

import co.edu.sena.domain.DetailSale;
import co.edu.sena.domain.Presentation;
import co.edu.sena.domain.Product;
import co.edu.sena.service.dto.DetailSaleDTO;
import co.edu.sena.service.dto.PresentationDTO;
import co.edu.sena.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DetailSale} and its DTO {@link DetailSaleDTO}.
 */
@Mapper(componentModel = "spring")
public interface DetailSaleMapper extends EntityMapper<DetailSaleDTO, DetailSale> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productProductName")
    @Mapping(target = "presentation", source = "presentation", qualifiedByName = "presentationPresentation")
    DetailSaleDTO toDto(DetailSale s);

    @Named("productProductName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "productName", source = "productName")
    ProductDTO toDtoProductProductName(Product product);

    @Named("presentationPresentation")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "presentation", source = "presentation")
    PresentationDTO toDtoPresentationPresentation(Presentation presentation);
}
