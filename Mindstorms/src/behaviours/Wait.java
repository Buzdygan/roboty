package behaviours;

import lejos.robotics.subsumption.*;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class Wait implements Behavior{
	private boolean suppressed = false;
	
	public boolean takeControl(){
		return true;
	}
	
	public void suppress(){
		suppressed = true;
	}
	
	public void action(){
		suppressed = false;
		DifferentialPilot pilot = new DifferentialPilot(40, 15, Motor.A, Motor.B);
		pilot.stop();
		while(!suppressed)
			Thread.yield();
	}
}
