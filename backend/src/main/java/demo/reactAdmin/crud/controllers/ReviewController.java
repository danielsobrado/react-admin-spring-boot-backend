package demo.reactAdmin.crud.controllers;

import demo.reactAdmin.crud.entities.Review;
import demo.reactAdmin.crud.repos.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import demo.reactAdmin.entities.QueryParamWrapper;
import demo.reactAdmin.services.FilterService;
import demo.reactAdmin.utils.QueryParamExtractor;

@RestController
@RequestMapping("api/v1")
public class ReviewController {

    @Autowired
    private FilterService<Review, Integer> filterService;

    @Autowired
    private ReviewRepository repo;

    @RequestMapping(value = "reviews", method = RequestMethod.POST)
    public Review create(@RequestBody Review review) {
        return repo.save(review);
    }

    @RequestMapping(value = "reviews/{id}", method = RequestMethod.PUT)
    public Review update(@RequestBody Review review, @PathVariable int id) {
        review.id = id;
        return repo.save(review);
    }

    @RequestMapping(value = "reviews/{id}/published/{value}", method = RequestMethod.POST)
    public Review publishedUpdate(@PathVariable int id, @PathVariable boolean value) {
        Review review = repo.findById(id).orElseThrow();
        review.published = value;
        return repo.save(review);
    }

    @RequestMapping(value = "reviews/{id}", method = RequestMethod.GET)
    public Review getById(@PathVariable int id) {
        return repo.findById(id).orElseThrow();
    }

    @RequestMapping(value = "reviews", method = RequestMethod.GET)
    public Iterable<Review> filterBy(
            @RequestParam(required = false, name = "filter") String filterStr,
            @RequestParam(required = false, name = "range") String rangeStr, @RequestParam(required = false, name="sort") String sortStr) {
        QueryParamWrapper wrapper = QueryParamExtractor.extract(filterStr, rangeStr, sortStr);
        return filterService.filterBy(wrapper, repo);
    }
}
