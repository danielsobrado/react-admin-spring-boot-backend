package demo.reactAdmin.crud.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.hibernate.annotations.Where;

import demo.reactAdmin.crud.entities.resolvers.CustomerIdResolver;

@Entity
@Where(clause="published=1")
// @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, 
//     resolver = CustomerIdResolver.class, property = "id")
// // See:
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String firstName;
    public String lastName;
    public String email;
    public String birthday;
    public String address;
    public String zipcode;
    public String city;
    public String avatar;
    public String firstSeen;
    public String lastSeen;
    public boolean hasNewsletter;
    public boolean hasOrdered;

    @ManyToMany(cascade = {CascadeType.DETACH})
    public Set<Group> groups;

    public String latestPurchase;
    public int nbCommands;
    public double totalSpent;
    public boolean published = true;

    public Customer() {}

    @JsonCreator
    public Customer(int id) {
        this.id = id;
    }
}
