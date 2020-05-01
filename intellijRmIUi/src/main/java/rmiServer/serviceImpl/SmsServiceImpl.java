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
import rmiApi.sms.SmsRequest;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SmsServiceImpl extends UnicastRemoteObject  implements SmsService {
    public SmsServiceImpl() throws RemoteException {
    }

    @Override
    public void sendSms(SmsRequest smsRequest) throws RemoteException {

        smsRequest.getPhoneNumber();
        smsRequest.getMessage();


        try(CloseableHttpClient httpclient = HttpClients.createDefault()){
            HttpPost httpPost = new HttpPost("http://localhost:8080/api/sms");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            //Gson gson = new Gson();
            Gson gson = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            String json = gson.toJson(smsRequest);


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
    public SmsRequest sendData(int floorLevel, int RoomNum, int co2, int smoke)  throws RemoteException{
        SmsRequest sms = new SmsRequest();
        sms.setPhoneNumber("+94768817502");
        sms.setMessage("Alert : Co2 level: "+co2+" Smoke level: "+smoke+" In Floor "+floorLevel+" Room Number "+RoomNum);

        return sms;
    }
}
