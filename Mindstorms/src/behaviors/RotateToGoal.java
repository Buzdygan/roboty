package behaviors;

import position.CurrentPositionBox;
import robot.AlmostDifferentialPilot;
import robot.Robot;

public class RotateToGoal extends RobotPositionBehavior {

	private final double steerRate = 30;
	
	private boolean suppressed = false;
	
	public RotateToGoal(Robot robot, CurrentPositionBox currentPositionBox) {
		super(robot, currentPositionBox);
	}

	@Override
	public boolean takeControl() {
//		return !getCurrentPositionBox().inFrontOfOpponentsGoal();
		return false;
	}

	@Override
	public void action() {
		suppressed = false;
		
		AlmostDifferentialPilot pilot = getRobot().getDifferentialPilot();
		while ((!suppressed) && getCurrentPositionBox().almostInFrontOfOpponentsGoal()) {
			pilot.setCurrentSpeed(pilot.getMaxSpeed());
			pilot.steer(steerRate * getCurrentPositionBox().getRotationToOpponentsGoal(steerRate));
		}
		while ((!suppressed) && getCurrentPositionBox().inFrontOfOpponentsGoal()) {
			pilot.setCurrentSpeed(pilot.getMaxSpeed() * 0.8);
			pilot.steer(steerRate * getCurrentPositionBox().getRotationToOpponentsGoal(steerRate));
		}
		pilot.stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
