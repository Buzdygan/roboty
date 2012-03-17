import lejos.nxt.Button;
import lejos.util.Delay;

public class DoNothing {
	public static void main(String[] args) {
		Delay.msDelay(500);
		Button.readButtons();
		while (Button.readButtons() == 0) {}
	}
}
