package robot;

import java.util.ArrayList;
import java.util.List;

import lejos.nxt.ColorSensor;
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
	
	static final long actionTime = 100; // in miliseconds
	
	private IRSeekerV2 seeker;
	private CompassMindSensor compass;
	private ColorSensor colorlight;
	private UltrasonicSensor distance;
	
	private NXTRegulatedMotor kicker;
	private DifferentialPilot pilot;
	
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
		pilot = new DifferentialPilot(1.6535f, 4.53f, Motor.C, Motor.B);
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

	public DifferentialPilot getPilot() {
		return pilot;
	}

	public void setPilot(DifferentialPilot pilot) {
		this.pilot = pilot;
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
		long cstartTime, timeDiff;
		do {
			cstartTime = System.currentTimeMillis();
			while (runNextAction()) {
				positionManager.updatePosition();
				timeDiff = System.currentTimeMillis() - cstartTime;
				if (timeDiff < actionTime) {
					Delay.msDelay(actionTime - timeDiff);
				}			
				cstartTime = System.currentTimeMillis();
			}
			Delay.msDelay(1000);
		} while(true);
	}
}
