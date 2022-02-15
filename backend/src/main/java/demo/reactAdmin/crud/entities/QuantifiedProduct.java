package demo.reactAdmin.crud.entities;

import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Entity(name = "QuantifiedProduct")
public class QuantifiedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Formula("(SELECT p.id FROM product p INNER JOIN quantified_product qp ON p.id = qp.product_id WHERE qp.id = id)")
    public Integer productId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    public Product product;

    public int quantity;

    public QuantifiedProduct() {}

    public QuantifiedProduct(Product product) {
        this.product = product;
    }
}