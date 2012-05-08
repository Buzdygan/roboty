package behaviors;

import robot.AlmostDifferentialPilot;
import robot.Robot;

public class Rotate extends RobotBehavior {

	private boolean once = true;

	public Rotate(Robot robot) {
		super(robot);
	}

	@Override
	public boolean takeControl() {
		if (once) {
			once = false;
			return true;
		}
		return false;
	}

	@Override
	public void action() {
		AlmostDifferentialPilot pilot = getRobot().getDifferentialPilot();
		
		pilot.setCurrentSpeed(pilot.getMaxSpeed() * 0.6);
		for (int i=0; i<8; ++i) {
			pilot.rotate(90, false);
		}
		
	}

	@Override
	public void suppress() {}

}
