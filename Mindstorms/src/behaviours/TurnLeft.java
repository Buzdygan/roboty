package behaviours;

import robot.Robot;

public class TurnLeft extends RobotBehavior {

	private boolean suppressed = false;
	
	public TurnLeft(Robot robot) {
		super(robot);
	}
	
	public boolean takeControl(){
		Robot robot = new Robot();
		int direction = robot.getSeeker().getDirection();
		if(direction == 0)
			return false;
		return direction < 5;
	}
	
	public void suppress(){
		suppressed = true;
	}
	
	public void action(){
		suppressed = false;
		getRobot().getDifferentialPilot().setRotateSpeed(getRobot().getDifferentialPilot().getRotateMaxSpeed() / 15);
		getRobot().getDifferentialPilot().rotateLeft();
		
		while(!suppressed)
			Thread.yield();
		getRobot().getDifferentialPilot().stop();
	}
}
