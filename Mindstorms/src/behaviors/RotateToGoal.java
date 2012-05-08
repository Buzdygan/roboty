package behaviors;

import lejos.nxt.LCD;
import lejos.nxt.comm.RConsole;
import position.CurrentPositionBox;
import robot.AlmostDifferentialPilot;
import robot.Robot;

public class RotateToGoal extends RobotPositionBehavior {

	private final double steerRate = 50;
	private double slowSpeed, fastSpeed;
	
	private boolean suppressed = false;
	
	public RotateToGoal(Robot robot, CurrentPositionBox currentPositionBox) {
		super(robot, currentPositionBox);
		slowSpeed = 0.3;
		fastSpeed = 0.5;
	}

	@Override
	public boolean takeControl() {
		return !getCurrentPositionBox().almostInFrontOfOpponentsGoal();
	}

	@Override
	public void action() {
		RConsole.println("=== ROTATE TO GOAL ===");
		
		suppressed = false;

		AlmostDifferentialPilot pilot = getRobot().getDifferentialPilot();
		RConsole.println("=== IN ===");
		while ((!suppressed) && !getCurrentPositionBox().almostInFrontOfOpponentsGoal()) {
			getCurrentPositionBox().printOpponentsGoal();
			pilot.setCurrentSpeed(pilot.getMaxSpeed() * fastSpeed);
			int result = getCurrentPositionBox().getRotationToOpponentsGoal(steerRate);
			RConsole.println("Direction: " + Integer.toString(result));
			pilot.steer(steerRate * result);
			Thread.yield();
		}
		RConsole.println("=== MIDDLE ===");
		while ((!suppressed) && !getCurrentPositionBox().inFrontOfOpponentsGoal()) {
			getCurrentPositionBox().printOpponentsGoal();
			pilot.setCurrentSpeed(pilot.getMaxSpeed() * slowSpeed);
			int result = getCurrentPositionBox().getRotationToOpponentsGoal(steerRate);
			RConsole.println("Direction: " + Integer.toString(result));
			pilot.steer(steerRate * result);
		}
		RConsole.println("=== OUT ===");
		pilot.steer(0);
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
