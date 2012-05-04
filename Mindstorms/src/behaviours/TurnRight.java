package behaviours;
import robot.Robot;

public class TurnRight extends RobotBehavior {

	private boolean suppressed = false;
	
	public TurnRight(Robot robot) {
		super(robot);
	}
	
	public boolean takeControl(){
		int direction = getRobot().getSeeker().getDirection();
		return direction > 5;
	}
	
	public void suppress(){
		suppressed = true;
	}
	
	public void action(){
		suppressed = false;
		getRobot().getDifferentialPilot().setRotateSpeed(getRobot().getDifferentialPilot().getRotateMaxSpeed() / 15);
		getRobot().getDifferentialPilot().rotateRight();
		
		while(!suppressed)
			Thread.yield();
		getRobot().getDifferentialPilot().stop();
	}

}
