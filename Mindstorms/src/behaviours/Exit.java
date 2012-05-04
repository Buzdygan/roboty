package behaviours;

import lejos.nxt.Button;
import lejos.robotics.subsumption.Behavior;

public class Exit implements Behavior {

	@Override
	public boolean takeControl() {
		return (Button.readButtons() & Button.ID_ESCAPE) != 0;
	}

	@Override
	public void action() {
		System.exit(0);
	}

	@Override
	public void suppress() {}

}
