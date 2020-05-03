package rmiServer.serviceImpl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hildan.fxgson.FxGson;
import rmiApi.entity.Alarm;
import rmiApi.entity.User;
import rmiApi.entityService.UserService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class UserServiceImpl extends UnicastRemoteObject implements UserService {

    public UserServiceImpl() throws RemoteException {
    }

    @Override
    public User getUser(String email) throws RemoteException {

        User user = null;
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            //HTTP GET method
            HttpGet httpget = new HttpGet("http://localhost:8080/getUser/"+email);
            System.out.println("Executing request " + httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler< String > responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            String responseBody = httpclient.execute(httpget, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);

            //Gson gson = new Gson();
            //Type alarmListType = new TypeToken<ArrayList<Alarm>>(){}.getType();
            Gson gson = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
             user = gson.fromJson(responseBody, User.class);

        } catch (IOException e) {
            e.printStackTrace();
        }



        return user;
    }

    @Override
    public User addUser(User newUser) throws RemoteException {

        newUser.getEmail();
        newUser.getPassword();


        try(CloseableHttpClient httpclient = HttpClients.createDefault()){
            HttpPost httpPost = new HttpPost("http://localhost:8080/addUser");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            //Gson gson = new Gson();
            Gson gson = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            String json = gson.toJson(newUser);


            StringEntity stringEntity = new StringEntity(json);
            httpPost.setEntity(stringEntity);
            System.out.println("Executing request " + httpPost.getRequestLine());


            ResponseHandler < String > responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };

            String responseBody = httpclient.execute(httpPost, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return newUser;
    }
}
