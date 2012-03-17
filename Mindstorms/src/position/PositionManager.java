package position;

import lejos.nxt.NXTRegulatedMotor;

public class PositionManager {

	private Position currentPosition;
	private NXTRegulatedMotor left, right;

	private int leftTachoCount, rightTachoCount;
	private double wheelRadius, diameter;

	public PositionManager(Position currentPosition, NXTRegulatedMotor left,
			NXTRegulatedMotor right, double wheelRadius, double diameter) {
		super();
		this.currentPosition = currentPosition;
		this.left = left;
		this.right = right;
		this.wheelRadius = wheelRadius;
		this.diameter = diameter;
		leftTachoCount = left.getTachoCount();
		rightTachoCount = right.getTachoCount();
	}

	public PositionManager(NXTRegulatedMotor left, NXTRegulatedMotor right,
			double wheelRadius, double diameter) {
		this(new Position(), left, right, wheelRadius, diameter);
	}

	public Position getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Position currentPosition) {
		this.currentPosition = currentPosition;
	}

	public void updatePosition() {
		int tmpLeft = left.getTachoCount();
		int tmpRight = right.getTachoCount();
		updatePositionUsingAngle(tmpLeft - leftTachoCount, tmpRight
				- rightTachoCount);
		leftTachoCount = tmpLeft;
		rightTachoCount = tmpRight;
	}

	private void updatePositionUsingAngle(int leftAngleDifference,
			int rightAngleDifference) {
		double m = wheelRadius * Math.PI / 180;
		updatePositionUsingDistance(leftAngleDifference * m,
				rightAngleDifference * m);
	}

	private void updatePositionUsingDistance(double leftDistance,
			double rightDistance) {
		double alpha = (leftDistance - rightDistance) / diameter;
		double distance = (leftDistance + rightDistance) / (2 * alpha)
				* Math.sin(alpha) / Math.cos(alpha / 2);
		currentPosition.update(alpha, distance);
	}

}
