package demo.reactAdmin.crud.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.hibernate.annotations.Where;

import demo.reactAdmin.crud.entities.deserializers.CommandDeserializer;
import demo.reactAdmin.crud.entities.deserializers.CustomCustomerSerializer;

@Entity
@Where(clause="published=1")
// @JsonDeserialize(using = CommandDeserializer.class)
public class Command {
    @Id
    public Integer id;

    public boolean published = true;
    public String reference;
    public String date;

    @ManyToOne(cascade = {CascadeType.DETACH})
    // @JsonIdentityReference(alwaysAsId = true)
    @JsonSerialize(using = CustomCustomerSerializer.class)
    public Customer customerId;

    public float totalExTaxes;
    public float deliveryFees;
    public float taxRate;
    public float taxes;
    public float total;
    public String status;
    public boolean returned;

    @OneToMany(cascade = {CascadeType.ALL})
    public Set<QuantifiedProduct> basket = new HashSet<>();


    public Command() {}

    @JsonCreator
    public Command(int id) {
        this.id = id;
    }


}