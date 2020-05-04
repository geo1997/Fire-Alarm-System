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

//The AlarmServiceImpl extends UnicastRemoteObject
public class AlarmServiceImpl extends UnicastRemoteObject implements alarmService {

    /* Default constructor to throw RemoteException
       from its parent constructor */
    public AlarmServiceImpl() throws RemoteException {
    }

    @Override
    public Alarm saveAlarm(Alarm alarm) throws RemoteException {


        //get values from the AlarmForm
        alarm.getFloorNum();
        alarm.getRoomNum();
        alarm.getSmokeLevel();
        alarm.getCo2level();

        /*Initialize the apache httpclient used to send http requests and response to the
        rest client
         */
        try(CloseableHttpClient httpclient = HttpClients.createDefault()){
            //HTTP POST method url add alarms using rest api
            HttpPost httpPost = new HttpPost("http://localhost:8080/addAlarm");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            //Convert alarm object to JSON response
            Gson gson = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
          String json = gson.toJson(alarm);

            //pass the json string request in the entity
            StringEntity stringEntity = new StringEntity(json);
            httpPost.setEntity(stringEntity);
            System.out.println("Executing request " + httpPost.getRequestLine());

            //Check the response status
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
        //Returns the newly created alarm object
        return alarm;
    }

    @Override
    public List<Alarm> getAlarms() throws RemoteException {
        ArrayList <Alarm> alarms = null;
        /*Initialize the apache httpclient used to send http requests and response to the
        rest client
         */
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            //HTTP GET method url retrieves alarms using rest api
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

            //Allow GSON to identify array list of alarm type
            Type alarmListType = new TypeToken<ArrayList<Alarm>>(){}.getType();
            //Convert JSON response to alarm list
            Gson gson = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
              alarms = gson.fromJson(responseBody,alarmListType);

        } catch (IOException e) {
            e.printStackTrace();
        }
        //Returns alarm list
        return alarms;
    }


    @Override
    public void deleteAlarm(int id) throws RemoteException {
        /*Initialize the apache httpclient used to send http requests and response to the
        rest client
         */
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            //HTTP DELETE method url deletes alarms according to their id using rest api


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
        //get values from the alarm reference passed
        alarm.getId();
        alarm.getFloorNum();
        alarm.getRoomNum();
        alarm.getSmokeLevel();
        alarm.getCo2level();


        /*Initialize the apache httpclient used to send http requests and response to the
        rest client
         */
            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                //HTTP PUT method url updates alarms according to their id using rest api
                HttpPut httpPut = new HttpPut("http://localhost:8080/updateAlarm");
                httpPut.setHeader("Accept", "application/json");
                httpPut.setHeader("Content-type", "application/json");

                //Convert alarm object to JSON response
                Gson gson = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
                String json = gson.toJson(alarm);

                //pass the json string request in the entity
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


        //Returns the updated alarm object
        return alarm;
    }



}
