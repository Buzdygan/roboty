import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.Sound;
import lejos.util.Delay;
import robot.Robot;

/**
 * This program rotate robot 360 degrees to get (diameter / wheel radius) value.
 * It uses Motor.B to rotate robot and Compass to control the rotation.
 */
public class DiameterHelper {

	public static void main(String[] args) {
		float compassReading, newReading, compassDifference, sum = 0;
		NXTRegulatedMotor motor = Motor.B;

		Delay.msDelay(1000);
		
		Button.readButtons();

		Robot robot = new Robot();
		robot.getCompass().startCalibration();
		robot.getCompass().resetCartesianZero();
		compassReading = robot.getCompass().getDegreesCartesian();

		while ((Math.abs(sum) < 800) && (Button.readButtons() == 0)) {
			motor.setSpeed(30);
			motor.backward();

			newReading = robot.getCompass().getDegreesCartesian();
			compassDifference = newReading - compassReading;
			while (compassDifference > 180) {
				compassDifference -= 360;
			}
			while (compassDifference < -180) {
				compassDifference += 360;
			}

			sum += compassDifference;
			compassReading = newReading;
			LCD.clear();
			LCD.drawString(new Float(compassReading).toString(), 0, 0);
			LCD.drawString(new Float(newReading).toString(), 0, 1);

			LCD.drawString(new Float(compassDifference).toString(), 0, 3);

			LCD.drawString(new Float(sum).toString(), 0, 5);
			LCD.drawString(new Integer(motor.getTachoCount()).toString(), 0, 6);
		}
		motor.stop();

		LCD.clear();
		LCD.drawString("d / wh r:", 0, 0);
		LCD.drawString(
				new Float((float)Math.abs(motor.getTachoCount()) / 360).toString(), 0,
				2);

		robot.getCompass().stopCalibration();
		Sound.beep();
		Button.waitForAnyPress();
	}
}
