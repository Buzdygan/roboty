package behaviours;
import lejos.nxt.*;
import robot.Robot;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*;

public class TurnRight implements Behavior{
	private boolean suppressed = false;
	
	public boolean takeControl(){
		Robot robot = new Robot();
		int direction = robot.getSeeker().getDirection();
		return direction > 5;
	}
	
	public void suppress(){
		suppressed = true;
	}
	
	public void action(){
		suppressed = false;
		DifferentialPilot pilot = new DifferentialPilot(40, 15, Motor.A, Motor.B);
		pilot.setRotateSpeed(pilot.getRotateMaxSpeed() / 15);
		pilot.rotateRight();
		
		while(!suppressed)
			Thread.yield();
		pilot.stop();
	}

}
