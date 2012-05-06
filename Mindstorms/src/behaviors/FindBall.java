package behaviors;

import lejos.nxt.LCD;
import lejos.util.Delay;
import robot.Robot;

public class FindBall extends RobotBehavior {

	private final int MAX_DISTANCE = 255;
	private final double TURN_RATE = 50;
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
			
			if ((distance != MAX_DISTANCE) || ((Math.abs(direction - 5) > 1) && (direction != 0))) {
				++lostBallCounter;
			} else {
				lostBallCounter = 0;
			}
			if (lostBallCounter >= LOST_BALL_THRESHOLD) {
				gotBall = false;
				lostBallCounter = 0;
			}
		}
		if (!gotBall) {
			LCD.drawString("A", 0, 7);
		}
		return !gotBall;
	}

	@Override
	public void action() {
		suppressed = false;
		
		LCD.drawString("a", 0, 6);
		
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

			LCD.drawString("b", 0, 6);
			getRobot().getDifferentialPilot().steer(TURN_RATE * (5 - direction));
			LCD.drawString("c", 0, 6);
			
		} while ((!suppressed) && (!gotBall));

		LCD.drawString("d", 0, 6);
		if (!suppressed) {
			LCD.drawString("e", 0, 6);
			Delay.msDelay(DELAY_LENGTH);
		}

		LCD.drawString("f", 0, 6);
		getRobot().getLeft().stop(true);
		getRobot().getRight().stop(true);
		LCD.drawString("g", 0, 6);
	}

	@Override
	public void suppress() {
		LCD.drawString("B", 0, 7);
		suppressed = true;
	}

}
