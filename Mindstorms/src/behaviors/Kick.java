package behaviors;

import robot.Robot;

public class Kick extends RobotBehavior {
	
	private final int MAX_DISTANCE = 255;
	
	public Kick(Robot robot) {
		super(robot);
	}

	@Override
	public boolean takeControl() {
		return getRobot().getUltrasonic().getDistance() == MAX_DISTANCE; 
	}

	@Override
	public void action() {
		getRobot().kick();
	}

	@Override
	public void suppress() {}

}
