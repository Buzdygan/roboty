package behaviours;

import lejos.robotics.subsumption.*;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import robot.Robot;

public class TurnLeft implements Behavior{
	private boolean suppressed = false;
	DifferentialPilot pilot;
	Robot robot;
	
	public TurnLeft(DifferentialPilot pil, Robot rob)
	{
		pilot = pil;
		robot = rob;
	}
	
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
		pilot.setRotateSpeed(pilot.getRotateMaxSpeed() / 15);
		pilot.rotateLeft();
		
		while(!suppressed)
			Thread.yield();
		pilot.stop();
	}
}
