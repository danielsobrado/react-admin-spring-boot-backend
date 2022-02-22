package demo.reactAdmin.crud.entities;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "`Order`")
public class Order {
    @Id
    public String id;

    public Order() {}

    @JsonCreator
    public Order(String id) {
        this.id = id;
    }
}
