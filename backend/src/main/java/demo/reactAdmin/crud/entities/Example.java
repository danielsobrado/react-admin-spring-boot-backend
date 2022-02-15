package demo.reactAdmin.crud.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Where(clause="published=1")
public class Example {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public String name;

    public boolean published = true;


    @OneToMany(cascade = {CascadeType.DETACH})
    public Set<UploadFile> fileA = new HashSet<>();

    @OneToMany(cascade = {CascadeType.DETACH})
    public Set<UploadFile> fileB = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.DETACH})
    public Client client;

    @JsonCreator
    public Example(int id) {
        this.id = id;
    }

    public Example() {
        this.id = id;
    }


}
