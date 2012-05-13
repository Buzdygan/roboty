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
		LCD.clear(0);
		LCD.drawString("ROTATE GOAL", 0, 0);
		RConsole.println("=== ROTATE TO GOAL ===");
		
		suppressed = false;

		AlmostDifferentialPilot pilot = getRobot().getDifferentialPilot();
		RConsole.println("=== IN ===");
		while ((!suppressed) && !getCurrentPositionBox().almostInFrontOfOpponentsGoal()) {
			getCurrentPositionBox().printOpponentsGoal();
			pilot.setCurrentSpeed(pilot.getMaxSpeed() * fastSpeed);
			double result = getCurrentPositionBox().getRotationToOpponentsGoal(steerRate);
			RConsole.println("Direction: " + Double.toString(result));
			pilot.steer(result);
			Thread.yield();
		}
		RConsole.println("=== MIDDLE ===");
		while ((!suppressed) && !getCurrentPositionBox().inFrontOfOpponentsGoal()) {
			getCurrentPositionBox().printOpponentsGoal();
			pilot.setCurrentSpeed(pilot.getMaxSpeed() * slowSpeed);
			double result = getCurrentPositionBox().getRotationToOpponentsGoal(steerRate);
			RConsole.println("Direction: " + Double.toString(result));
			pilot.steer(result);
		}
		RConsole.println("=== OUT ===");
		pilot.steer(0);
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
