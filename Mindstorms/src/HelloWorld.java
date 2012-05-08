import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.util.Delay;
import robot.Robot;

public class HelloWorld {
	
	public static void main(String[] args) {

		Robot robot = new Robot();
		
		while (Button.readButtons() == 0) {
			LCD.clear();
			LCD.drawInt(robot.getUltrasonic().getDistance(), 0, 0);
			LCD.drawInt((int)robot.getCompass().getDegrees(), 0, 2);
			LCD.drawInt((int)robot.getCompass().getDegreesCartesian(), 0, 3);
			Delay.msDelay(200);
		}
		
	}
}