import lejos.util.Delay;
import robot.Robot;


public class CompassCal {
	public static void main(String[] args) {

		Robot robot = new Robot();
		
		Delay.msDelay(500);
		
		robot.getCompass().startCalibration();

		robot.getLeft().setSpeed(30);
		robot.getRight().setSpeed(30);
		robot.getLeft().forward();
		robot.getRight().backward();
		
		Delay.msDelay(70 * 1000);
		
		robot.getCompass().stopCalibration();
	}
}
