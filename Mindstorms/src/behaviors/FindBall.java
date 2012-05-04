package behaviors;

import lejos.nxt.LCD;
import lejos.util.Delay;
import robot.Robot;

public class FindBall extends RobotBehavior {

	private final int MAX_DISTANCE = 255;
	private final double TURN_RATE = 50;
	private final int DELAY_LENGTH = 100; // in milliseconds
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
			if (getRobot().getUltrasonic().getDistance() != MAX_DISTANCE) {
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
		suppressed = false;
		
		boolean canCatchIt = false;
		
		do {
			int distance = getRobot().getUltrasonic().getDistance();
			int direction = getRobot().getSeeker().getDirection();
			
			if (direction == 0) {
				continue;
			}
			
			if ((distance == MAX_DISTANCE) && (canCatchIt) && (Math.abs(direction - 5) < 2)) {
				gotBall = true;
				continue;
			}
			
			if ((distance < 20) && (Math.abs(direction - 5) < 2)) {
				canCatchIt = true;
			} else {
				canCatchIt = false;
			}
			
			getRobot().getDifferentialPilot().steer(TURN_RATE * (5 - direction));
			Delay.msDelay(10);
			
		} while ((!suppressed) && (!gotBall));

		if (!suppressed) {
			Delay.msDelay(DELAY_LENGTH);
		}
		
		getRobot().getDifferentialPilot().stop();
		LCD.clear();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
