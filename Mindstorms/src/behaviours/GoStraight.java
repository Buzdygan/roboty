package behaviours;

import robot.Robot;
import lejos.nxt.LCD;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*;

public class GoStraight implements Behavior{
	private boolean suppressed = false;
	DifferentialPilot pilot;
	Robot robot;
	
	public GoStraight(DifferentialPilot pil, Robot rob)
	{
		pilot = pil;
		robot = rob;
	}
	
	public boolean takeControl(){
		int direction = robot.getSeeker().getDirection();
		return direction == 5;
	}
	
	public void suppress(){
		suppressed = true;
	}
	
	public void action(){
		LCD.drawString("go straight", 0, 0);
		suppressed = false;
		pilot.setRotateSpeed(pilot.getRotateMaxSpeed() / 15);
		pilot.forward();
		while(!suppressed)
			Thread.yield();
		pilot.stop();
	}

}
