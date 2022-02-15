package demo.reactAdmin.crud.controllers;


import demo.reactAdmin.crud.entities.Product;
import demo.reactAdmin.crud.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springboot.rest.entities.QueryParamWrapper;
import springboot.rest.services.FilterService;
import springboot.rest.utils.QueryParamExtractor;

@RestController
@RequestMapping("api/v1")
public class ProductController {

    @Autowired
    private FilterService<Product, Integer> filterService;

    @Autowired
    private ProductRepository repo;

    @RequestMapping(value = "products", method = RequestMethod.POST)
    public Product create(@RequestBody Product product) {
        return repo.save(product);
    }

    @RequestMapping(value = "products/{id}", method = RequestMethod.PUT)
    public Product update(@RequestBody Product product, @PathVariable int id) {
        product.id = id;
        return repo.save(product);
    }

    @RequestMapping(value = "products/{id}/published/{value}", method = RequestMethod.POST)
    public Product publishedUpdate(@PathVariable int id, @PathVariable boolean value) {
        Product product = repo.findById(id).orElseThrow();
        product.published = value;
        return repo.save(product);
    }

    @RequestMapping(value = "products/{id}", method = RequestMethod.GET)
    public Product getById(@PathVariable int id) {
        return repo.findById(id).orElseThrow();
    }

    @RequestMapping(value = "products", method = RequestMethod.GET)
    public Iterable<Product> filterBy(
            @RequestParam(required = false, name = "filter") String filterStr,
            @RequestParam(required = false, name = "range") String rangeStr, @RequestParam(required = false, name="sort") String sortStr) {
        QueryParamWrapper wrapper = QueryParamExtractor.extract(filterStr, rangeStr, sortStr);
        return filterService.filterBy(wrapper, repo);
    }
}
