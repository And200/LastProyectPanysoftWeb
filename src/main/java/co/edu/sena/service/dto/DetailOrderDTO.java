package co.edu.sena.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.DetailOrder} entity.
 */
public class DetailOrderDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer quantityOrdered;

    @NotNull
    private ZonedDateTime date;

    @NotNull
    @Size(max = 30)
    private String invoiceNumber;

    @NotNull
    private Double pricePayment;

    private ProviderDTO provider;

    private OrderPlacedDTO orderPlaced;

    private ProductDTO product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(Integer quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Double getPricePayment() {
        return pricePayment;
    }

    public void setPricePayment(Double pricePayment) {
        this.pricePayment = pricePayment;
    }

    public ProviderDTO getProvider() {
        return provider;
    }

    public void setProvider(ProviderDTO provider) {
        this.provider = provider;
    }

    public OrderPlacedDTO getOrderPlaced() {
        return orderPlaced;
    }

    public void setOrderPlaced(OrderPlacedDTO orderPlaced) {
        this.orderPlaced = orderPlaced;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetailOrderDTO)) {
            return false;
        }

        DetailOrderDTO detailOrderDTO = (DetailOrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, detailOrderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetailOrderDTO{" +
            "id=" + getId() +
            ", quantityOrdered=" + getQuantityOrdered() +
            ", date='" + getDate() + "'" +
            ", invoiceNumber='" + getInvoiceNumber() + "'" +
            ", pricePayment=" + getPricePayment() +
            ", provider=" + getProvider() +
            ", orderPlaced=" + getOrderPlaced() +
            ", product=" + getProduct() +
            "}";
    }
}
