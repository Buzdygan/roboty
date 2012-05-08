package position;

import java.util.ArrayList;
import java.util.List;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.util.Delay;
import robot.AlmostDifferentialPilot;
import robot.Robot;

public class PositionFinder {
	
	private Robot robot;
	private final int LONG_SIDE = 1;
	private final int SHORT_SIDE = 2;
	private final int turns = 12;
	private final int measurements = 7;
	private final int turnAngle = 360 / turns;
	private final int measurementDelay = 50;
	private final int turnDelay = 2000;
	
	public PositionFinder(Robot rob)
	{
		robot = rob;
	}
	
	private int getMostProbable(List<Integer> elist)
	{
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
	
	private int getXDistance(double angle, int dist)
	{
		int res = (int)((double) dist * Math.cos(angle));
		if(angle < Math.PI * 1.5 && angle > Math.PI * 0.5)
			return -res;
		return 120 - res;
	}
	
	private int getYDistance(double angle, int dist)
	{
		int res = (int)((double) dist * Math.sin(angle));
		if(angle < Math.PI)
			return 180 - res;
		return -res;
	}
	
	private boolean in(int a, int b, int v)
	{
		return(v >= a && v <= b);
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
		
		LCD.drawString("AAA", 0,3);
		
		Position startPosition = new Position();
		AlmostDifferentialPilot pilot = robot.getDifferentialPilot();
		List<Integer> xPositions = new ArrayList<Integer>();
		List<Integer> yPositions = new ArrayList<Integer>();
		
		
		for(int i = 0; i < turns; i++)
		{
			pilot.rotate(turnAngle);
			Delay.msDelay(turnDelay);
			if (Button.readButtons() == Button.ID_LEFT)
				break;
			List<Integer> dists = new ArrayList<Integer>();
			for(int j = 0; j < measurements; j++)
			{
				int dist = robot.getUltrasonic().getDistance();
				if(dist != 255)
					dists.add(dist);
				Delay.msDelay(measurementDelay);
			}
			if(dists.isEmpty())
				continue;
			int dist = getMostProbable(dists);
			LCD.clear();
			//LCD.drawInt(dist, 0, 0);
			

			int comp = (int)robot.getCompass().getDegreesCartesian();
			LCD.drawInt(comp, 0, 0);
			int ang = (90 + comp) % 360;
			int side = whichSide(ang);
			//LCD.drawInt(side, 0, 0);
			LCD.drawInt(ang, 4, 0);
			//LCD.drawInt(ang, 0, 1);
			double angle = Math.PI * ang / 180.0;
			
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
		Complex coordinates = new Complex(yPosition, 120 - xPosition);
		startPosition.setCoordinates(coordinates);
		startPosition.getCoordinates().mul(new Complex(10,0)); // translate centimeters to millimeters
		return startPosition;
	}

}
