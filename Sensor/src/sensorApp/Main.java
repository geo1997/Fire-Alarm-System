package sensorApp;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import service.ServiceImpl;



class SmokeValueGen extends TimerTask{
	//Reference to the ServicImpl class
	ServiceImpl serviceImpl = new ServiceImpl();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//Retrieve list of alarm ids using the getIds method.
		List<Integer> ids = serviceImpl.getIds();
		
		//generate random integer values between 1 and 10 to pass as co2 and smoke level
		int s = (int) (10.0 * Math.random() + 1);
		int c = (int) (10.0 * Math.random() + 1);
		int smoke = smokeLevel(s);
		int co2 = co2Level(c);
		//from the list of alarm ids pick a random alarm id
		int id = getRandomElement(ids);
		
		//update the co2 and smoke levels of a particular alarm imitating a real life sensor.
		serviceImpl.updateFields(co2, smoke, id);
		System.out.println("Sensor App running c02 "+co2+" smoke "+smoke+ " id "+id);
		
	}
	
	public int smokeLevel(int s) {
		return s;
	}
	
	public int co2Level(int c) {
		return c;
	}
	
	public int sensorid(int i) {
		return i;
	}
	
	//method to retrieve a random alarm id
	public int getRandomElement(List<Integer> list) 
    { 
        Random rand = new Random(); 
        return list.get(rand.nextInt(list.size())); 
    } 
	
}



public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		SmokeValueGen smokeValueGen = new SmokeValueGen();
		Timer timer = new Timer();
		
		//Every 10 seconds execute, to send sensor data to REST api
		timer.schedule(smokeValueGen, 000,10000);
	}

}
