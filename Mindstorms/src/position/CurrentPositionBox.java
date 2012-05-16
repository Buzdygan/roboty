package position;

import robot.Robot;

public class CurrentPositionBox {

	// in millimeters
	public static final double pitchWidth = 1200, pitchHeight = 1800;
	public static final double outerBoundaryMargin = -30;
	public static final double boundaryMargin = 50;
	
	// in radians
	public static final double rotationTreshold = Math.PI * 2 / 3; 
	
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
		this.opponentsGoalCoord = new Complex(pitchHeight * 1.2, pitchWidth / 2);
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

	private final double angleTreshold = Math.PI / 32;

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

	// 100 - zakręca z jednym kołem w miejscu, 0 - jedzie na wprost
	public double getRotationTo(double arc, Complex destination) {
		
		double leftArc, rightArc, radius;
		double A = 1, B = 100 - arc, C, CC;
		
		while(B - A > 1) {
			C = (A+B) / 2;
			CC = C / 100;
			radius = diameter * (1 + CC) / (1 - CC) / 2;
			double dist = boundaryDistance(
					getCurrentPosition().getCoordinates().add(
							new Complex(0, radius).mul(getCurrentPosition()
									.getRotation()))) - radius;
			if (dist > boundaryMargin) {
				A = C;
			} else {
				B = C;
			}
		}
		leftArc = 100 - A;
		
		A = 1; B = 100 - arc;
		while(B - A > 1) {
			C = (A+B) / 2;
			CC = C / 100;
			radius = diameter * (1 + CC) / (1 - CC) / 2;
			double dist = boundaryDistance(
					getCurrentPosition().getCoordinates().add(
							new Complex(0, -radius).mul(getCurrentPosition()
									.getRotation()))) - radius;
			if (dist > boundaryMargin) {
				A = C;
			} else {
				B = C;
			}
		}
		rightArc = 100 - A;
		
		//RConsole.println(Double.toString(leftArc));
		//RConsole.println(Double.toString(rightArc));
		
		Complex relative = destination.sub(
				getCurrentPosition().getCoordinates()).div(
				getCurrentPosition().getRotation());
		
		if (Math.abs(relative.getAngle()) < rotationTreshold) {
			if (Math.signum(relative.getAngle()) > 0) {
				return (int) leftArc;
			} else {
				return - (int) rightArc;
			}
		}
		if (leftArc < rightArc) {
			return (int) leftArc;
		} else {
			return - (int) rightArc;
		}
	}

	private double boundaryDistance(Complex center) {
		double res = Math.min(
				center.getRe(),
				Math.min(
						pitchHeight - center.getRe(),
						Math.min(center.getIm(),
								pitchWidth - center.getIm())));
		return res;
	}

	public boolean inFrontOfOpponentsGoal() {
		return inFront(getOpponentsGoalCoord());
	}

	public boolean almostInFrontOfOpponentsGoal() {
		return almostInFront(getOpponentsGoalCoord());
	}

	public double getRotationToOpponentsGoal(double arc) {
		return getRotationTo(arc, opponentsGoalCoord);
	}

	public double getRotationToOurHalf(double arc) {
		return getRotationTo(arc, ourHalfCoord);
	}

	public boolean isOutside() {
		return boundaryDistance(getCurrentPosition().getCoordinates()) - outerBoundaryMargin < 0;
	}
}
