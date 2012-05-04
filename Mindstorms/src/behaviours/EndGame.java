package behaviours;

import lejos.nxt.Button;
import robot.Robot;

public class EndGame extends RobotBehavior{
	
	public EndGame(Robot robot) {
		super(robot);
	}

	@Override
	public boolean takeControl() {
		return (Button.readButtons() & Button.ID_ESCAPE) != 0;
	}

	@Override
	public void action() {
		System.exit(0);
	}

	@Override
	public void suppress() {
		
	}

}
