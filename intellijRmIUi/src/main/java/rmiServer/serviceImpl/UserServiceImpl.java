package rmiServer.serviceImpl;
import com.google.gson.Gson;
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
import rmiApi.entity.User;
import rmiApi.entityService.UserService;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


/* Default constructor to throw RemoteException
     from its parent constructor */
public class UserServiceImpl extends UnicastRemoteObject implements UserService {

    public UserServiceImpl() throws RemoteException {
    }

    //method implemenation to getUser details by passing the email as a paramteer
    @Override
    public User getUser(String email) throws RemoteException {
        //Set user to null
        User user = null;
         /*Initialize the apache httpclient used to send http requests and response to the
        rest client
         */
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            //HTTP GET method allows to get user details according to the email using the rest api
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

            //Convert JSON response to user object
            Gson gson = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
             user = gson.fromJson(responseBody, User.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Return user
        return user;
    }

    //method implementation to add a new user by passing a user reference
    @Override
    public User addUser(User newUser) throws RemoteException {
            //get values from User reference passed
        newUser.getEmail();
        newUser.getPassword();
        /*Initialize the apache httpclient used to send http requests and response to the
        rest client
         */
        try(CloseableHttpClient httpclient = HttpClients.createDefault()){
            //HTTP POST method url add new user using rest api
            HttpPost httpPost = new HttpPost("http://localhost:8080/addUser");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            //Convert user object to JSON response
            Gson gson = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            String json = gson.toJson(newUser);

            //pass the json string request in the entity
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

        //Return the newly added user
        return newUser;
    }
}
