package behaviors;
import position.CurrentPositionBox;
import robot.AlmostDifferentialPilot;
import robot.Robot;


public class Reset extends RobotPositionBehavior {

	public Reset(Robot robot, CurrentPositionBox currentPositionBox) {
		super(robot, currentPositionBox);
	}

	@Override
	public boolean takeControl() {
		return getCurrentPositionBox().isOutside();
	}

	@Override
	public void action() { // blocking operation
		AlmostDifferentialPilot pilot = getRobot().getDifferentialPilot();
		pilot.setSpeedRate(0.5);
		pilot.backward();
		getRobot().kick();
		pilot.stop();
		setCurrentPosition(getRobot().getPositionFinder().findPosition());
	}

	@Override
	public void suppress() {}

}
