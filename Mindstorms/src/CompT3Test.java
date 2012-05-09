import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import position.Complex;
import position.CurrentPositionBox;
import position.Position;
import robot.Robot;
import behaviors.Exit;
import behaviors.FindBall;
import behaviors.Kick;
import behaviors.Reset;
import behaviors.RotateToGoal;
import behaviors.UpdatePosition;

public class CompT3Test {
	public static void main(String[] args) {
		Robot robot = new Robot();
		CurrentPositionBox positionBox = new CurrentPositionBox();

		robot.initialize(positionBox, new Position(new Complex(200, 200), new Complex(1, 0)));
	//	RConsole.open();

		Behavior behaviors[] = new Behavior[6];
		behaviors[0] = new Kick(robot, positionBox);
		behaviors[1] = new RotateToGoal(robot, positionBox);
		behaviors[2] = new FindBall(robot, positionBox);
		behaviors[3] = new UpdatePosition(robot, positionBox);
		behaviors[4] = new Reset(robot, positionBox);
		behaviors[5] = new Exit();

		Arbitrator arbiter = new Arbitrator(behaviors);

		Delay.msDelay(500);

		arbiter.start();

	}
}
