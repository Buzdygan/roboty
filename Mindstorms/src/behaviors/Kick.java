package behaviors;

import java.io.File;

import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.nxt.comm.RConsole;
import position.CurrentPositionBox;
import robot.Robot;

public class Kick extends RobotPositionBehavior {

	File cannonFile;
	
	boolean suppressed = false;

	public Kick(Robot robot, CurrentPositionBox currentPositionBox) {
		super(robot, currentPositionBox);
		cannonFile = new File("cannon8.wav");
	}

	@Override
	public boolean takeControl() {
		return getRobot().canHaveBall(getRobot().getUltrasonic().getDistance(),
				getRobot().getSeeker().getDirection());
	}

	@Override
	public void action() {
		LCD.clear(0);
		LCD.drawString("KICK", 0, 0);
		RConsole.println("=== KICK ===");
		
		Sound.playSample(cannonFile, 100);
		getRobot().kick();
		getCurrentPositionBox().setGotBall(false);
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
