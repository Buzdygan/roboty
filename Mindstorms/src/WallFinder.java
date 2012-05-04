import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import robot.Robot;
import behaviours.EndGame;
import behaviours.MoveForward;
import behaviours.StepBack;
public class WallFinder {
	public static void main(String[] args){
		Robot robot = new Robot();
		Behavior [] behaviors = {new MoveForward(robot), new StepBack(robot), new EndGame(robot)};
		Arbitrator arbiter = new Arbitrator(behaviors);
		arbiter.start();
	
	}
}
