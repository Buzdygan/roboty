package behaviours;

import robot.Robot;

public class Wait extends RobotBehavior {
	
	public Wait(Robot robot) {
		super(robot);
	}

	private boolean suppressed = false;

	public boolean takeControl(){
		return true;
	}
	
	public void suppress(){
		suppressed = true;
	}
	
	public void action(){
		suppressed = false;
		getRobot().getDifferentialPilot().stop();
		while(!suppressed)
			Thread.yield();
	}
}
