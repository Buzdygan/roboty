package behaviours;

import lejos.nxt.LCD;
import robot.Robot;

public class FindBall extends RobotBehavior {

	private final int MAX_DISTANCE = 255;
	private final double TURN_RATE = 50;
	private final int DELAY_LENGTH = 100; // in milliseconds
	private final int DELAY_DISTANCE = 1; // in wheel diameters 
	
	private boolean suppressed = false;
	
	public FindBall(Robot robot) {
		super(robot);
	}

	@Override
	public boolean takeControl() {
		return getRobot().getUltrasonic().getDistance() != MAX_DISTANCE;
	}

	@Override
	public void action() {
		suppressed = false;
		
		LCD.drawString("Took control: findBall", 0, 1);
		
		while ((!suppressed) && (getRobot().getUltrasonic().getDistance() != MAX_DISTANCE)) {
			int direction = getRobot().getSeeker().getDirection();
			if (direction != 0) {
				LCD.clear(0);
				LCD.drawInt(direction, 0, 0);
				getRobot().getDifferentialPilot().steer(TURN_RATE * (5 - direction));
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		LCD.drawString("Delay!", 0, 2);
		
		if (!suppressed) {
			getRobot().getDifferentialPilot().setTravelSpeed(DELAY_DISTANCE / DELAY_LENGTH * 1000);
			long finishTime = System.currentTimeMillis() + DELAY_LENGTH;
			while ((!suppressed) && (System.currentTimeMillis() < finishTime)) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		getRobot().getDifferentialPilot().stop();
		getRobot().getDifferentialPilot().setTravelSpeed(.8f * getRobot().getDifferentialPilot().getMaxTravelSpeed());
		LCD.clear();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
