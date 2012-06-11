import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import position.CurrentPositionBox;
import robot.Robot;
import behaviors.Exit;
import behaviors.FindBall;
import behaviors.Kick;
import behaviors.Reset;
import behaviors.ReturnHome;
import behaviors.RotateToGoal;
import behaviors.UpdatePosition;


public class CompT3 {
	public static void main(String[] args) {
		Robot robot = new Robot();
		CurrentPositionBox positionBox = new CurrentPositionBox();
		
		robot.initialize(positionBox, robot.getPositionFinder().findPosition());
		new ReturnHome(robot, positionBox).action();
		
		Behavior behaviors[] = new Behavior[6];
		behaviors[0] = new Kick(robot, positionBox);
		behaviors[1] = new RotateToGoal(robot, positionBox);
		behaviors[2] = new FindBall(robot, positionBox);
		behaviors[3] = new UpdatePosition(robot, positionBox);
		behaviors[4] = new Reset(robot, positionBox);
		behaviors[5] = new Exit();

		Arbitrator arbiter = new Arbitrator(behaviors);
		
		//Button.waitForAnyPress();
		
		arbiter.start();
	}
}
