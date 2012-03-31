package behaviours;

import lejos.robotics.subsumption.*;
import lejos.robotics.navigation.DifferentialPilot;

public class Wait implements Behavior{
	private boolean suppressed = false;
	DifferentialPilot pilot;
	
	public Wait(DifferentialPilot pil)
	{
		pilot = pil;
	}
	
	public boolean takeControl(){
		return true;
	}
	
	public void suppress(){
		suppressed = true;
	}
	
	public void action(){
		suppressed = false;
		pilot.stop();
		while(!suppressed)
			Thread.yield();
	}
}
