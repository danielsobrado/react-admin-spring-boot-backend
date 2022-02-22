package demo.reactAdmin.crud.controllers;


import demo.reactAdmin.crud.entities.Order;
import demo.reactAdmin.crud.repos.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import demo.reactAdmin.entities.QueryParamWrapper;
import demo.reactAdmin.services.FilterService;
import demo.reactAdmin.utils.QueryParamExtractor;

@RestController
@RequestMapping("api/v1")
public class OrderController {

    @Autowired
    private FilterService<Order, Integer> filterService;

    @Autowired
    private OrderRepository repo;

    @RequestMapping(value = "orders", method = RequestMethod.POST)
    public Order create(@RequestBody Order order) {
        return repo.save(order);
    }

    @RequestMapping(value = "orders/{id}", method = RequestMethod.PUT)
    public Order update(@RequestBody Order order, @PathVariable int id) {
        // order.id = id;
        return repo.save(order);
    }

    @RequestMapping(value = "orders/{id}/published/{value}", method = RequestMethod.POST)
    public Order publishedUpdate(@PathVariable int id, @PathVariable boolean value) {
        Order order = repo.findById(id).orElseThrow();
        // order.published = value;
        return repo.save(order);
    }


    @RequestMapping(value = "orders/{id}", method = RequestMethod.GET)
    public Order getById(@PathVariable int id) {
        return repo.findById(id).orElseThrow();
    }

    @RequestMapping(value = "orders", method = RequestMethod.GET)
    public Iterable<Order> filterBy(
            @RequestParam(required = false, name = "filter") String filterStr,
            @RequestParam(required = false, name = "range") String rangeStr, @RequestParam(required = false, name="sort") String sortStr) {
        QueryParamWrapper wrapper = QueryParamExtractor.extract(filterStr, rangeStr, sortStr);
        return filterService.filterBy(wrapper, repo);
    }
}
