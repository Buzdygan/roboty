package robot;

import java.util.ArrayList;
import java.util.List;

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
import position.PositionManager;

public class Robot {

	/*
	 * All lengths are expressed in millimeters.
	 * Wheel diameter value is usually printed on the tire; 
	 * Use DiameterHelper to get diameter / wheelRadius value;
	 */
	
	static final double wheelRadius = 21.6;
	static final double diameter = wheelRadius * 6.175;
	
	static final long actionTime = 200; // in miliseconds
	
	private IRSeekerV2 seeker;
	private CompassMindSensor compass;
	private ColorSensor colorlight;
	private UltrasonicSensor distance;
	
	private NXTRegulatedMotor kicker;
	private DifferentialPilot diffPilot;
	private SimplePilot pilot;
	
	private PositionManager positionManager;
	private List<RobotAction> actionList;

	public Robot() {
		initSensors();
		positionManager = new PositionManager(Motor.C, Motor.B, wheelRadius, diameter);
		actionList = new ArrayList<RobotAction>();
	}
	
	private void initSensors() {
		seeker = new IRSeekerV2(SensorPort.S1, Mode.AC);
		compass = new CompassMindSensor(SensorPort.S2);
		colorlight = new ColorSensor(SensorPort.S4);
		distance = new UltrasonicSensor(SensorPort.S3);
		
		kicker = Motor.A;
		pilot = new SimplePilot(Motor.C, Motor.B, wheelRadius, diameter);
		diffPilot = new DifferentialPilot(wheelRadius * 2, diameter, Motor.C, Motor.B);
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

	public UltrasonicSensor getDistance() {
		return distance;
	}

	public void setDistance(UltrasonicSensor distance) {
		this.distance = distance;
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
	
	public boolean runNextAction() {
		if (actionList.isEmpty()) {
			return false;
		}
		actionList.get(0).runAction(this);
		actionList.remove(0);
		return true;
	}
	
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
}
