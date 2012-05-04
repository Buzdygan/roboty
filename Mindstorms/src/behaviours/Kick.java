package behaviours;

import lejos.nxt.ColorSensor.Color;
import lejos.nxt.LCD;
import robot.Robot;

public class Kick extends RobotBehavior {

	public Kick(Robot robot) {
		super(robot);
	}

	@Override
	public boolean takeControl() {
		Color color = getRobot().getColorlight().getColor();
		int color_sum = color.getBlue() + color.getGreen() + color.getRed();
		LCD.clear();
		LCD.drawInt(color_sum, 0, 0);
		return false;
		//return color_sum > 25;
	}

	@Override
	public void action() {
		getRobot().kick();
		
	}

	@Override
	public void suppress() {
		
	}

}
