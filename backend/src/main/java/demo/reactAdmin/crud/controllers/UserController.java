package demo.reactAdmin.crud.controllers;

import demo.reactAdmin.crud.entities.PlatformUser;
import demo.reactAdmin.crud.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import demo.reactAdmin.entities.QueryParamWrapper;
import demo.reactAdmin.services.FilterService;
import demo.reactAdmin.utils.QueryParamExtractor;

@RestController
@RequestMapping("api/v1")
public class UserController {

    @Autowired
    private FilterService<PlatformUser, Integer> filterService;

    @Autowired
    private UserRepository repo;

    @RequestMapping(value = "current-user", method = RequestMethod.GET)
    public PlatformUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username
        return repo.findOneByUsername(username);
    }

    @RequestMapping(value = "users/{id}", method = RequestMethod.GET)
    public PlatformUser getById(@PathVariable int id) {
        return repo.findById(id).orElseThrow();
    }


    @RequestMapping(value = "users/{id}/published/{value}", method = RequestMethod.POST)
    public void publishedUpdate(@PathVariable int id, @PathVariable boolean value) {
        PlatformUser user = repo.findById(id).orElseThrow();
        user.published = value;
        repo.save(user);
    }

    @RequestMapping(value = "users", method = RequestMethod.GET)
    public Iterable<PlatformUser> filterBy(
            @RequestParam(required = false, name = "filter") String filterStr,
            @RequestParam(required = false, name = "range") String rangeStr, @RequestParam(required = false, name="sort") String sortStr) {
        QueryParamWrapper wrapper = QueryParamExtractor.extract(filterStr, rangeStr, sortStr);
        return filterService.filterBy(wrapper, repo);
    }
}
