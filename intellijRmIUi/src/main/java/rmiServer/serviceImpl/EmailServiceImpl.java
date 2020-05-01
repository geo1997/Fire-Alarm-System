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
import rmiApi.email.MailRequest;
import rmiApi.entityService.Emailservice;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class EmailServiceImpl extends UnicastRemoteObject implements Emailservice {
    public EmailServiceImpl() throws RemoteException {
    }

    @Override
    public void sendEmail(MailRequest request) throws RemoteException  {

        request.getFrom();
        request.getTo();
        request.getSubject();
        request.getName();


        try(CloseableHttpClient httpclient = HttpClients.createDefault()){
            HttpPost httpPost = new HttpPost("http://localhost:8080/sendEmail");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            //Gson gson = new Gson();
            Gson gson = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            String json = gson.toJson(request);


            StringEntity stringEntity = new StringEntity(json);
            httpPost.setEntity(stringEntity);
            System.out.println("Executing request " + httpPost.getRequestLine());


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

    @Override
    public MailRequest mailRequest(int floorLevel, int RoomNum) throws RemoteException {
        MailRequest req = new MailRequest();
        req.setTo("distributedsystems123@gmail.com");
        req.setFrom("distributedsystems123@gmail.com");
        req.setSubject("Hazardous levels in Floor Number "+floorLevel+" Room Number "+RoomNum);
        req.setName("Alarm System");


        return req;
    }
}
