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
//		int values[] = getRobot().getSeeker().getSensorValues();
//		int dist = getRobot().getUltrasonic().getDistance();
//		LCD.clear();
//		for (int i=0; i<values.length; ++i) {
//			LCD.drawInt(values[i], 0, i);
//		}
//		LCD.drawInt(dist, 0, 5);
		
		getRobot().getPositionManager().updatePosition();
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
