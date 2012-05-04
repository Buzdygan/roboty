package behaviours;

import lejos.nxt.LCD;
import robot.Robot;

public class StepBack extends RobotBehavior {
	
	public StepBack(Robot robot) {
		super(robot);
	}

	private boolean suppressed = false;

	public boolean takeControl(){
		int distance = getRobot().getUltrasonic().getDistance();
		LCD.clear(0);
		LCD.drawInt(distance, 0, 0);
		return (distance < 50);
	}
	
	public void suppress(){
		suppressed = true;
	}
	
	public void action(){
		suppressed = false;
		getRobot().getDifferentialPilot().travel(-10);
		while(getRobot().getDifferentialPilot().isMoving() & !suppressed)
			Thread.yield();
		getRobot().getDifferentialPilot().stop();
	}
}
