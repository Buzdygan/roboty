package robot;

import position.PositionManager;
import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassMindSensor;
import lejos.nxt.addon.IRSeekerV2;
import lejos.nxt.addon.IRSeekerV2.Mode;
import lejos.robotics.navigation.DifferentialPilot;

public class Robot {

	static final float wheelRadius = 2;
	static final float diameter = 15;
	
	private IRSeekerV2 seeker;
	private CompassMindSensor compass;
	private ColorSensor colorlight;
	private UltrasonicSensor distance;
	
	private NXTRegulatedMotor kicker;
	private DifferentialPilot pilot;
	
	private PositionManager positionManager;
	
	public Robot() {
		initSensors();
		positionManager = new PositionManager(Motor.C, Motor.B, wheelRadius, diameter);
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
	
}
