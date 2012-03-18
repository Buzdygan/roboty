import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.util.Delay;

public class DoNothing {
	public static void main(String[] args) {
		LCD.drawString("Nothing", 0, 0);
		Delay.msDelay(500);
		Button.readButtons();
		while (Button.readButtons() == 0) {}
	}
}
