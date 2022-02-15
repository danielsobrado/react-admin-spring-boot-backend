package demo.reactAdmin.crud.services;


import demo.reactAdmin.auth.PasswordEncoderProvider;
import demo.reactAdmin.crud.entities.Group;
import demo.reactAdmin.crud.entities.PlatformUser;
import demo.reactAdmin.crud.repos.ClientRepository;
import demo.reactAdmin.crud.repos.ExampleRepository;
import demo.reactAdmin.crud.repos.GroupRepository;
import demo.reactAdmin.crud.repos.UserRepository;
import demo.reactAdmin.crud.utils.ApiHandler;
import demo.reactAdmin.utils.JSONUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Service
public class DataInitService {

    @Autowired
    private ExampleRepository exampleRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private PasswordEncoderProvider passEncodeProvider;

    @Autowired
    ServletContext context;

    @Autowired
    ApiHandler apiHandler;


    public void init() {





//        UploadFile file = new UploadFile();
//        file.path = "C:\\Users\\Michael\\Documents\\GitHub\\aor-demo\\src\\main\\webapp\\WEB-INF\\uploaded\\ParentMapper.txt";
//        fileRepository.save(file);
//
//        UploadFile fileRef = new UploadFile();
//        fileRef.id = 1;


//
//        Client client = new Client();
//        client.name = "client 1";
//        client.password = passEncodeProvider.getEncoder().encode("client1");
//        client.username = "client1";
//        clientRepository.save(client);
//
//        Client clientRef = new Client();
//        clientRef.id = 1;
//
//
//        Example e1 = new Example();
//        e1.client = clientRef;
//
//
//        Example e2 = new Example();
//        e2.client = clientRef;
//
//        exampleRepository.save(e1);
//        exampleRepository.save(e2);

        //collector,compulsive,regular, returns, reviewer, ordered_once
        Group collector = new Group("collector");
        groupRepository.save(collector);

        Group compulsive = new Group("compulsive");
        groupRepository.save(compulsive);

        Group regular = new Group("regular");
        groupRepository.save(regular);

        Group returns = new Group("returns");
        groupRepository.save(returns);

        Group reviewer = new Group("reviewer");
        groupRepository.save(reviewer);

        Group orderedOnce = new Group("ordered_once");
        groupRepository.save(orderedOnce);




        PlatformUser admin = new PlatformUser();
        admin.username = "demo";
        admin.password = passEncodeProvider.getEncoder().encode("demo");
        userRepository.save(admin);


        File dataFile = new File(context.getRealPath("/WEB-INF/uploaded/data.json"));
        FileInputStream fis = null;
        JSONObject jsonObj = null;
        try {
            fis = new FileInputStream(dataFile);
            byte[] data = new byte[(int) dataFile.length()];
            fis.read(data);
            fis.close();
            String dataStr = new String(data, "UTF-8");
            jsonObj = JSONUtils.toJsonObject(dataStr);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String token = apiHandler.authenticate("demo", "demo");
        Map<String, String> headers = new HashMap<>();
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("X-Authorization", "Bearer "+token);
        String[] keys = {"categories", "customers", "products", "commands", "reviews"};



        for (String key : keys) {
            JSONArray objects = ((JSONArray)jsonObj.get(key));
            for (int i = 0; i < objects.length(); i++) {
                JSONObject object = objects.getJSONObject(i);
                incrementValue(object, Arrays.asList("id", "product_id", "category_id", "customer_id", "command_id"));
                apiHandler.sendPost("http://localhost:8080/api/v1/"+key+"/",object.toString(), headers);
            }
        }
    }

    //https://stackoverflow.com/a/46633583/986160
    public static void incrementValue(JSONObject obj, List<String> keysToIncrementValue) {
        Set<String> keys = obj.keySet();
        for (String key : keys) {
            Object ob = obj.get(key);

            if (keysToIncrementValue.contains(key)) {
                obj.put(key, (Integer)obj.get(key) + 1);
            }

            if (ob instanceof JSONObject) {
                incrementValue((JSONObject) ob, keysToIncrementValue);
            }
            else if (ob instanceof JSONArray) {
                JSONArray arr = (JSONArray) ob;
                for (int i=0; i < arr.length(); i++) {
                    Object arrObj = arr.get(0);
                    if (arrObj instanceof JSONObject) {
                        incrementValue((JSONObject) arrObj, keysToIncrementValue);
                    }
                }
            }
        }
    }

}
