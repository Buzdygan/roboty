package behaviours;

import robot.Robot;

public class FindBall extends RobotBehavior {

	private boolean haveBall = false;
	private int wrongSamples = 0;
	
	final int wrongSamplesTreshold = 3;
	
	private boolean sample() {
		if (haveBall == getRobot().checkBall()) {
			wrongSamples = 0;
		} else {
			++wrongSamples;
		}
		if (wrongSamples > wrongSamplesTreshold) {
			haveBall = !haveBall;
			wrongSamples = 0;
		}
		return haveBall;
	}
	
	public FindBall(Robot robot) {
		super(robot);
	}

	@Override
	public boolean takeControl() {
		return !sample();
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}

}
