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

import rmiApi.entityService.SmsService;
import rmiApi.entity.SmsRequest;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SmsServiceImpl extends UnicastRemoteObject  implements SmsService {
    public SmsServiceImpl() throws RemoteException {
    }

    //method implementation to send sms
    @Override
    public void sendSms(SmsRequest smsRequest) throws RemoteException {
        //get the data from the smsRequest reference passed
        smsRequest.getPhoneNumber();
        smsRequest.getMessage();

        /*Initialize the apache httpclient used to send http requests and response to the
        rest client
         */
        try(CloseableHttpClient httpclient = HttpClients.createDefault()){
            //HTTP POST request to send sms using the REST api
            HttpPost httpPost = new HttpPost("http://localhost:8080/api/sms");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

           //convert smsRequest object to JSON
            Gson gson = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            String json = gson.toJson(smsRequest);


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
            System.out.println("sms sent successfully");
            System.out.println(responseBody);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //method to set smsRequest
    @Override
    public SmsRequest sendData(int floorLevel, int RoomNum, int co2, int smoke)  throws RemoteException{
        SmsRequest sms = new SmsRequest();
        sms.setPhoneNumber("+94770044814");
        sms.setMessage("Alert : Co2 level: "+co2+" Smoke level: "+smoke+" In Floor "+floorLevel+" Room Number "+RoomNum);

        //return the sms reference with set values.
        return sms;
    }
}
