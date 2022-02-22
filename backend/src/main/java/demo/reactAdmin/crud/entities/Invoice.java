package demo.reactAdmin.crud.entities;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "`Invoice`")
public class Invoice {
    @Id
    public String id;

    public Invoice() {}

    @JsonCreator
    public Invoice(String id) {
        this.id = id;
    }
}
