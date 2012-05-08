package behaviors;

import lejos.util.Delay;
import robot.AlmostDifferentialPilot;
import robot.Robot;

public class Forward extends RobotBehavior {

	private boolean once = true;
	
	public Forward(Robot robot) {
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
		pilot.steer(0);
		
		Delay.msDelay(1000 * 5);
		
		pilot.stop();
	}

	@Override
	public void suppress() {}

}
