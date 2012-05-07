package behaviors;

import javax.microedition.lcdui.Alert;

import lejos.util.Delay;

import robot.AlmostDifferentialPilot;
import robot.Robot;

public class Kick extends RobotBehavior {
	
	boolean suppressed = false;
	
	public Kick(Robot robot) {
		super(robot);
	}

	@Override
	public boolean takeControl() {
		return getRobot().canHaveBall(
				getRobot().getUltrasonic().getDistance(), 
				getRobot().getSeeker().getDirection());
	}

	@Override
	public void action() {
		suppressed = false;
		//getRobot().kick();
		AlmostDifferentialPilot pilot = getRobot().getDifferentialPilot();
		pilot.setCurrentSpeed(pilot.getMaxSpeed() * 0.3);
		pilot.steer(50);
		
		while (!suppressed) {
			Thread.yield();
		}
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
