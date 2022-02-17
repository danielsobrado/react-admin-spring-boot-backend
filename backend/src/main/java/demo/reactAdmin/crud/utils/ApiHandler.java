package demo.reactAdmin.crud.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import demo.reactAdmin.utils.JSONUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@Service
public class ApiHandler {


    private final String USER_AGENT = "Mozilla/5.0";
    public String sendGet(String url, Map<String,String> headers) {

        URL obj = null;
        HttpURLConnection con = null;
        StringBuffer response = new StringBuffer();
        try {
            obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            for (String key : headers.keySet()) {
                String value = headers.get(key);
                con.setRequestProperty(key, value);
            }
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Access-Control-Allow-Origin", "*");
            con.setRequestProperty("Access-Control-Expose-Headers", "content-range, Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
            con.setRequestProperty("Access-Control-Allow-Headers", "content-range, Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;


            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return response.toString();

    }

    public String sendPost(String url, String bodyJson, Map<String,String> headers) {
        return helper(url, bodyJson, headers, "POST");
    }

    public String sendPatch(String url, String bodyJson, Map<String,String> headers) {

        System.out.println("\nSending 'PATCH' request to URL :"+ url);

        StringBuffer result = new StringBuffer();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPatch httpPatch = null;

        try {
            httpPatch = new HttpPatch(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        httpPatch.addHeader("User-Agent", USER_AGENT);
        StringEntity params = null;
        try {
            params = new StringEntity(bodyJson);
            System.out.println(bodyJson);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for (String key : headers.keySet()) {
            String value = headers.get(key);
            httpPatch.addHeader(key, value);
        }

        httpPatch.setEntity(params);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPatch);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader rd = null;
        try {
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line = "";
        try {
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        return result.toString();
    }

    private String helper(String url, String bodyJson, Map<String,String> headers, String method) {

        URL obj = null;
        HttpURLConnection con = null;
        StringBuffer response = new StringBuffer();
        try {
            obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod(method);
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Access-Control-Allow-Origin", "*");
            con.setRequestProperty("Access-Control-Expose-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
            con.setRequestProperty("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
            con.setRequestProperty("Content-Type", "application/json");
            for (String key : headers.keySet()) {
                String value = headers.get(key);
                con.setRequestProperty(key, value);
            }

            byte[] out = bodyJson.getBytes(StandardCharsets.UTF_8);
            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(out);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + bodyJson);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //print result
        return response.toString();

    }

    public static class TokenValue {
            public String token;
        }


    public String authenticate(String username, String password) {
        Map<String, String> headers = new HashMap<>();
        String bodyJson = "{\"username\":\""+username+"\", \"password\":\""+password+"\"}";
        TokenValue obj = JSONUtils.toObject(this.sendPost("http://localhost:8080/api/v1/auth/login",
                bodyJson, headers), TokenValue.class);
        String token = obj.token;
        System.out.println("authenticate: http://localhost:8080/api/v1/auth/login");
        System.out.println("Json: "+bodyJson);
        System.out.println("token: "+obj.token);
        return token;
    }
}
