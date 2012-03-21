import robot.Robot;
import robot.RobotAction;


public class ActionStop implements RobotAction {

	@Override
	public void runAction(Robot robot) {
		robot.getPilot().stop();
		robot.getPilot().setSpeed(1, 1);
	}

}
