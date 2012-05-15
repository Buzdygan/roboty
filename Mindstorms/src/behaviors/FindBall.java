package behaviors;

import java.io.File;

import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.nxt.comm.RConsole;
import position.CurrentPositionBox;
import robot.AlmostDifferentialPilot;
import robot.Robot;

public class FindBall extends RobotPositionBehavior {

	File toyHornFile;
	
	public FindBall(Robot robot, CurrentPositionBox currentPositionBox) {
		super(robot, currentPositionBox);
		toyHornFile = new File("toyHorn8.wav");
	}

	private final double TURN_RATE = 75;
	private final double ROTATE_SPEED = 0.4;
	private final double NEAR_SPEED = 0.5;
	private final int LOST_BALL_THRESHOLD = 10;
	
	private boolean suppressed = false;
	
	int lostBallCounter = 0;
	
	@Override
	public boolean takeControl() {
		
		if (currentPositionBox.gotBall()) {
			int distance = getRobot().getUltrasonic().getDistance();
			int direction = getRobot().getSeeker().getDirection();
			
			if (!getRobot().canHaveBall(distance, direction)) {
				++lostBallCounter;
			} else {
				lostBallCounter = 0;
			}
			if (lostBallCounter >= LOST_BALL_THRESHOLD) {
				currentPositionBox.setGotBall(false);
				lostBallCounter = 0;
			}
		}
		return !currentPositionBox.gotBall();
	}

	@Override
	public void action() {
		LCD.clear(0);
		LCD.drawString("FIND BALL", 0, 0);
		RConsole.println("=== FIND BALL ===");
		
		Sound.playSample(toyHornFile, 100);
		
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
				currentPositionBox.setGotBall(true);
				continue;
			}
			
			direction = direction - 5;
			
			pilot.setCurrentSpeed(pilot.getMaxSpeed());
			canCatchIt = false;
			if (Math.abs(direction) < 3) {
				if (distance < 25) {
					pilot.setCurrentSpeed(Math.min(pilot.getCurrentSpeed(), pilot.getMaxSpeed() * NEAR_SPEED));
					canCatchIt = true;
				}
			} else {
				pilot.setCurrentSpeed(Math.min(pilot.getCurrentSpeed(), pilot.getMaxSpeed() * ROTATE_SPEED));
			}
			
			pilot.steer(TURN_RATE * (-direction));
			
		} while ((!suppressed) && (!currentPositionBox.gotBall()));
		
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
