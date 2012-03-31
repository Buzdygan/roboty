import lejos.robotics.subsumption.*;
import behaviours.*;
public class BallFinder {
	public static void main(String[] args){
		Behavior b1 = new Wait();
		Behavior b2 = new GoStraight();
		Behavior b3 = new TurnLeft();
		Behavior b4 = new TurnRight();
		Behavior [] behaviors = {b1,b2,b3,b4};
		Arbitrator arbiter = new Arbitrator(behaviors);
		arbiter.start();
	
	}
}
