package robot;

import java.util.ArrayList;
import java.util.List;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassMindSensor;
import lejos.nxt.addon.IRSeekerV2;
import lejos.nxt.addon.IRSeekerV2.Mode;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import position.Complex;
import position.CurrentPositionBox;
import position.Position;
import position.PositionManager;

public class Robot {

	/*
	 * All lengths are expressed in millimeters.
	 * Wheel diameter value is usually printed on the tire; 
	 * Use DiameterHelper to get diameter / wheelRadius value;
	 */
	
	static final double wheelRadius = 21.6;
	static final double diameter = wheelRadius * 6.175;
	
	public static double getDiameter() {
		return diameter;
	}

	private IRSeekerV2 seeker;
	private CompassMindSensor compass;
	private ColorSensor colorlight;
	private UltrasonicSensor ultrasonic;
	
	private NXTRegulatedMotor left, right, kicker;
	private DifferentialPilot diffPilot;
	private SimplePilot pilot;
	
	private PositionManager positionManager;
	private List<RobotAction> actionList;

	public Robot() {
		left = Motor.C;
		right = Motor.B;
		initializeSensors();
		positionManager = new PositionManager(left, right, wheelRadius, diameter);
		actionList = new ArrayList<RobotAction>();
	}
	
	private void initializeSensors() {
		seeker = new IRSeekerV2(SensorPort.S1, Mode.AC);
		compass = new CompassMindSensor(SensorPort.S2);
		compass.resetCartesianZero();
		colorlight = new ColorSensor(SensorPort.S4);
		ultrasonic = new UltrasonicSensor(SensorPort.S3);
		
		kicker = Motor.A;
		pilot = new SimplePilot(left, right, wheelRadius, diameter);
		diffPilot = new DifferentialPilot(wheelRadius * 2, diameter, left, right);
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
	
	public Position findPosition(){
		Position startPosition = new Position();
		DifferentialPilot pilot = getDifferentialPilot();
		List<Integer> xPositions = new ArrayList<Integer>();
		List<Integer> yPositions = new ArrayList<Integer>();
		final int turns = 12;
		final int measurements = 7;
		final int turnAngle = 360 / turns;
		
		for(int i = 0; i < turns; i++)
		{
			if (Button.readButtons() != 0)
				break;
			List<Integer> dists = new ArrayList<Integer>();
			for(int j = 0; j < measurements; j++)
			{
				int dist = ultrasonic.getDistance();
				if(dist != 255)
					dists.add(dist);
				Delay.msDelay(50);
			}
			if(dists.isEmpty())
				continue;
			int dist = getMostProbable(dists);
			LCD.clear();
			LCD.drawInt(dist, 0, 0);
			double angle = (90 + (int)compass.getDegreesCartesian()) % 360;
			LCD.drawInt((int)angle, 0, 1);
			angle = Math.PI * angle / 180.0;
			int xpos = getXDistance(angle, dist);
			int ypos = getYDistance(angle, dist);
			xPositions.add(xpos);
			yPositions.add(ypos);
			LCD.drawInt(xpos, 0, 2);
			LCD.drawInt(ypos, 0, 3);
			pilot.rotate(turnAngle);
			Delay.msDelay(200);
		}
		int xPosition = getMostProbable(xPositions);
		int yPosition = getMostProbable(yPositions);
		LCD.clear();
		LCD.drawInt(xPosition, 0, 0);
		LCD.drawInt(yPosition, 0, 1);
		Complex coordinates = new Complex(yPosition, 120 - xPosition);
		startPosition.setCoordinates(coordinates);
		return startPosition;
	}

	public NXTRegulatedMotor getLeft() {
		return left;
	}

	public NXTRegulatedMotor getRight() {
		return right;
	}

	public IRSeekerV2 getSeeker() {
		return seeker;
	}

	public void setSeeker(IRSeekerV2 seeker) {
		this.seeker = seeker;
	}

	public CompassMindSensor getCompass() {
		return compass;
	}

	public void setCompass(CompassMindSensor compass) {
		this.compass = compass;
	}

	public ColorSensor getColorlight() {
		return colorlight;
	}

	public void setColorlight(ColorSensor colorlight) {
		this.colorlight = colorlight;
	}

	public UltrasonicSensor getUltrasonic() {
		return ultrasonic;
	}

	public void setUltrasonic(UltrasonicSensor ultrasonic) {
		this.ultrasonic = ultrasonic;
	}

	public NXTRegulatedMotor getKicker() {
		return kicker;
	}

	public void setKicker(NXTRegulatedMotor kicker) {
		this.kicker = kicker;
	}
	
	public DifferentialPilot getDifferentialPilot() {
		return diffPilot;
	}

	public SimplePilot getPilot() {
		return pilot;
	}

	public PositionManager getPositionManager() {
		return positionManager;
	}

	public List<RobotAction> getActionList() {
		return actionList;
	}

	public void setActionList(List<RobotAction> actionList) {
		this.actionList = actionList;
	}

	public void addAction(RobotAction action) {
		actionList.add(action);
	}
	
	public void kick() {
		getKicker().rotate(-90);
		getKicker().rotate(90);
	}
/*	
	public void run() {
		long endTime, timeDiff = 0;

		endTime = System.currentTimeMillis();
		while (runNextAction()) {
			positionManager.updatePosition(pilot.getPreviousMovement(timeDiff));;
			
			// LCD.clear();
			// LCD.drawString( positionManager1.getCurrentPosition().getCoordinates().toString(), 0, 0);
			// LCD.drawString( positionManager1.getCurrentPosition().getRotation().toString(), 0, 1);
			
			timeDiff = System.currentTimeMillis() - endTime;
			if (timeDiff < actionTime) {
				Delay.msDelay(actionTime - timeDiff);
			} else {
				LCD.drawString(Long.toString(timeDiff), 0, 7);
			}
			timeDiff = System.currentTimeMillis() - endTime;
			endTime += timeDiff;
		}
		positionManager.updatePosition(pilot.getPreviousMovement(timeDiff));

		// LCD.clear();
		// LCD.drawString( positionManager1.getCurrentPosition().getCoordinates().toString(), 0, 0);
		// LCD.drawString( positionManager1.getCurrentPosition().getRotation().toString(), 0, 1);

	}
*/

	public void initialize(CurrentPositionBox positionBox) {
		Position startPosition = findPosition();
		positionBox.setCurrentPosition(startPosition);
		positionManager.setPositionBox(positionBox);
		positionManager.reset();
	}
}
