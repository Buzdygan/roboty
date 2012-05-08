package robot;

import java.util.ArrayList;
import java.util.List;

import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassHTSensor;
import lejos.nxt.addon.IRSeekerV2;
import lejos.nxt.addon.IRSeekerV2.Mode;
import position.CurrentPositionBox;
import position.Position;
import position.PositionFinder;
import position.PositionManager;

public class Robot {

	/*
	 * All lengths are expressed in millimeters.
	 * Wheel diameter value is usually printed on the tire; 
	 * Use DiameterHelper to get diameter / wheelRadius value;
	 */
	
	static final double wheelRadius = 21.6; // in millimeters
	static final double diameter = wheelRadius * 6.175; // in millimeters
	static final int MAX_DISTANCE = 255;
	
	public static double getDiameter() {
		return diameter;
	}

	private IRSeekerV2 seeker;
	private CompassHTSensor compass;
	private ColorSensor colorlight;
	private UltrasonicSensor ultrasonic;
	
	private NXTRegulatedMotor left, right, kicker;
	private AlmostDifferentialPilot diffPilot;
	private SimplePilot pilot;
	
	private PositionManager positionManager;
	private PositionFinder positionFinder;
	
	public PositionFinder getPositionFinder() {
		return positionFinder;
	}

	public void setPositionFinder(PositionFinder positionFinder) {
		this.positionFinder = positionFinder;
	}

	private List<RobotAction> actionList;

	public Robot() {
		left = Motor.C;
		right = Motor.B;
		left.setAcceleration(2000);
		right.setAcceleration(2000);
		initializeSensors();
		positionManager = new PositionManager(left, right, wheelRadius, diameter);
		positionFinder = new PositionFinder(this);
		actionList = new ArrayList<RobotAction>();
	}
	
	private void initializeSensors() {
		seeker = new IRSeekerV2(SensorPort.S1, Mode.AC);
		compass = new CompassHTSensor(SensorPort.S2);
		compass.resetCartesianZero();
		colorlight = new ColorSensor(SensorPort.S4);
		ultrasonic = new UltrasonicSensor(SensorPort.S3);
		
		kicker = Motor.A;
		pilot = new SimplePilot(left, right, wheelRadius, diameter);
		diffPilot = new AlmostDifferentialPilot(wheelRadius, diameter, left, right);
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

	public CompassHTSensor getCompass() {
		return compass;
	}

	public void setCompass(CompassHTSensor compass) {
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
	
	public AlmostDifferentialPilot getDifferentialPilot() {
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

	public void initialize(CurrentPositionBox positionBox, Position startPosition) {
		positionBox.setCurrentPosition(startPosition);
		positionManager.setPositionBox(positionBox);
		positionManager.reset();
	}
	
	public void initialize(CurrentPositionBox positionBox) {
		initialize(positionBox, new Position());
	}
	
	public boolean canHaveBall(int distance, int direction) {
		return (((distance == MAX_DISTANCE) || (distance < 10)) && (direction != 0) && (Math.abs(direction - 5) < 2));
	}
	
}
