package robot;

import position.Complex;
import position.Position;

public class SimplePilotCalculator {

	static final double eps = 0.0001;

	private int begLeftSpeed, begRightSpeed, begAcc;
	private int endLeftSpeed, endRightSpeed, endAcc;
	private int currLeftSpeed, currRightSpeed, currAcc, waitingAcc;
	private long begTime, endTime, currTime;
	private double wheelRadius, diameter;

	public SimplePilotCalculator(int acceleration, double wheelRadius,
			double diameter) {
		super();
		this.waitingAcc = acceleration;
		this.wheelRadius = wheelRadius;
		this.diameter = diameter;
		init();
	}

	private void init() {
		begLeftSpeed = endLeftSpeed = currLeftSpeed;
		begRightSpeed = endRightSpeed = currRightSpeed;
		begAcc = endAcc = currAcc = waitingAcc;
	}

	public void setAcceleration(int acceleration) {
		waitingAcc = acceleration;
	}

	public void setSpeed(int leftSpeed, int rightSpeed) {
		begTime = endTime;
		endTime = currTime;
		currTime = System.currentTimeMillis();
		begLeftSpeed = endLeftSpeed;
		begRightSpeed = endRightSpeed;
		endLeftSpeed = currLeftSpeed;
		endRightSpeed = currRightSpeed;
		currLeftSpeed = leftSpeed;
		currRightSpeed = rightSpeed;
		begAcc = endAcc;
		endAcc = currAcc;
		currAcc = waitingAcc;
	}

	public Position calculatePositionChange() {
		long timeInMs = endTime - begTime;
		double fstTime = (double) Math.max(
				Math.abs(begLeftSpeed - endLeftSpeed),
				Math.abs(begRightSpeed - endRightSpeed))
				/ begAcc;
		fstTime = Math.min(fstTime, timeInMs);
		double sndTime = timeInMs - fstTime;

		double cSpeed = Math.PI / 180 * wheelRadius / 1000; // bo 1 s = 1000 ms

		Position fstMovement, sndMovement;

		fstMovement = calculatePositionChangeByDistance(fstTime
				* (begLeftSpeed + endLeftSpeed) / 2 * cSpeed, fstTime
				* (begRightSpeed + endRightSpeed) / 2 * cSpeed);
		sndMovement = calculatePositionChangeByDistance(sndTime * endLeftSpeed
				* cSpeed, sndTime * endRightSpeed * cSpeed);

		return fstMovement.compose(sndMovement);
	}

	private Position calculatePositionChangeByDistance(double leftDistance,
			double rightDistance) {
		double alpha = (leftDistance - rightDistance) / diameter;
		double distance;
		if (Math.abs(alpha) < eps) {
			distance = (leftDistance + rightDistance) / 2;
		} else if (Math.abs(Math.cos(alpha / 2)) < eps) {
			distance = (leftDistance + rightDistance) / alpha;
		} else {
			distance = (leftDistance + rightDistance) / (2 * alpha)
					* Math.sin(alpha) / Math.cos(alpha / 2);
		}
		return calculatePositionChangeByAlpha(alpha, distance);
	}

	public Position calculatePositionChangeByAlpha(double alpha, double distance) {
		return new Position(Complex.fromPolar(distance, alpha / 2),
				Complex.fromPolar(1, alpha));
	}
}
