package rmiServer.serviceImpl;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hildan.fxgson.FxGson;
import rmiApi.entity.MailRequest;
import rmiApi.entityService.Emailservice;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class EmailServiceImpl extends UnicastRemoteObject implements Emailservice {
    public EmailServiceImpl() throws RemoteException {
    }

    //method implementation to send email
    @Override
    public void sendEmail(MailRequest request) throws RemoteException  {
        //get the data passed from the request parameter
        request.getFrom();
        request.getTo();
        request.getSubject();
        request.getName();

        /*Initialize the apache httpclient used to send http requests and response to the
        rest client
         */
        try(CloseableHttpClient httpclient = HttpClients.createDefault()){
            //HTTP POST request to send an email using the REST api
            HttpPost httpPost = new HttpPost("http://localhost:8080/sendEmail");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            //convert the request object to JSON
            Gson gson = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            String json = gson.toJson(request);


            StringEntity stringEntity = new StringEntity(json);
            httpPost.setEntity(stringEntity);
            System.out.println("Executing request " + httpPost.getRequestLine());

            //custom header to handle http status
            ResponseHandler< String > responseHandler = response -> {
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


    }

    //method implementation to set the mail request
    @Override
    public MailRequest mailRequest(int floorLevel, int RoomNum) throws RemoteException {
        MailRequest req = new MailRequest();
        req.setTo("distributedsystems123@gmail.com");
        req.setFrom("distributedsystems123@gmail.com");
        req.setSubject("Hazardous levels in Floor Number "+floorLevel+" Room Number "+RoomNum);
        req.setName("Fire Alarm System");

        //returns the req reference with set values
        return req;
    }
}
