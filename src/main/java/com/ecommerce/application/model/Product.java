package com.ecommerce.application.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

@Entity()
@Table(name = "products")
public class Product extends BaseEntity{

    @Id
    @Column(nullable = false)
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private Long category_id;
    private String name;
    private String description;
    private Long price;
    private Integer stocks;

    // Relationships ManyToOne
    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    private Category category;

    public Product() { }

    public Product(Long category_id, String name, String description, Long price, Integer stocks) {
        this.category_id = category_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stocks = stocks;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getStocks() {
        return stocks;
    }

    public void setStocks(Integer stocks) {
        this.stocks = stocks;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", category_id=" + category_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stocks=" + stocks +
                '}';
    }
}
