package robot;

import position.Position;
import lejos.nxt.NXTRegulatedMotor;

public class SimplePilot {

	private NXTRegulatedMotor left, right;
	private int basicSpeed, acceleration;
	
	private SimplePilotCalculator calculator;
	
	public SimplePilot(NXTRegulatedMotor left, NXTRegulatedMotor right, double wheelRadius, double diameter) {
		this(left, right, 180, 6000, wheelRadius, diameter);
	}
	
	public SimplePilot(NXTRegulatedMotor left, NXTRegulatedMotor right, int basicSpeed, int acceleration, double wheelRadius, double diameter) {
		this.left = left;
		this.right = right;
		this.basicSpeed = basicSpeed;
		this.acceleration = acceleration;
		calculator = new SimplePilotCalculator(acceleration, wheelRadius, diameter);
		calculator.setSpeed(0, 0);
		calculator.setSpeed(0, 0);
		calculator.setSpeed(0, 0);
	}
	
	public void setSpeed(int leftSpeed, int rightSpeed) {
		int fullLeftSpeed = basicSpeed * leftSpeed, fullRightSpeed = basicSpeed * rightSpeed;
		left.setSpeed(fullLeftSpeed);
		right.setSpeed(fullRightSpeed);
		calculator.setSpeed(fullLeftSpeed, fullRightSpeed);
	}

	public void move(int leftSpeed, int rightSpeed) {
		setSpeed(leftSpeed, rightSpeed);
		if (leftSpeed >= 0) { left.forward(); }
		else { left.backward(); }
		if (rightSpeed >= 0) { right.forward(); }
		else {right.backward(); }
	}

	public NXTRegulatedMotor getLeft() {
		return left;
	}

	public NXTRegulatedMotor getRight() {
		return right;
	}

	public int getBasicSpeed() {
		return basicSpeed;
	}

	public void setBasicSpeed(int basicSpeed) {
		this.basicSpeed = basicSpeed;
	}

	public int getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(int acceleration) {
		this.acceleration = acceleration;
	}

	public void stop() {
		setSpeed(0, 0);
		left.stop();
		right.stop();
	}
	
	public Position getPreviousMovement(long timeInMs) {
		if (timeInMs == 0) {
			return new Position();
		}
		return calculator.calculatePositionChange();
	}
}
