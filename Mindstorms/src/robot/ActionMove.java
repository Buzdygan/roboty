package robot;

public class ActionMove implements RobotAction {

	static final double basicSpeed = 10;
	
	private int leftSpeed, rightSpeed;

	public ActionMove(int leftSpeed, int rightSpeed) {
		super();
		this.leftSpeed = leftSpeed;
		this.rightSpeed = rightSpeed;
	}

	@Override
	public void runAction(Robot robot) {
		robot.getPilot().move(leftSpeed, rightSpeed);
	}
	
}
