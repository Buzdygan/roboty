import lejos.nxt.Button;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import position.CurrentPositionBox;
import robot.Robot;
import behaviours.Exit;
import behaviours.FindBall;
import behaviours.Kick;
import behaviours.MoveToOurHalf;
import behaviours.RotateToGoal;
import behaviours.RotateToOurHalf;


public class CompetitionType2 {
	public static void main(String[] args) {
		Robot robot = new Robot();
		CurrentPositionBox positionBox = new CurrentPositionBox();
		
		robot.initialize(positionBox);
		
		Behavior behaviors[] = new Behavior[6];
		behaviors[0] = new Kick(robot);
		behaviors[1] = new RotateToGoal(robot, positionBox);
		behaviors[2] = new MoveToOurHalf(robot, positionBox);
		behaviors[3] = new RotateToOurHalf(robot, positionBox);
		behaviors[4] = new FindBall(robot);
		behaviors[5] = new Exit();

		Arbitrator arbiter = new Arbitrator(behaviors);
		
		Button.waitForAnyPress();
		
		arbiter.start();
		
	}
}
