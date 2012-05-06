package position;

import robot.Robot;


public class CurrentPositionBox {
	
	private final double pitchWidth, pitchHeight; // in millimeters
	private Complex opponentsGoalCoord, ourHalfCoord;
	private final double diameter;
	private Position currentPosition;
	
	public CurrentPositionBox(double pitchWidth, double pitchHeight, double diameter) {
		super();
		this.pitchWidth = pitchWidth;
		this.pitchHeight = pitchHeight;
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
		this(1200, 1800, Robot.getDiameter());
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
	
	public double getRotationTo(double arc, Complex destination) {
		arc /= 100;
		double radius = diameter * (1 + arc ) / (1 - arc) / 2;
		double left = minDistance(getCurrentPosition().getCoordinates().add(new Complex(0, radius).mul(getCurrentPosition().getRotation())), radius);
		double right = minDistance(getCurrentPosition().getCoordinates().add(new Complex(0, radius).mul(getCurrentPosition().getRotation())), radius);
		return 0;
	}

	private double minDistance(Complex center, double radius) {
		return Math.min(center.getRe() - radius, 
				Math.min(pitchHeight - center.getRe() - radius,
				Math.min(center.getIm() - radius,
				pitchWidth - center.getIm() - radius)));
	}
	
}
