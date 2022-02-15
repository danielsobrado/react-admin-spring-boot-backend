package demo.reactAdmin.crud.repos;

import demo.reactAdmin.crud.entities.Product;
import springboot.rest.repositories.BaseRepository;

public interface ProductRepository extends BaseRepository<Product, Integer> {
}
