package robot;

import lejos.nxt.LCD;
import position.Complex;
import position.Position;

public class SimplePilotCalculator {

	static final double eps = 0.0001;
	
	private int oldLeftSpeed, oldRightSpeed;
	private int newLeftSpeed, newRightSpeed;
	private int leftAcceleration, rightAcceleration;
	private int basicSpeed;
	private double wheelRadius, diameter;

	public SimplePilotCalculator(int leftAcceleration, int rightAcceleration,
			int basicSpeed, double wheelRadius, double diameter) {
		super();
		this.leftAcceleration = leftAcceleration;
		this.rightAcceleration = rightAcceleration;
		this.basicSpeed = basicSpeed;
		this.wheelRadius = wheelRadius;
		this.diameter = diameter;
	}

	public void setLeftAcceleration(int leftAcceleration) {
		this.leftAcceleration = leftAcceleration;
	}

	public void setRightAcceleration(int rightAcceleration) {
		this.rightAcceleration = rightAcceleration;
	}

	public void setBasicSpeed(int basicSpeed) {
		this.basicSpeed = basicSpeed;
	}
	
	public void setSpeed(int leftSpeed, int rightSpeed) {
		oldLeftSpeed = newLeftSpeed;
		oldRightSpeed = newRightSpeed;
		newLeftSpeed = leftSpeed;
		newRightSpeed = rightSpeed;
	}

	public Position calculatePositionChange(double timeInMs) {
		double middleTime = Math.max(
				Math.abs((double)(oldLeftSpeed - newLeftSpeed) / leftAcceleration), 
				Math.abs((double)(oldRightSpeed - newRightSpeed) / rightAcceleration)
			);
		
		double cSpeed = Math.PI / 180 * wheelRadius * basicSpeed / 1000; // bo 1 s = 1000 ms
		double cAcc = Math.PI / 180 * wheelRadius / (1000 * 1000); // bo 1 s^2 = 1000 ms^2
		
		Position fstMovement, sndMovement;
		
		double fstLSpeed = oldLeftSpeed * cSpeed, fstRSpeed = oldRightSpeed * cSpeed;
		double fstLAcc = (newLeftSpeed - oldLeftSpeed) * leftAcceleration * cAcc;
		double fstRAcc = (newRightSpeed - oldRightSpeed) * rightAcceleration * cAcc;
		
		if (middleTime > timeInMs - eps) {
			fstMovement = calculatePositionChange(fstLSpeed, fstRSpeed, fstLAcc, fstRAcc, timeInMs);
			sndMovement = new Position();
		} else {
			fstMovement = calculatePositionChange(fstLSpeed, fstRSpeed, fstLAcc, fstRAcc, middleTime);
			
			double sndLSpeed = newLeftSpeed * cSpeed, sndRSpeed = newRightSpeed * cSpeed;
			sndMovement = calculatePositionChange(sndLSpeed, sndRSpeed, 0, 0, timeInMs - middleTime);
		}
		/*
		LCD.drawString(fstMovement.getCoordinates().toString(), 0, 0);
		LCD.drawString(fstMovement.getRotation().toString(), 0, 1);
		LCD.drawString(sndMovement.getCoordinates().toString(), 0, 3);
		LCD.drawString(sndMovement.getRotation().toString(), 0, 4);
		*/
		return fstMovement.compose(sndMovement);
	}
	
	private Position calculatePositionChange(double v1, double v2, double a1, double a2, double t) {
		Position result = new Position();
		result.setRotation(Complex.fromPolar(1, calculateAngle(v1, v2, a1, a2, t)));
		result.setCoordinates(calculateCoordinatesChange(v1, v2, a1, a2, t));
		return result;
	}
	
	private Complex calculateCoordinatesChange(double v1, double v2, double a1, double a2, double t) {
		return new Complex(calculateFirstCoordinate(v1, v2, a1, a2, t), calculateSecondCoordinate(v1, v2, a1, a2, t));
	}

	// Całkowanie
	private double calculateFirstCoordinate(double v1, double v2, double a1, double a2, double t) {
		double sum = 0;
		for (int s = 0; s < t; ++s) {
			sum += calculateSpeed(v1, v2, a1, a2, t) * Math.cos(calculateAngle(v1, v2, a1, a2, t));
		}
		return sum;
	}

	// Całkowanie
	private double calculateSecondCoordinate(double v1, double v2, double a1,
			double a2, double t) {
		double sum = 0;
		for (int s = 0; s < t; ++s) {
			sum += calculateSpeed(v1, v2, a1, a2, t) * Math.sin(calculateAngle(v1, v2, a1, a2, t));
		}
		return sum;
	}

	private double calculateSpeed(double v1, double v2, double a1, double a2, double t) {
		return (v1 + v2) / 2 + (a1 + a2) / 2 * t;
	}
	
	private double calculateAngle(double v1, double v2, double a1, double a2, double t) {
		double res = ((v1 - v2) / diameter * t) + ((a1 - a2) / (2 * diameter) * t * t);
		return res;
	}
}
