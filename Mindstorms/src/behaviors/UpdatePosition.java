package behaviors;

import lejos.nxt.LCD;
import position.CurrentPositionBox;
import position.PositionFinder;
import robot.Robot;

public class UpdatePosition extends RobotPositionBehavior {

	public UpdatePosition(Robot robot, CurrentPositionBox currentPositionBox) {
		super(robot, currentPositionBox);
	}

	@Override
	public boolean takeControl() {
		getRobot().getPositionManager().updatePosition();
		
		LCD.clear(5);
		LCD.drawInt((int)getCurrentPosition().getCoordinates().getNorm(), 0, 5);
		LCD.drawInt((int)getCurrentPosition().getCoordinates().getIm(), 5, 5);
		
		LCD.clear(6);
		LCD.drawInt((int)(getCurrentPosition().getRotation().getAngle() * 180 / Math.PI), 0, 6);
		
		// TODO
		
		return false;
	}

	@Override
	public void action() {
		setCurrentPosition(new PositionFinder(getRobot()).findPosition());
	}

	@Override
	public void suppress() {}

}
