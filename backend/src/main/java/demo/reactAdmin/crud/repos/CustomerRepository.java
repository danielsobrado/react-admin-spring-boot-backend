package demo.reactAdmin.crud.repos;

import demo.reactAdmin.crud.entities.Customer;
import springboot.rest.repositories.BaseRepository;

public interface CustomerRepository extends BaseRepository<Customer, Integer> {
}
