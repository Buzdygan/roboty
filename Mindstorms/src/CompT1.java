import lejos.nxt.Button;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import position.CurrentPositionBox;
import robot.Robot;
import behaviors.Exit;
import behaviors.FindBall;
import behaviors.Kick;
import behaviors.MoveToOurHalf;
import behaviors.Reset;
import behaviors.RotateToGoal;
import behaviors.RotateToOurHalf;
import behaviors.UpdatePosition;


public class CompT1 {
	public static void main(String[] args) {
		Robot robot = new Robot();
		CurrentPositionBox positionBox = new CurrentPositionBox();
		
		robot.initialize(positionBox, robot.getPositionFinder().findPosition());
		
		Behavior behaviors[] = new Behavior[8];
		behaviors[0] = new Kick(robot, positionBox);
		behaviors[1] = new RotateToGoal(robot, positionBox);
		behaviors[2] = new MoveToOurHalf(robot, positionBox);
		behaviors[3] = new RotateToOurHalf(robot, positionBox);
		behaviors[4] = new FindBall(robot, positionBox);
		behaviors[5] = new UpdatePosition(robot, positionBox);
		behaviors[6] = new Reset(robot, positionBox);
		behaviors[7] = new Exit();

		Arbitrator arbiter = new Arbitrator(behaviors);
		
		Button.waitForAnyPress();
		
		Delay.msDelay(1000);
		
		arbiter.start();
		
	}
}
