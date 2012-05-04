package behaviours;

import lejos.robotics.subsumption.Behavior;
import robot.Robot;

public abstract class RobotBehavior implements Behavior {

	private Robot robot;

	public RobotBehavior(Robot robot) {
		super();
		this.robot = robot;
	}

	public Robot getRobot() {
		return robot;
	}

	public void setRobot(Robot robot) {
		this.robot = robot;
	}
	
}
