package demo.reactAdmin.crud.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Where(clause="published=1")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String reference;
    public float width;
    public float height;
    public float price;
    public String thumbnail;
    public String image;
    @Lob @Basic(fetch = FetchType.EAGER)
    @Column(length=1000)
    public String description;
    public int stock;
    public boolean published = true;

    @ManyToOne(cascade = {CascadeType.DETACH})
    public Category categoryId;

    public Product() {}

    @JsonCreator
    public Product(int id) {
        this.id = id;
    }
}