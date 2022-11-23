package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A DetailAmountRecip.
 */
@Entity
@Table(name = "detail_amount_recip")
public class DetailAmountRecip implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "amount_product", nullable = false)
    private Double amountProduct;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "detailSales", "inventories", "detailAmountRecips", "detailOrders", "category", "provider", "presentation" },
        allowSetters = true
    )
    private Product product;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "detailAmountRecips" }, allowSetters = true)
    private Recip recip;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DetailAmountRecip id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmountProduct() {
        return this.amountProduct;
    }

    public DetailAmountRecip amountProduct(Double amountProduct) {
        this.setAmountProduct(amountProduct);
        return this;
    }

    public void setAmountProduct(Double amountProduct) {
        this.amountProduct = amountProduct;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public DetailAmountRecip product(Product product) {
        this.setProduct(product);
        return this;
    }

    public Recip getRecip() {
        return this.recip;
    }

    public void setRecip(Recip recip) {
        this.recip = recip;
    }

    public DetailAmountRecip recip(Recip recip) {
        this.setRecip(recip);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetailAmountRecip)) {
            return false;
        }
        return id != null && id.equals(((DetailAmountRecip) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetailAmountRecip{" +
            "id=" + getId() +
            ", amountProduct=" + getAmountProduct() +
            "}";
    }
}
