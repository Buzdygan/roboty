package behaviors;

import robot.Robot;

public class Kick extends RobotBehavior {
	
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
		getRobot().kick();
	}

	@Override
	public void suppress() {}

}
