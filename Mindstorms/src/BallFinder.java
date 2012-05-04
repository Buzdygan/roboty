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
		Behavior b0 = new EndGame(robot);
		Behavior b1 = new StepBack(robot);
		Behavior b3 = new TurnLeft(robot);
		//Behavior [] behaviors = {b0,b1,b2,b3,b4};
		Behavior [] behaviors = {b0};
		Arbitrator arbiter = new Arbitrator(behaviors);
		arbiter.start();
	
	}
}
