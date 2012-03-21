package robot;

import position.Position;
import lejos.nxt.NXTRegulatedMotor;

public class SimplePilot {

	private NXTRegulatedMotor left, right;
	private int basicSpeed, acceleration;
	
	private int previousLeftSpeed, previousRightSpeed;
	private SimplePilotCalculator calculator;
	
	public SimplePilot(NXTRegulatedMotor left, NXTRegulatedMotor right, double wheelRadius, double diameter) {
		this(left, right, 180, 500, wheelRadius, diameter);
	}
	
	public SimplePilot(NXTRegulatedMotor left, NXTRegulatedMotor right, int basicSpeed, int acceleration, double wheelRadius, double diameter) {
		this.left = left;
		this.right = right;
		this.basicSpeed = basicSpeed;
		this.acceleration = acceleration;
		calculator = new SimplePilotCalculator(acceleration, acceleration, basicSpeed, wheelRadius, diameter);
		previousLeftSpeed = 0;
		previousRightSpeed = 0;
		setSpeed(0, 0);
		setSpeed(0, 0);
		update();
	}
	
	private void update() {
		left.setSpeed(basicSpeed);
		right.setSpeed(basicSpeed);
		left.setAcceleration(acceleration);
		right.setAcceleration(acceleration);
		calculator.setBasicSpeed(basicSpeed);
		calculator.setLeftAcceleration(acceleration);
		calculator.setRightAcceleration(acceleration);
	}
	
	public void setSpeed(int leftSpeed, int rightSpeed) {
		left.setSpeed(basicSpeed * leftSpeed);
		right.setSpeed(basicSpeed * rightSpeed);
		calculator.setSpeed(previousLeftSpeed, previousRightSpeed);
		updatePreviousSpeed(leftSpeed, rightSpeed);
	}
	
	private void updatePreviousSpeed(int leftSpeed, int rightSpeed) {
		previousLeftSpeed = leftSpeed;
		previousRightSpeed = rightSpeed;
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
		update();
	}

	public int getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(int acceleration) {
		this.acceleration = acceleration;
		update();
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
		return calculator.calculatePositionChange(timeInMs);
	}
}
