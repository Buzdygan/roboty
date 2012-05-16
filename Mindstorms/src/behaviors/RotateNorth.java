package behaviors;

import lejos.nxt.LCD;
import lejos.nxt.comm.RConsole;
import robot.Robot;

public class RotateNorth extends RobotBehavior {

	static final int SteerRate = 50;
	
	boolean suppressed = false;
	
	public RotateNorth(Robot robot) {
		super(robot);
	}

	private float getRel() {
		float value = getRobot().getCompass().getDegreesCartesian();
		if (value <= 180) {
			return value;
		}
		return value - 360;
	}
	
	@Override
	public boolean takeControl() {
		return Math.abs(getRel()) > 20;
	}

	@Override
	public void action() {
		LCD.clear(0);
		LCD.drawString("NORTH", 0, 0);
		RConsole.println("=== NORTH ===");
		
		suppressed = false;
		while ((!suppressed) && (Math.abs(getRel()) > 10)) {
			if (getRel() > 0) {
				getRobot().getDifferentialPilot().steer(-SteerRate);
			} else {
				getRobot().getDifferentialPilot().steer(SteerRate);
			}
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
