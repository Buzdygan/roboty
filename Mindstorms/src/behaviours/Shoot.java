package behaviours;

import robot.Robot;

public class Shoot extends RobotBehavior {

	public Shoot(Robot robot) {
		super(robot);
	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		getRobot().kick();
	}

	@Override
	public void suppress() {}

}
