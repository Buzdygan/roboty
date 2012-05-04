import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.util.Delay;
import robot.Robot;

public class HelloWorld {
	
	public static void main(String[] args) {

		Robot robot = new Robot();
		
		while (Button.readButtons() == 0) {
			LCD.clear(0);
			LCD.drawInt(robot.getUltrasonic().getDistance(), 0, 0);
			Delay.msDelay(50);
		}
		
	}
}