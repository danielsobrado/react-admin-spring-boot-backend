package demo.reactAdmin.crud.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Where(clause="published=1")
public class Command {
    @Id
    public Integer id;

    public boolean published = true;
    public String reference;
    public String date;

    @ManyToOne(cascade = {CascadeType.DETACH})
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