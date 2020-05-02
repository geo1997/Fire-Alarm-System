package sensorApp;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import service.ServiceImpl;



class SmokeValueGen extends TimerTask{
	ServiceImpl serviceImpl = new ServiceImpl();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		List<Integer> ids = serviceImpl.getIds();
		
		
		int s = (int) (10.0 * Math.random() + 1);
		int c = (int) (10.0 * Math.random() + 1);
		//int i = (int) (5.0 * Math.random() + 1);
		//System.out.println("sensor id "+getRandomElement(ids)+" smoke "+smokeLevel(s)+ " co2 "+co2Level(c));
		
		//serviceImpl.updateFields(co2Level(c), smokeLevel(s), getRandomElement(ids)));
		int smoke = smokeLevel(s);
		int co2 = co2Level(c);
		int id = getRandomElement(ids);
		
		serviceImpl.updateFields(co2, smoke, id);
		System.out.println("c02 "+co2+" smoke "+smoke+ " id "+id);
		
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
		
		timer.schedule(smokeValueGen, 000,10000);
	}

}
