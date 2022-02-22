package demo.reactAdmin.crud.entities;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "`BasketItem`")
public class BasketItem {
    @Id
    public String id;

    public BasketItem() {}

    @JsonCreator
    public BasketItem(String id) {
        this.id = id;
    }
}
