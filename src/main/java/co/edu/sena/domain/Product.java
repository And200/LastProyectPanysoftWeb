package co.edu.sena.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "product_name", length = 50, nullable = false, unique = true)
    private String productName;

    @NotNull
    @Column(name = "buy_price", nullable = false)
    private Double buyPrice;

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "purchaseReceipts", "product", "presentation" }, allowSetters = true)
    private Set<DetailSale> detailSales = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product" }, allowSetters = true)
    private Set<Inventory> inventories = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "product", "recip" }, allowSetters = true)
    private Set<DetailAmountRecip> detailAmountRecips = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = { "provider", "orderPlaced", "product" }, allowSetters = true)
    private Set<DetailOrder> detailOrders = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "products" }, allowSetters = true)
    private Category category;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "products", "detailOrders" }, allowSetters = true)
    private Provider provider;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "detailSales", "products", "measureUnit" }, allowSetters = true)
    private Presentation presentation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return this.productName;
    }

    public Product productName(String productName) {
        this.setProductName(productName);
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getBuyPrice() {
        return this.buyPrice;
    }

    public Product buyPrice(Double buyPrice) {
        this.setBuyPrice(buyPrice);
        return this;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Set<DetailSale> getDetailSales() {
        return this.detailSales;
    }

    public void setDetailSales(Set<DetailSale> detailSales) {
        if (this.detailSales != null) {
            this.detailSales.forEach(i -> i.setProduct(null));
        }
        if (detailSales != null) {
            detailSales.forEach(i -> i.setProduct(this));
        }
        this.detailSales = detailSales;
    }

    public Product detailSales(Set<DetailSale> detailSales) {
        this.setDetailSales(detailSales);
        return this;
    }

    public Product addDetailSale(DetailSale detailSale) {
        this.detailSales.add(detailSale);
        detailSale.setProduct(this);
        return this;
    }

    public Product removeDetailSale(DetailSale detailSale) {
        this.detailSales.remove(detailSale);
        detailSale.setProduct(null);
        return this;
    }

    public Set<Inventory> getInventories() {
        return this.inventories;
    }

    public void setInventories(Set<Inventory> inventories) {
        if (this.inventories != null) {
            this.inventories.forEach(i -> i.setProduct(null));
        }
        if (inventories != null) {
            inventories.forEach(i -> i.setProduct(this));
        }
        this.inventories = inventories;
    }

    public Product inventories(Set<Inventory> inventories) {
        this.setInventories(inventories);
        return this;
    }

    public Product addInventory(Inventory inventory) {
        this.inventories.add(inventory);
        inventory.setProduct(this);
        return this;
    }

    public Product removeInventory(Inventory inventory) {
        this.inventories.remove(inventory);
        inventory.setProduct(null);
        return this;
    }

    public Set<DetailAmountRecip> getDetailAmountRecips() {
        return this.detailAmountRecips;
    }

    public void setDetailAmountRecips(Set<DetailAmountRecip> detailAmountRecips) {
        if (this.detailAmountRecips != null) {
            this.detailAmountRecips.forEach(i -> i.setProduct(null));
        }
        if (detailAmountRecips != null) {
            detailAmountRecips.forEach(i -> i.setProduct(this));
        }
        this.detailAmountRecips = detailAmountRecips;
    }

    public Product detailAmountRecips(Set<DetailAmountRecip> detailAmountRecips) {
        this.setDetailAmountRecips(detailAmountRecips);
        return this;
    }

    public Product addDetailAmountRecip(DetailAmountRecip detailAmountRecip) {
        this.detailAmountRecips.add(detailAmountRecip);
        detailAmountRecip.setProduct(this);
        return this;
    }

    public Product removeDetailAmountRecip(DetailAmountRecip detailAmountRecip) {
        this.detailAmountRecips.remove(detailAmountRecip);
        detailAmountRecip.setProduct(null);
        return this;
    }

    public Set<DetailOrder> getDetailOrders() {
        return this.detailOrders;
    }

    public void setDetailOrders(Set<DetailOrder> detailOrders) {
        if (this.detailOrders != null) {
            this.detailOrders.forEach(i -> i.setProduct(null));
        }
        if (detailOrders != null) {
            detailOrders.forEach(i -> i.setProduct(this));
        }
        this.detailOrders = detailOrders;
    }

    public Product detailOrders(Set<DetailOrder> detailOrders) {
        this.setDetailOrders(detailOrders);
        return this;
    }

    public Product addDetailOrder(DetailOrder detailOrder) {
        this.detailOrders.add(detailOrder);
        detailOrder.setProduct(this);
        return this;
    }

    public Product removeDetailOrder(DetailOrder detailOrder) {
        this.detailOrders.remove(detailOrder);
        detailOrder.setProduct(null);
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product category(Category category) {
        this.setCategory(category);
        return this;
    }

    public Provider getProvider() {
        return this.provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Product provider(Provider provider) {
        this.setProvider(provider);
        return this;
    }

    public Presentation getPresentation() {
        return this.presentation;
    }

    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }

    public Product presentation(Presentation presentation) {
        this.setPresentation(presentation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", buyPrice=" + getBuyPrice() +
            "}";
    }
}
