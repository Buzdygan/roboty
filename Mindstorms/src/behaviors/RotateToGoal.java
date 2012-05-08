package behaviors;

import lejos.nxt.LCD;
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
		return !getCurrentPositionBox().inFrontOfOpponentsGoal();
	}

	@Override
	public void action() {
		LCD.clear(0);
		LCD.drawString("rotGoal", 0, 0);
		
		suppressed = false;

		AlmostDifferentialPilot pilot = getRobot().getDifferentialPilot();
		LCD.drawString("fast", 0, 2);
		while ((!suppressed) && getCurrentPositionBox().inFrontOfOpponentsGoal()) {
			pilot.setCurrentSpeed(pilot.getMaxSpeed() * fastSpeed);
			pilot.steer(steerRate * getCurrentPositionBox().getRotationToOpponentsGoal(steerRate));
			Thread.yield();
		}
		LCD.drawString("slow", 0, 2);
		while ((!suppressed) && getCurrentPositionBox().almostInFrontOfOpponentsGoal()) {
			pilot.setCurrentSpeed(pilot.getMaxSpeed() * slowSpeed);
			pilot.steer(steerRate * getCurrentPositionBox().getRotationToOpponentsGoal(steerRate));
		}
		pilot.steer(0);
		LCD.clear();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
