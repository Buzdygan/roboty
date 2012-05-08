package behaviors;

import position.CurrentPositionBox;
import lejos.nxt.LCD;
import lejos.nxt.comm.RConsole;
import robot.Robot;

public class Kick extends RobotPositionBehavior {

	boolean suppressed = false;

	public Kick(Robot robot, CurrentPositionBox currentPositionBox) {
		super(robot, currentPositionBox);
	}

	@Override
	public boolean takeControl() {
		return getRobot().canHaveBall(getRobot().getUltrasonic().getDistance(),
				getRobot().getSeeker().getDirection());
	}

	@Override
	public void action() {
		LCD.clear(0);
		LCD.drawString("KICK", 0, 0);
		RConsole.println("=== KICK ===");

		getRobot().kick();
		getCurrentPositionBox().setGotBall(false);
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
