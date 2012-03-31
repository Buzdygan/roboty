package behaviours;

import lejos.robotics.subsumption.*;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import robot.Robot;

public class TurnLeft implements Behavior{
	private boolean suppressed = false;
	
	public boolean takeControl(){
		Robot robot = new Robot();
		int direction = robot.getSeeker().getDirection();
		return direction < 5;
	}
	
	public void suppress(){
		suppressed = true;
	}
	
	public void action(){
		suppressed = false;
		DifferentialPilot pilot = new DifferentialPilot(40, 15, Motor.A, Motor.B);
		pilot.setRotateSpeed(pilot.getRotateMaxSpeed() / 15);
		pilot.rotateLeft();
		
		while(!suppressed)
			Thread.yield();
		pilot.stop();
	}
}
