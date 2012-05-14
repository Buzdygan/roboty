package behaviors;

import lejos.nxt.LCD;
import lejos.nxt.comm.RConsole;
import position.Complex;
import position.CurrentPositionBox;
import robot.Robot;

public class UpdatePosition extends RobotPositionBehavior {

	public UpdatePosition(Robot robot, CurrentPositionBox currentPositionBox) {
		super(robot, currentPositionBox);
	}

	@Override
	public boolean takeControl() {
		getRobot().getPositionManager().updatePosition();
		Complex direction = Complex.fromPolar(1, getRobot().getCompass().getDegreesCartesian() * Math.PI / 180);
		direction = direction.add(getCurrentPosition().getRotation());
		getCurrentPosition().setRotation(direction.div(direction.getNorm()));
		
		RConsole.print(Integer.toString((int)getCurrentPosition().getCoordinates().getRe()));
		RConsole.print(" ");
		RConsole.println(Integer.toString((int)getCurrentPosition().getCoordinates().getIm()));
		
		LCD.drawInt((int)getCurrentPosition().getCoordinates().getRe(), 0, 5);
		LCD.drawInt((int)getCurrentPosition().getCoordinates().getIm(), 5, 5);
		
		LCD.drawInt((int)(getCurrentPosition().getRotation().getAngle() * 180 / Math.PI), 0, 6);
		RConsole.println(Integer.toString((int)(getCurrentPosition().getRotation().getAngle() * 180 / Math.PI)));
		return false;
	}

	@Override
	public void action() {}

	@Override
	public void suppress() {}

}
