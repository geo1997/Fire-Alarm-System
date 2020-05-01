package rmiServer.serviceImpl;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;


import org.apache.http.util.EntityUtils;
import org.hildan.fxgson.FxGson;
import rmiApi.entity.Alarm;
import rmiApi.entityService.alarmService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class AlarmServiceImpl extends UnicastRemoteObject implements alarmService {


    public AlarmServiceImpl() throws RemoteException {
    }

    @Override
    public Alarm saveAlarm(Alarm alarm) throws RemoteException {



        alarm.getFloorNum();
        alarm.getRoomNum();
        alarm.getSmokeLevel();
        alarm.getCo2level();


        try(CloseableHttpClient httpclient = HttpClients.createDefault()){
            HttpPost httpPost = new HttpPost("http://localhost:8080/addAlarm");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

          //Gson gson = new Gson();
            Gson gson = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
          String json = gson.toJson(alarm);


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

        return alarm;
    }

    @Override
    public List<Alarm> getAlarms() throws RemoteException {
        ArrayList <Alarm> alarms = null;


        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            //HTTP GET method
            HttpGet httpget = new HttpGet("http://localhost:8080/alarms");
            System.out.println("Executing request " + httpget.getRequestLine());

            // Create a custom response handler
            ResponseHandler < String > responseHandler = response -> {
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
            Type alarmListType = new TypeToken<ArrayList<Alarm>>(){}.getType();
            Gson gson = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
              alarms = gson.fromJson(responseBody,alarmListType);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return alarms;
    }

    @Override
    public Alarm getAlarmById(int id) throws RemoteException {



        return null;
    }

    @Override
    public void deleteAlarm(int id) throws RemoteException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {



            HttpDelete httpDelete = new HttpDelete("http://localhost:8080/alarmDelete/"+id);
            System.out.println("Executing request " + httpDelete.getRequestLine());

            // Create a custom response handler
            ResponseHandler < String > responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };
            String responseBody = httpclient.execute(httpDelete, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Alarm updateAlarm(Alarm alarm) throws RemoteException {

        alarm.getId();
        alarm.getFloorNum();
        alarm.getRoomNum();
        alarm.getSmokeLevel();
        alarm.getCo2level();



            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                HttpPut httpPut = new HttpPut("http://localhost:8080/updateAlarm");
                httpPut.setHeader("Accept", "application/json");
                httpPut.setHeader("Content-type", "application/json");

                Gson gson = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
                String json = gson.toJson(alarm);

                StringEntity stringEntity = new StringEntity(json);
                httpPut.setEntity(stringEntity);

                System.out.println("Executing request " + httpPut.getRequestLine());

                // Create a custom response handler
                ResponseHandler < String > responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
                };
                String responseBody = httpclient.execute(httpPut, responseHandler);
                System.out.println("----------------------------------------");
                System.out.println(responseBody);

        } catch (IOException e) {
            e.printStackTrace();
        }



        return alarm;
    }



}
