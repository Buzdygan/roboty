import robot.ActionMove;
import robot.Robot;
import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.LCD;


public class Test {
	public static void main(String[] args) {
		Robot robot = new Robot();
		
		Button.ESCAPE.addButtonListener(new ButtonListener() {
			
			@Override
			public void buttonReleased(Button b) {
				System.exit(0);
			}
			
			@Override
			public void buttonPressed(Button b) {
				LCD.clear();
				LCD.drawString("Button pressed", 0, 0);
			}
		});
/*
		robot.addAction(new ActionMove(-1, 1));
		robot.addAction(new ActionMove(-1, 1));
		robot.addAction(new ActionMove(-1, 1));
		robot.addAction(new ActionMove(-1, 1));
		robot.addAction(new ActionMove(-1, 1));
		robot.addAction(new ActionMove(-1, 1));
		robot.addAction(new ActionMove(-1, 1));
		robot.addAction(new ActionMove(-1, 1));
		robot.addAction(new ActionMove(-1, 1));
		robot.addAction(new ActionMove(-1, 1));
		robot.addAction(new ActionMove(0, 0));
		robot.addAction(new ActionMove(1, -1));
		robot.addAction(new ActionMove(1, -1));
		robot.addAction(new ActionMove(1, -1));
		robot.addAction(new ActionMove(1, -1));
		robot.addAction(new ActionMove(1, -1));
		robot.addAction(new ActionMove(1, -1));
		robot.addAction(new ActionMove(1, -1));
		robot.addAction(new ActionMove(1, -1));
		robot.addAction(new ActionMove(1, -1));
		robot.addAction(new ActionMove(1, -1));
		robot.addAction(new ActionMove(0, 0));
*/ 
		robot.addAction(new ActionMove(0, 0));
		robot.addAction(new ActionMove(1, 1));
		robot.addAction(new ActionMove(2, 2));
		robot.addAction(new ActionMove(3, 3));
		robot.addAction(new ActionMove(2, 3));
		robot.addAction(new ActionMove(2, 3));
		robot.addAction(new ActionMove(1, 3));
		robot.addAction(new ActionMove(0, 3));
		robot.addAction(new ActionMove(-1, 3));
		robot.addAction(new ActionMove(-2, 2));
		robot.addAction(new ActionMove(-1, 1));
		robot.addAction(new ActionMove(0, 0));
		robot.addAction(new ActionMove(1, 1));
		robot.addAction(new ActionMove(2, 2));
		robot.addAction(new ActionMove(3, 3));
		robot.addAction(new ActionMove(2, 3));
		robot.addAction(new ActionMove(2, 3));
		robot.addAction(new ActionMove(1, 3));
		robot.addAction(new ActionMove(0, 3));
		robot.addAction(new ActionMove(-1, 3));
		robot.addAction(new ActionMove(-2, 2));
		robot.addAction(new ActionMove(-1, 1));
		robot.addAction(new ActionMove(0, 0));
		robot.addAction(new ActionMove(1, 1));
		robot.addAction(new ActionMove(2, 2));
		robot.addAction(new ActionMove(3, 3));
		robot.addAction(new ActionMove(2, 3));
		robot.addAction(new ActionMove(2, 3));
		robot.addAction(new ActionMove(1, 3));
		robot.addAction(new ActionMove(0, 3));
		robot.addAction(new ActionMove(-1, 3));
		robot.addAction(new ActionMove(-2, 2));
		robot.addAction(new ActionMove(-1, 1));
		robot.addAction(new ActionMove(0, 0));
/*
		robot.addAction(new ActionMove(0, 0));
		robot.addAction(new ActionMove(1, 1));
		robot.addAction(new ActionMove(2, 2));
		robot.addAction(new ActionMove(3, 3));
		robot.addAction(new ActionMove(2, 2));
		robot.addAction(new ActionMove(1, 1));
		robot.addAction(new ActionMove(0, 0));
/*
		robot.addAction(new ActionMove(2, 3));
		robot.addAction(new ActionMove(2, 3));
		robot.addAction(new ActionMove(1, 3));
		robot.addAction(new ActionMove(0, 3));
		robot.addAction(new ActionMove(-1, 3));
		robot.addAction(new ActionMove(-2, 2));
		robot.addAction(new ActionMove(-1, 1));
		robot.addAction(new ActionMove(0, 0));
		*/
		robot.addAction(new ActionStop());
		
		robot.run();

		Button.waitForAnyPress();
	
	}
}
