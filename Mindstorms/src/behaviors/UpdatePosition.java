package behaviors;

import lejos.nxt.LCD;
import position.CurrentPositionBox;
import robot.Robot;

public class UpdatePosition extends RobotPositionBehavior {

	public UpdatePosition(Robot robot, CurrentPositionBox currentPositionBox) {
		super(robot, currentPositionBox);
	}

	@Override
	public boolean takeControl() {
		getRobot().getPositionManager().updatePosition();
		LCD.clear();
		LCD.drawInt((int)Runtime.getRuntime().freeMemory(), 0, 0);
		LCD.drawInt((int) Math.round(getCurrentPosition().getCoordinates().getRe()), 0, 1);
		LCD.drawInt((int) Math.round(getCurrentPosition().getCoordinates().getIm()), 0, 2);
		LCD.drawInt((int) Math.round(getCurrentPosition().getRotation().getRe()), 0, 3);
		LCD.drawInt((int) Math.round(getCurrentPosition().getRotation().getIm()), 0, 4);
		return false;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}

}
