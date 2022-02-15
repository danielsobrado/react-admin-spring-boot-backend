package demo.reactAdmin.crud.controllers;


import demo.reactAdmin.crud.entities.Command;
import demo.reactAdmin.crud.entities.QuantifiedProduct;
import demo.reactAdmin.crud.repos.CommandRepository;
import demo.reactAdmin.crud.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springboot.rest.entities.QueryParamWrapper;
import springboot.rest.services.FilterService;
import springboot.rest.utils.QueryParamExtractor;

@RestController
@RequestMapping("api/v1")
public class CommandController {

    @Autowired
    private FilterService<Command, Integer> filterService;

    @Autowired
    private CommandRepository repo;

    @Autowired
    private ProductRepository productRepo;

    @RequestMapping(value = "commands", method = RequestMethod.POST)
    public Command create(@RequestBody Command command) {
        for (QuantifiedProduct qp: command.basket) {
            qp.product = productRepo.findById(qp.productId).orElseThrow();
        }
        return repo.save(command);
    }

    @RequestMapping(value = "commands/{id}", method = RequestMethod.PUT)
    public Command update(@RequestBody Command command, @PathVariable int id) {
        command.id = id;
        for (QuantifiedProduct qp: command.basket) {
            qp.product = productRepo.findById(qp.productId).orElseThrow();
        }
        return repo.save(command);
    }

    @RequestMapping(value = "commands/{id}/published/{value}", method = RequestMethod.POST)
    public Command publishedUpdate(@PathVariable int id, @PathVariable boolean value) {
        Command command = repo.findById(id).orElseThrow();
        command.published = value;
        return repo.save(command);
    }

    @RequestMapping(value = "commands/{id}", method = RequestMethod.GET)
    public Command getById(@PathVariable int id) {
        return repo.findById(id).orElseThrow();
    }

    @RequestMapping(value = "commands", method = RequestMethod.GET)
    public Iterable<Command> filterBy(
            @RequestParam(required = false, name = "filter") String filterStr,
            @RequestParam(required = false, name = "range") String rangeStr, @RequestParam(required = false, name="sort") String sortStr) {
        QueryParamWrapper wrapper = QueryParamExtractor.extract(filterStr, rangeStr, sortStr);
        return filterService.filterBy(wrapper, repo);
    }
}