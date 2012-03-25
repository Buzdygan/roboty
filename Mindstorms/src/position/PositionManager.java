package position;

import lejos.nxt.NXTRegulatedMotor;

public class PositionManager {

	static final double eps = 0.0001;
	
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

	public void updatePosition(Position movement) {
		currentPosition = currentPosition.compose(movement);
	}

	// deprecated
	public void updatePosition() {
		int tmpLeft = left.getTachoCount();
		int tmpRight = right.getTachoCount();
		updatePositionUsingAngle(tmpLeft - leftTachoCount, tmpRight
				- rightTachoCount);
		leftTachoCount = tmpLeft;
		rightTachoCount = tmpRight;
	}

	// deprecated
	private void updatePositionUsingAngle(int leftAngleDifference,
			int rightAngleDifference) {
		double m = wheelRadius * Math.PI / 180;
		updatePositionUsingDistance(leftAngleDifference * m,
				rightAngleDifference * m);
	}

	// deprecated
	private void updatePositionUsingDistance(double leftDistance,
			double rightDistance) {
		double alpha = (leftDistance - rightDistance) / diameter;
		double distance;
		if (Math.abs(alpha) < eps) {
			distance = (leftDistance + rightDistance) / 2;
		} else if (Math.abs(Math.cos(alpha / 2)) < eps) { 
			distance = (leftDistance + rightDistance) / alpha;
		} else {
			distance = (leftDistance + rightDistance) / (2 * alpha)	* Math.sin(alpha) / Math.cos(alpha / 2);
		}
		currentPosition.update(alpha, distance);
	}

}
