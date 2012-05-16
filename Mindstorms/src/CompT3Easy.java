import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import position.CurrentPositionBox;
import robot.Robot;
import behaviors.Exit;
import behaviors.FindBall;
import behaviors.Kick;
import behaviors.RotateNorth;

public class CompT3Easy {

	public static void main(String[] args) {
		Robot robot = new Robot();
		CurrentPositionBox positionBox = new CurrentPositionBox();

		robot.initialize(positionBox);

		Behavior behaviors[] = new Behavior[4];
		behaviors[0] = new Kick(robot, positionBox);
		behaviors[1] = new RotateNorth(robot);
		behaviors[2] = new FindBall(robot, positionBox);
		behaviors[3] = new Exit();

		Arbitrator arbiter = new Arbitrator(behaviors);

		Delay.msDelay(1000);

		arbiter.start();
	}
}
