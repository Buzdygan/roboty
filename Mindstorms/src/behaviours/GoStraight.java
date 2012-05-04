package behaviours;

import lejos.nxt.LCD;
import robot.Robot;

public class GoStraight extends RobotBehavior {

	private boolean suppressed = false;
	
	public GoStraight(Robot robot) {
		super(robot);
	}
	
	
	public boolean takeControl(){
		int direction = getRobot().getSeeker().getDirection();
		return direction == 5;
	}
	
	public void suppress(){
		suppressed = true;
	}
	
	public void action(){
		LCD.drawString("go straight", 0, 0);
		suppressed = false;
		getRobot().getDifferentialPilot().setRotateSpeed(getRobot().getDifferentialPilot().getRotateMaxSpeed() / 15);
		getRobot().getDifferentialPilot().forward();
		while(!suppressed)
			Thread.yield();
		getRobot().getDifferentialPilot().stop();
	}

}
