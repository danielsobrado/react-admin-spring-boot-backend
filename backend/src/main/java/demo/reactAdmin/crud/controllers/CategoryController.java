package demo.reactAdmin.crud.controllers;


import demo.reactAdmin.crud.entities.Category;
import demo.reactAdmin.crud.repos.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import demo.reactAdmin.entities.QueryParamWrapper;
import demo.reactAdmin.services.FilterService;
import demo.reactAdmin.utils.QueryParamExtractor;

@RestController
@RequestMapping("api/v1")
public class CategoryController {

    @Autowired
    private FilterService<Category, Integer> filterService;

    @Autowired
    private CategoryRepository repo;

    @RequestMapping(value = "categories", method = RequestMethod.POST)
    public Category create(@RequestBody Category category) {
        return repo.save(category);
    }

    @RequestMapping(value = "categories/{id}", method = RequestMethod.PUT)
    public Category update(@RequestBody Category category, @PathVariable int id) {
        category.id = id;
        return repo.save(category);
    }

    @RequestMapping(value = "categories/{id}/published/{value}", method = RequestMethod.POST)
    public Category publishedUpdate(@PathVariable int id, @PathVariable boolean value) {
        Category category = repo.findById(id).orElseThrow();
        category.published = value;
        return repo.save(category);
    }


    @RequestMapping(value = "categories/{id}", method = RequestMethod.GET)
    public Category getById(@PathVariable int id) {
        return repo.findById(id).orElseThrow();
    }

    @RequestMapping(value = "categories", method = RequestMethod.GET)
    public Iterable<Category> filterBy(
            @RequestParam(required = false, name = "filter") String filterStr,
            @RequestParam(required = false, name = "range") String rangeStr, @RequestParam(required = false, name="sort") String sortStr) {
        QueryParamWrapper wrapper = QueryParamExtractor.extract(filterStr, rangeStr, sortStr);
        return filterService.filterBy(wrapper, repo);
    }
}
