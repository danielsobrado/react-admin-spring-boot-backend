package demo.reactAdmin.crud.repos;

import demo.reactAdmin.crud.entities.PlatformUser;
import springboot.rest.repositories.BaseRepository;

public interface UserRepository extends BaseRepository<PlatformUser, Integer> {
    PlatformUser findOneByUsername(String username);
}
