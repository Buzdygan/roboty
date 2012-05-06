package robot;

import lejos.nxt.LCD;
import lejos.nxt.NXTRegulatedMotor;

public class AlmostDifferentialPilot {

	private final double wheelRadius, diameter;
	NXTRegulatedMotor left, right;

	private final double maxSpeed;
	private double currentSpeed;
	
	public AlmostDifferentialPilot(double wheelRadius, double diameter,
			NXTRegulatedMotor left, NXTRegulatedMotor right) {
		super();
		this.wheelRadius = wheelRadius;
		this.diameter = diameter;
		this.left = left;
		this.right = right;
		this.maxSpeed = Math.min(left.getMaxSpeed(), right.getMaxSpeed()) * 0.8;
		setCurrentSpeed(this.maxSpeed);
	}

	public double getCurrentSpeed() {
		return currentSpeed;
	}

	public void setCurrentSpeed(double currentSpeed) {
		this.currentSpeed = currentSpeed;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public void steer(double turnRate) {
		NXTRegulatedMotor inside, outside;

		if (turnRate > 300) {
			turnRate = 300;
		}
		if (turnRate < -300) {
			turnRate = -300; 
		}
		
		if (turnRate > 0) {
			inside = left;
			outside = right;
		} else {
			inside = right;
			outside = left;
			turnRate = -turnRate;
		}
		double insideSteerRatio = 1 - (Math.min(200, turnRate) / 100.0);
		double outsideSteerRatio = 1 + ((Math.min(0, 200 - turnRate)) / 100);

		LCD.clear(5);
		LCD.drawInt((int)Math.round(turnRate), 0, 5);
		LCD.clear(6);
		LCD.drawInt((int)Math.round(insideSteerRatio * 1000), 0, 6);
		LCD.clear(7);
		LCD.drawInt((int)Math.round(outsideSteerRatio * 1000), 0, 7);
		
		outside.setSpeed(Math.round(currentSpeed * outsideSteerRatio));
		inside.setSpeed(Math.round(currentSpeed * insideSteerRatio));
		outside.forward();
		if (insideSteerRatio > 0) {
			inside.forward();
		} else {
			inside.backward();
		}
	}
	
	public void stop() {
		left.stop(true);
		right.stop(true);
	}

}
