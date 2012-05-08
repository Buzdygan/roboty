package behaviors;

import lejos.nxt.comm.RConsole;
import robot.Robot;

public class Kick extends RobotBehavior {

	boolean suppressed = false;

	public Kick(Robot robot) {
		super(robot);
	}

	@Override
	public boolean takeControl() {
		return getRobot().canHaveBall(getRobot().getUltrasonic().getDistance(),
				getRobot().getSeeker().getDirection());
	}

	@Override
	public void action() {
		RConsole.println("=== KICK ===");

		getRobot().kick();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
