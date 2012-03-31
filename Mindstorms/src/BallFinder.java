import lejos.nxt.Motor;
import robot.Robot;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.*;
import behaviours.*;
public class BallFinder {
	public static void main(String[] args){
		DifferentialPilot pilot = new DifferentialPilot(40, 15, Motor.A, Motor.B);
		Robot robot = new Robot();
		pilot.setAcceleration(80);
		Behavior b1 = new Wait(pilot);
		Behavior b2 = new GoStraight(pilot, robot);
		Behavior b3 = new TurnLeft(pilot, robot);
		Behavior b4 = new TurnRight(pilot, robot);
		Behavior [] behaviors = {b1,b2,b3,b4};
		Arbitrator arbiter = new Arbitrator(behaviors);
		arbiter.start();
	
	}
}
