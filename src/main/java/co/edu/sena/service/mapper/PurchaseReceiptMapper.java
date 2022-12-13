package co.edu.sena.service.mapper;

import co.edu.sena.domain.Client;
import co.edu.sena.domain.DetailSale;
import co.edu.sena.domain.Employee;
import co.edu.sena.domain.PurchaseReceipt;
import co.edu.sena.service.dto.ClientDTO;
import co.edu.sena.service.dto.DetailSaleDTO;
import co.edu.sena.service.dto.EmployeeDTO;
import co.edu.sena.service.dto.PurchaseReceiptDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PurchaseReceipt} and its DTO {@link PurchaseReceiptDTO}.
 */
@Mapper(componentModel = "spring")
public interface PurchaseReceiptMapper extends EntityMapper<PurchaseReceiptDTO, PurchaseReceipt> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    @Mapping(target = "detailSale", source = "detailSale", qualifiedByName = "detailSaleProductAmount")
    @Mapping(target = "client", source = "client", qualifiedByName = "clientId")
    PurchaseReceiptDTO toDto(PurchaseReceipt s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "person", source = "person")
    EmployeeDTO toDtoEmployeeId(Employee employee);

    @Named("detailSaleProductAmount")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "productAmount", source = "productAmount")
    DetailSaleDTO toDtoDetailSaleProductAmount(DetailSale detailSale);

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "person", source = "person")
    ClientDTO toDtoClientId(Client client);
}
