import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;
import position.CurrentPositionBox;
import robot.Robot;
import behaviors.Exit;
import behaviors.Rotate;
import behaviors.UpdatePosition;


public class RotateTest {
	public static void main(String[] args) {
		
		Robot robot = new Robot();
		CurrentPositionBox positionBox = new CurrentPositionBox();
		robot.initialize(positionBox);
		Behavior behaviors[] = new Behavior[3];

		behaviors[0] = new Rotate(robot);
		behaviors[1] = new UpdatePosition(robot, positionBox);
		behaviors[2] = new Exit();

		Arbitrator arbiter = new Arbitrator(behaviors);
		
		Delay.msDelay(1000);
		
		arbiter.start();
	}
}
