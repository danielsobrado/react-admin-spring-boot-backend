package demo.reactAdmin.crud.entities;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "`Group`")
public class Group {
    @Id
    public String id;

    public Group() {}

    @JsonCreator
    public Group(String id) {
        this.id = id;
    }
}
