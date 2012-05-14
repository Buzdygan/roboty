package position;

import java.util.ArrayList;
import java.util.List;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.RConsole;
import lejos.util.Delay;
import robot.AlmostDifferentialPilot;
import robot.Robot;

public class PositionFinder {
	
	private Robot robot;
	private final int DIST_MODIFIER = 2;
	private final int LONG_SIDE = 1;
	private final int SHORT_SIDE = 2;
	private final int FRONT_SIDE = 1;
	private final int LEFT_SIDE = 2;
	private final int BACK_SIDE = 3;
	private final int RIGHT_SIDE = 4;
	private final int TURNS = 12;
	private final int MEASUREMENTS = 7;
	private final int TURN_ANGLE = 360 / TURNS;
	private final int MEASUREMENT_DELAY = 5;
	private final int TURN_DELAY = 10;
	private final int WIDTH = 120;
	private final int HEIGHT = 180;
	private final int WRONG_DIST = 255;
	private final int BAD_POSITION_ALARM_NUMBER = 4;
	private final int ACCEPTABLE_DIST_ERROR = 50;
	
	private int badPositionCounter;
	
	public PositionFinder(Robot rob)
	{
		robot = rob;
		badPositionCounter = 0;
	}
	
	private int getMostProbable(List<Integer> elist){
		float bestScore = 0;
		int result = 0;
		for(Integer el: elist)
		{
			float score = 0;
			for(Integer el2: elist)
				score += 1.0 / (1.0 + Math.abs(el - el2));
			if(score > bestScore)
			{
				bestScore = score;
				result = el;
			}
		}
		return result;
	}
	
	private int getXDistance(double angle, int dist){
		int res = (int)((double) dist * Math.cos(angle));
		if(angle < Math.PI * 1.5 && angle > Math.PI * 0.5)
			return -res;
		return WIDTH - res;
	}
	
	private int getYDistance(double angle, int dist){
		int res = (int)((double) dist * Math.sin(angle));
		if(angle < Math.PI)
			return HEIGHT - res;
		return -res;
	}
	
	private boolean in(double a, double b, double v){
		return(v >= a && v <= b);
	}
	
	private int getAng(){
		int comp = (int)robot.getCompass().getDegreesCartesian();
		return (90 + comp) % 360;
	}
	
	private double getAngle(int ang){
		return Math.PI * ang / 180.0;
	}
	
	private double getAngle(){
		return getAngle(getAng());
	}
	
	private int getDist(){
		return robot.getUltrasonic().getDistance() + DIST_MODIFIER;
	}
	
	private int whichSide(int angle)
	{
		int res = 0;
		if(angle <= 60 || in(120, 240, angle) || angle > 300)
			res += LONG_SIDE;
		if(in(30, 150, angle) || in(210, 330, angle))
			res += SHORT_SIDE;
		return res;
	}
	
	public Position findPosition(){
		AlmostDifferentialPilot pilot = robot.getDifferentialPilot();
		List<Integer> xPositions = new ArrayList<Integer>();
		List<Integer> yPositions = new ArrayList<Integer>();
		
		
		for(int i = 0; i < TURNS; i++)
		{
			pilot.rotate(TURN_ANGLE);
			Delay.msDelay(TURN_DELAY);
			if (Button.readButtons() == Button.ID_LEFT)
				break;
			List<Integer> dists = new ArrayList<Integer>();
			for(int j = 0; j < MEASUREMENTS; j++)
			{
				int distance = getDist();
				if(distance != WRONG_DIST)
					dists.add(distance);
				Delay.msDelay(MEASUREMENT_DELAY);
			}
			if(dists.isEmpty())
				continue;
			int dist = getMostProbable(dists);
			LCD.clear();
			int ang = getAng();
			int side = whichSide(ang);
			LCD.drawInt(side, 0, 0);
			LCD.drawInt(ang, 4, 0);
			double angle = getAngle(ang);
			if((side & LONG_SIDE) > 0)
			{
				int xpos = getXDistance(angle, dist);
				xPositions.add(xpos);
				LCD.drawInt(xpos, 0, 2);
			}
			if((side & SHORT_SIDE) > 0)
			{
				int ypos = getYDistance(angle, dist);
				yPositions.add(ypos);
				LCD.drawInt(ypos, 0, 4);
			}
			
		}
		int xPosition = getMostProbable(xPositions);
		int yPosition = getMostProbable(yPositions);
		LCD.clear();
		LCD.drawInt(xPosition, 0, 0);
		LCD.drawInt(yPosition, 0, 1);
		RConsole.println("Position:  " + Integer.toString(xPosition) + "  " + Integer.toString(yPosition));
		Complex coordinates = new Complex(10.0 * yPosition, 10.0 * (WIDTH - xPosition));
		return new Position(coordinates, Complex.fromPolar(1, (robot.getCompass().getDegreesCartesian() / 180.0) * Math.PI));
	}
	
	private boolean checkMeasurements(double expectedDist, double dist)
	{
		return Math.abs(expectedDist - dist) < ACCEPTABLE_DIST_ERROR; // in milimeters
	}
	
	private int getSide(double front, double left, double back, double right, double angle){
		double zeroAngle = 0.0;
		double frontLeftAngle = new Complex(front, left).getAngle();
		double backLeftAngle = new Complex(back, left).getAngle();
		double backRightAngle = new Complex(back, right).getAngle();
		double frontRightAngle = new Complex(front, right).getAngle();
		double endAngle = 360.0;
		if(in(zeroAngle, frontLeftAngle, angle) || in(frontRightAngle, endAngle, angle))
			return FRONT_SIDE;
		if(in(frontLeftAngle, backLeftAngle, angle))
			return LEFT_SIDE;
		if(in(backLeftAngle, backRightAngle, angle))
			return BACK_SIDE;
		if(in(backRightAngle, frontRightAngle, angle))
			return RIGHT_SIDE;
		return 0;
	}
	
	/* returns expected distance in milimeters */
	public double expectedDistance(Position position)
	{
		double res = 0.0;
		double yPos = position.getCoordinates().getIm();
		double xPos = position.getCoordinates().getRe();
		double front = HEIGHT - xPos;
		double left = WIDTH - yPos;
		double back = -xPos;
		double right = -yPos;
		double angle = getAngle();
		int side = getSide(front, left, back, right, angle);
		switch(side){
			case FRONT_SIDE:
				res = front * Math.cos(angle);
				break;
			case LEFT_SIDE:
				res = left * Math.sin(angle);
				break;
			case BACK_SIDE:
				res = back * Math.cos(angle);
				break;
			case RIGHT_SIDE:
				res = right * Math.sin(angle);
				break;
		}
		return res * 10.0;
	}
	
	public boolean verifyPosition(Position position)
	{
		double dist = getDist() * 10.0; // convert to milimeters
		if(checkMeasurements(expectedDistance(position), dist) == false)
		{
			badPositionCounter ++;
			if(badPositionCounter >= BAD_POSITION_ALARM_NUMBER)
			{
				badPositionCounter = 0;
				return false;
			}
		}
		else
			badPositionCounter = 0;
		return true;
	}

}
