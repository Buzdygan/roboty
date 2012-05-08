package position;

import lejos.nxt.NXTRegulatedMotor;

public class PositionManager {

	static final double eps = 0.0001;
	
	private CurrentPositionBox positionBox;
	private NXTRegulatedMotor left, right;

	private int leftTC, rightTC;
	private double diameter;
	double rotationConstant;
	
	
	public PositionManager(NXTRegulatedMotor left,
			NXTRegulatedMotor right, double wheelRadius, double diameter) {
		super();
		this.left = left;
		this.right = right;
		this.diameter = diameter;
		leftTC = left.getTachoCount();
		rightTC = right.getTachoCount();
		rotationConstant = wheelRadius * Math.PI / 180;
	}
	
	public void reset() {
		leftTC = left.getTachoCount();
		rightTC = right.getTachoCount();
	}

	public void setPositionBox(CurrentPositionBox currentPositionBox) {
		this.positionBox = currentPositionBox;
	}

	public void updatePosition() {
		int leftTCNew = left.getTachoCount();
		int rightTCNew = right.getTachoCount();
		updatePositionUsingAngle(leftTCNew - leftTC, rightTCNew
				- rightTC);
		leftTC = leftTCNew;
		rightTC = rightTCNew;
	}

	private void updatePositionUsingAngle(int leftAngleDifference,
			int rightAngleDifference) {
		updatePositionUsingDistance(leftAngleDifference * rotationConstant,
				rightAngleDifference * rotationConstant);
	}

	private void updatePositionUsingDistance(double leftDistance,
			double rightDistance) {
		double alpha = (rightDistance - leftDistance) / diameter;
		double distance;
		if (Math.abs(alpha) < eps) {
			distance = (leftDistance + rightDistance) / 2;
		} else if (Math.abs(Math.cos(alpha / 2)) < eps) { 
			distance = (leftDistance + rightDistance) / alpha;
		} else {
			distance = (leftDistance + rightDistance) / (2 * alpha)	* Math.sin(alpha) / Math.cos(alpha / 2);
		}
		positionBox.getCurrentPosition().update(alpha, distance);
	}

}
