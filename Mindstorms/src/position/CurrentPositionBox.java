package position;

import robot.Robot;


public class CurrentPositionBox {
	
	public static final double pitchWidth = 1200, pitchHeight = 1800; // in millimeters
	private Complex opponentsGoalCoord, ourHalfCoord;
	private final double diameter;
	private Position currentPosition;
	boolean gotBall = false;
	
	public boolean gotBall() {
		return gotBall;
	}

	public void setGotBall(boolean gotBall) {
		this.gotBall = gotBall;
	}

	public CurrentPositionBox(double diameter) {
		super();
		this.opponentsGoalCoord = new Complex(pitchHeight, pitchWidth / 2);
		this.ourHalfCoord = new Complex(pitchHeight / 3, pitchWidth / 2);
		this.diameter = diameter;
	}
	
	public Complex getOpponentsGoalCoord() {
		return opponentsGoalCoord;
	}

	public Complex getOurHalfCoord() {
		return ourHalfCoord;
	}

	public CurrentPositionBox() {
		this(Robot.getDiameter());
	}

	public Position getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Position currentPosition) {
		this.currentPosition = currentPosition;
	}
	
	private final double angleTreshold = Math.PI / 16;
	public boolean inFront(Complex destination) {
		Complex diff = destination.sub(getCurrentPosition().getCoordinates());
		diff = diff.div(getCurrentPosition().getRotation());
		return Math.abs(diff.getAngle()) < angleTreshold;
	}
	
	public boolean almostInFront(Complex destination) {
		Complex diff = destination.sub(getCurrentPosition().getCoordinates());
		diff = diff.div(getCurrentPosition().getRotation());
		return Math.abs(diff.getAngle()) < 3 * angleTreshold;
	}
	
	public int getRotationTo(double arc, Complex destination) {
		arc /= 100;
		double radius = diameter * (1 + arc ) / (1 - arc) / 2;
		double left = minDistance(getCurrentPosition().getCoordinates().add(new Complex(0, radius).mul(getCurrentPosition().getRotation())), radius);
		double right = minDistance(getCurrentPosition().getCoordinates().add(new Complex(0, -radius).mul(getCurrentPosition().getRotation())), radius);
		Complex relative = destination.sub(getCurrentPosition().getCoordinates()).div(getCurrentPosition().getRotation());
		if ((left > 0) && (right > 0)) {
			return (int)Math.round(Math.signum(relative.getAngle()));
		}
		if (left > 0) {
			return 1;
		}
		if (right > 0) {
			return -1;
		}
		return (int)Math.round(Math.signum(left - right));
	}
	
	private double minDistance(Complex center, double radius) {
		return Math.min(center.getRe() - radius, 
				Math.min(pitchHeight - center.getRe() - radius,
				Math.min(center.getIm() - radius,
				pitchWidth - center.getIm() - radius)));
	}

	public boolean inFrontOfOpponentsGoal() {
		return inFront(getOpponentsGoalCoord());
	}

	public boolean almostInFrontOfOpponentsGoal() {
		return almostInFront(getOpponentsGoalCoord());
	}
	
	public int getRotationToOpponentsGoal(double arc) {
		return getRotationTo(arc, opponentsGoalCoord);
	}
	
	public int getRotationToOurHalf(double arc) {
		return getRotationTo(arc, ourHalfCoord);
	}
}
