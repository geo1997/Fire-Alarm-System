package service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ServiceImpl {

	public   List<Integer> getIds(){
		
		List <Integer> Ids = null;
		
		  try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

	            //HTTP GET method
	            HttpGet httpget = new HttpGet("http://localhost:8080/getIDs");
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

	            Gson gson = new Gson();
	            Type alarmListIds = new TypeToken<ArrayList<Integer>>(){}.getType();
	              Ids = gson.fromJson(responseBody,alarmListIds);
	              

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return Ids;
	}

	
	public  void updateFields(int co2, int smoke , int id) {
		
		 try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
             HttpPut httpPut = new HttpPut("http://localhost:8080/updateFields/"+co2+"/"+smoke+"/"+id);
             httpPut.setHeader("Accept", "application/json");
             httpPut.setHeader("Content-type", "application/json");

//             Gson gson = FxGson.coreBuilder().setPrettyPrinting().disableHtmlEscaping().create();
//             String json = gson.toJson(alarm);
//
//             StringEntity stringEntity = new StringEntity(json);
//             httpPut.setEntity(stringEntity);
//
//             System.out.println("Executing request " + httpPut.getRequestLine());

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

	}
	
	
	
	

	
}
