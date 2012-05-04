package behaviors;

import lejos.nxt.LCD;
import robot.Robot;

public class MoveForward extends RobotBehavior {

	boolean suppress = false;
	
	public MoveForward(Robot robot) {
		super(robot);
	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		suppress = false;
		LCD.clear(1);
		LCD.drawInt(5, 0, 1);
		getRobot().getDifferentialPilot().forward();
		while(!suppress) {
			Thread.yield();
		}
	}

	@Override
	public void suppress() {
		LCD.clear(1);
		LCD.drawInt(10, 0, 1);
		getRobot().getDifferentialPilot().stop();
		suppress = true;
	}

}
