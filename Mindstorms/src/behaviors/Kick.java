package behaviors;

import lejos.nxt.LCD;
import robot.Robot;

public class Kick extends RobotBehavior {
	
	boolean suppressed = false;
	
	public Kick(Robot robot) {
		super(robot);
	}

	@Override
	public boolean takeControl() {
		return getRobot().canHaveBall(
				getRobot().getUltrasonic().getDistance(), 
				getRobot().getSeeker().getDirection());
	}

	@Override
	public void action() {
		LCD.clear(0);
		LCD.drawString("kick", 0, 0);
		
		getRobot().kick();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
