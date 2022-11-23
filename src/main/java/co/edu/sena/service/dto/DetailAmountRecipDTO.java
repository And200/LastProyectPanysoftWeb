package co.edu.sena.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link co.edu.sena.domain.DetailAmountRecip} entity.
 */
public class DetailAmountRecipDTO implements Serializable {

    private Long id;

    @NotNull
    private Double amountProduct;

    private ProductDTO product;

    private RecipDTO recip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmountProduct() {
        return amountProduct;
    }

    public void setAmountProduct(Double amountProduct) {
        this.amountProduct = amountProduct;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public RecipDTO getRecip() {
        return recip;
    }

    public void setRecip(RecipDTO recip) {
        this.recip = recip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetailAmountRecipDTO)) {
            return false;
        }

        DetailAmountRecipDTO detailAmountRecipDTO = (DetailAmountRecipDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, detailAmountRecipDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetailAmountRecipDTO{" +
            "id=" + getId() +
            ", amountProduct=" + getAmountProduct() +
            ", product=" + getProduct() +
            ", recip=" + getRecip() +
            "}";
    }
}
