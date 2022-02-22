package demo.reactAdmin.crud.controllers;


import demo.reactAdmin.crud.entities.Invoice;
import demo.reactAdmin.crud.repos.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import demo.reactAdmin.entities.QueryParamWrapper;
import demo.reactAdmin.services.FilterService;
import demo.reactAdmin.utils.QueryParamExtractor;

@RestController
@RequestMapping("api/v1")
public class InvoiceController {

    @Autowired
    private FilterService<Invoice, Integer> filterService;

    @Autowired
    private InvoiceRepository repo;

    @RequestMapping(value = "invoices", method = RequestMethod.POST)
    public Invoice create(@RequestBody Invoice invoice) {
        return repo.save(invoice);
    }

    @RequestMapping(value = "invoices/{id}", method = RequestMethod.PUT)
    public Invoice update(@RequestBody Invoice invoice, @PathVariable int id) {
        // invoice.id = id;
        return repo.save(invoice);
    }

    @RequestMapping(value = "invoices/{id}/published/{value}", method = RequestMethod.POST)
    public Invoice publishedUpdate(@PathVariable int id, @PathVariable boolean value) {
        Invoice invoice = repo.findById(id).orElseThrow();
        // invoice.published = value;
        return repo.save(invoice);
    }


    @RequestMapping(value = "invoices/{id}", method = RequestMethod.GET)
    public Invoice getById(@PathVariable int id) {
        return repo.findById(id).orElseThrow();
    }

    @RequestMapping(value = "invoices", method = RequestMethod.GET)
    public Iterable<Invoice> filterBy(
            @RequestParam(required = false, name = "filter") String filterStr,
            @RequestParam(required = false, name = "range") String rangeStr, @RequestParam(required = false, name="sort") String sortStr) {
        QueryParamWrapper wrapper = QueryParamExtractor.extract(filterStr, rangeStr, sortStr);
        return filterService.filterBy(wrapper, repo);
    }
}
