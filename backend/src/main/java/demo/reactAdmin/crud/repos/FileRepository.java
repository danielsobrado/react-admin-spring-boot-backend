package demo.reactAdmin.crud.repos;

import demo.reactAdmin.crud.entities.UploadFile;
import springboot.rest.repositories.BaseRepository;

public interface FileRepository extends BaseRepository<UploadFile, Integer> {
}
