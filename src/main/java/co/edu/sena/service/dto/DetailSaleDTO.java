package co.edu.sena.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.DetailSale} entity.
 */
public class DetailSaleDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer productAmount;

    private ProductDTO product;

    private PresentationDTO presentation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Integer productAmount) {
        this.productAmount = productAmount;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public PresentationDTO getPresentation() {
        return presentation;
    }

    public void setPresentation(PresentationDTO presentation) {
        this.presentation = presentation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetailSaleDTO)) {
            return false;
        }

        DetailSaleDTO detailSaleDTO = (DetailSaleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, detailSaleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetailSaleDTO{" +
            "id=" + getId() +
            ", productAmount=" + getProductAmount() +
            ", product=" + getProduct() +
            ", presentation=" + getPresentation() +
            "}";
    }
}
