package behaviors;

import lejos.nxt.LCD;
import lejos.util.Delay;
import robot.AlmostDifferentialPilot;
import robot.Robot;

public class FindBall extends RobotBehavior {

	private final double TURN_RATE = 75;
	private final double SPEED_RATE = 0.15;
	private final int DELAY_LENGTH = 0; // in milliseconds
	private final int LOST_BALL_THRESHOLD = 10;
	
	private boolean suppressed = false;
	boolean gotBall = false;
	int lostBallCounter = 0;
	
	public FindBall(Robot robot) {
		super(robot);
	}

	@Override
	public boolean takeControl() {
		if (gotBall) {
			int distance = getRobot().getUltrasonic().getDistance();
			int direction = getRobot().getSeeker().getDirection();
			
			if (!getRobot().canHaveBall(distance, direction)) {
				++lostBallCounter;
			} else {
				lostBallCounter = 0;
			}
			if (lostBallCounter >= LOST_BALL_THRESHOLD) {
				gotBall = false;
				lostBallCounter = 0;
			}
		}
		return !gotBall;
	}

	@Override
	public void action() {
		AlmostDifferentialPilot pilot = getRobot().getDifferentialPilot();
		suppressed = false;
		
		boolean canCatchIt = false;
		
		do {
			int distance = getRobot().getUltrasonic().getDistance();
			int direction = getRobot().getSeeker().getDirection();

			LCD.clear();
			LCD.drawInt(direction, 0, 0);
			LCD.drawInt(distance, 0, 1);
			if (canCatchIt) {
				LCD.drawInt(1, 0, 4);
			} else {
				LCD.drawInt(0, 0, 4);
			}
			
			if (direction == 0) {
				continue;
			}
			
			if (canCatchIt && getRobot().canHaveBall(distance, direction)) {
				gotBall = true;
				continue;
			}
			
			direction = direction - 5;
			
			pilot.setCurrentSpeed(pilot.getMaxSpeed());
			canCatchIt = false;
			if (Math.abs(direction) < 3) {
				if (distance < 25) {
					pilot.setCurrentSpeed(Math.min(pilot.getCurrentSpeed(), pilot.getMaxSpeed() * 0.5));
					canCatchIt = true;
				}
			} else {
				pilot.setCurrentSpeed(Math.min(pilot.getCurrentSpeed(), pilot.getMaxSpeed() * 0.7));
			}
			
			pilot.steer(TURN_RATE * (-direction));
			
		} while ((!suppressed) && (!gotBall));

		if (!suppressed) {
			Delay.msDelay(DELAY_LENGTH);
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
