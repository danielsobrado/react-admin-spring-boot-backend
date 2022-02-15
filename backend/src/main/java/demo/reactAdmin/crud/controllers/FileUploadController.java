package demo.reactAdmin.crud.controllers;

import demo.reactAdmin.crud.entities.UploadFile;
import demo.reactAdmin.crud.repos.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("api/v1")
public class FileUploadController {
    @Autowired
    ServletContext context;

    @Autowired
    private FileRepository repo;

    @Autowired
    private Environment env;

    private class FileInfo {

        private String fileName;
        private long fileSize;
        private int id;
        private String property;


        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public long getFileSize() {
            return fileSize;
        }

        public void setFileSize(long fileSize) {
            this.fileSize = fileSize;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }
    }

    @RequestMapping(value = "file/{id}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> getFile(@PathVariable int id )
            throws IOException {
        File file = new File( context.getRealPath(repo.findById(id).orElseThrow().diskPath));


        return ResponseEntity
                .ok()
                .contentLength(file.length())
                .contentType(
                        MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(new FileInputStream(file)));
    }

    @RequestMapping(value = "upload", headers=("content-type=multipart/*"), method = RequestMethod.POST)
    public ResponseEntity<FileInfo> upload(@RequestParam("file") MultipartFile inputFile, @RequestParam("property") String property) {
        FileInfo fileInfo = new FileInfo();
        HttpHeaders headers = new HttpHeaders();
        if (!inputFile.isEmpty()) {
            try {
                String originalFilename = inputFile.getOriginalFilename();
                String pathToStore = "/WEB-INF/uploaded" +  File.separator + originalFilename;
                File destinationFile = new File(context.getRealPath("/WEB-INF/uploaded")+  File.separator + originalFilename);
                inputFile.transferTo(destinationFile);
                fileInfo.setFileName(pathToStore);
                fileInfo.setFileSize(inputFile.getSize());

                UploadFile file = new UploadFile();
                file.diskPath = pathToStore;
                repo.save(file);
                file.path = env.getProperty("react-admin-api")+"/api/v1/file/"+ file.id;
                repo.save(file);

                fileInfo.setId(file.id);
                fileInfo.setProperty(property);

                headers.add("File Uploaded Successfully - ", originalFilename);
                return new ResponseEntity<>(fileInfo, headers, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}