package co.edu.sena.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.PurchaseReceipt} entity.
 */
public class PurchaseReceiptDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime date;

    @NotNull
    private Double totalSale;

    private EmployeeDTO employee;

    private DetailSaleDTO detailSale;

    private ClientDTO client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Double getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(Double totalSale) {
        this.totalSale = totalSale;
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public DetailSaleDTO getDetailSale() {
        return detailSale;
    }

    public void setDetailSale(DetailSaleDTO detailSale) {
        this.detailSale = detailSale;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PurchaseReceiptDTO)) {
            return false;
        }

        PurchaseReceiptDTO purchaseReceiptDTO = (PurchaseReceiptDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, purchaseReceiptDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PurchaseReceiptDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", totalSale=" + getTotalSale() +
            ", employee=" + getEmployee() +
            ", detailSale=" + getDetailSale() +
            ", client=" + getClient() +
            "}";
    }
}
