package behaviours;

import position.CurrentPositionBox;
import position.Position;
import robot.Robot;

public abstract class RobotPositionBehavior extends RobotBehavior {

	CurrentPositionBox currentPositionBox;
	
	public RobotPositionBehavior(Robot robot, CurrentPositionBox currentPositionBox) {
		super(robot);
		this.currentPositionBox = currentPositionBox;
	}
	
	public Position getCurrentPosition() {
		return currentPositionBox.getCurrentPosition();
	}

}
