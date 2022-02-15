package demo.reactAdmin.crud.entities;

import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Where(clause="published=1")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public boolean published = true;
    public String date;
    public String status;
    public int rating;

    @ManyToOne(cascade = {CascadeType.DETACH})
    public Command commandId;

    @ManyToOne(cascade = {CascadeType.DETACH})
    public Product productId;

    @ManyToOne(cascade = {CascadeType.DETACH})
    public Customer customerId;

    @Lob @Basic(fetch = FetchType.EAGER)
    @Column(length=1000)
    public String comment;

}
