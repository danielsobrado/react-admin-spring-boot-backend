package demo.reactAdmin.crud.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Where(clause="published=1")
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String name;
    public boolean published = true;


    public Category() {}

    @JsonCreator
    public Category(int id) {
        this.id = id;
    }
}
