package demo.reactAdmin.crud.entities;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.hibernate.annotations.Where;

import demo.reactAdmin.crud.entities.deserializers.CustomCustomerSerializer;
import demo.reactAdmin.crud.entities.deserializers.CustomProductSerializer;

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
    @JsonSerialize(using = CustomProductSerializer.class)
    public Product productId;

    @ManyToOne(cascade = {CascadeType.DETACH})
    @JsonSerialize(using = CustomCustomerSerializer.class)
    public Customer customerId;

    @Lob @Basic(fetch = FetchType.EAGER)
    @Column(length=1000)
    public String comment;

}
