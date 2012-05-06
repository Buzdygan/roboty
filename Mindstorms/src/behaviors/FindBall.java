package behaviors;

import lejos.util.Delay;
import robot.AlmostDifferentialPilot;
import robot.Robot;

public class FindBall extends RobotBehavior {

	private final double TURN_RATE = 65;
	private final double SPEED_RATE = 0;
	private final int DELAY_LENGTH = 0; // in milliseconds
	private final int LOST_BALL_THRESHOLD = 3;
	
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
			
			if (direction == 0) {
				continue;
			}
			
			if (canCatchIt && getRobot().canHaveBall(distance, direction)) {
				gotBall = true;
				continue;
			}
			
			direction = direction - 5;
			
			pilot.setCurrentSpeed(pilot.getMaxSpeed() * (1 - SPEED_RATE * Math.abs(direction)));
			if ((distance < 25) && (Math.abs(direction) < 2)) {
				canCatchIt = true;
				pilot.setCurrentSpeed(Math.min(pilot.getCurrentSpeed(), pilot.getMaxSpeed() * 0.8));
			} else {
				canCatchIt = false;
			}
			
			pilot.steer(TURN_RATE * (-direction));
			
		} while ((!suppressed) && (!gotBall));

		if (!suppressed) {
			Delay.msDelay(DELAY_LENGTH);
		}

		getRobot().getDifferentialPilot().stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
