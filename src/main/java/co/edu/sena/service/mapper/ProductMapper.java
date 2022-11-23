package co.edu.sena.service.mapper;

import co.edu.sena.domain.Category;
import co.edu.sena.domain.Presentation;
import co.edu.sena.domain.Product;
import co.edu.sena.domain.Provider;
import co.edu.sena.service.dto.CategoryDTO;
import co.edu.sena.service.dto.PresentationDTO;
import co.edu.sena.service.dto.ProductDTO;
import co.edu.sena.service.dto.ProviderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryNameCategory")
    @Mapping(target = "provider", source = "provider", qualifiedByName = "providerName")
    @Mapping(target = "presentation", source = "presentation", qualifiedByName = "presentationPresentation")
    ProductDTO toDto(Product s);

    @Named("categoryNameCategory")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nameCategory", source = "nameCategory")
    CategoryDTO toDtoCategoryNameCategory(Category category);

    @Named("providerName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProviderDTO toDtoProviderName(Provider provider);

    @Named("presentationPresentation")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "presentation", source = "presentation")
    PresentationDTO toDtoPresentationPresentation(Presentation presentation);
}
