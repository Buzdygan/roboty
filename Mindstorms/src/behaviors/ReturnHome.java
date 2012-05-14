package behaviors;

import lejos.nxt.LCD;
import lejos.nxt.comm.RConsole;
import position.CurrentPositionBox;
import robot.AlmostDifferentialPilot;
import robot.Robot;

public class ReturnHome extends RobotPositionBehavior {

	private boolean suppressed;
	public ReturnHome(Robot robot, CurrentPositionBox currentPositionBox) {
		super(robot, currentPositionBox);
	}
	
	int angle_to_ball(){
		// robot_angle 0 - heading opponent goal
		double robot_angle = getRobot().getCompass().getDegreesCartesian();
		// ball_angle is from -180 to 180 or Nan
		double ball_angle = 180.0;
		int direction = getRobot().getSeeker().getDirection();
		if (direction > 0)
			ball_angle = (direction - 5) * 30.0;
		int result = (360 + (int)robot_angle - (int)ball_angle) % 360;
		/*
		RConsole.println("angle_to_ball");
		RConsole.println(Double.toString(ball_angle));
		RConsole.println(Double.toString(robot_angle));
		RConsole.println(Integer.toString(result));
		*/
		return result;
	}

	@Override
	public boolean takeControl() {
		int angle_to_ball = angle_to_ball();
		//RConsole.println("takeControlReturnHome");
		//RConsole.println(Integer.toString(angle_to_ball));
		return angle_to_ball >= 90 && angle_to_ball <= 270;
	}
	
	private void rotate_to_home()
	{
		AlmostDifferentialPilot pilot = getRobot().getDifferentialPilot();
		getRobot().getDifferentialPilot().setSpeedRate(0.5);
		int robot_angle = (int)(getRobot().getCompass().getDegreesCartesian());
		int how_far = Math.abs(robot_angle - 180);
		//RConsole.println("how far");
		//RConsole.println(Integer.toString(how_far));
		if(how_far < 3)
		{
			pilot.forward();
			return;
		}
		if(how_far < 10)
		{
			getRobot().getDifferentialPilot().setSpeedRate(0.2);
			if(robot_angle < 180)
				pilot.steer(20);
			else
				pilot.steer(-20);
			return;
		}
		getRobot().getDifferentialPilot().rotate(180 - robot_angle, true);
			
	}
	
	/*
	private void rotate_from_ball()
	{
		int direction = getRobot().getSeeker().getDirection();
		if(direction == 0)
		{
			getRobot().getDifferentialPilot().stop();
			int robot_angle = (int)(getRobot().getCompass().getDegreesCartesian());
			if(robot_angle >= 180)
				getRobot().getDifferentialPilot().rotate(-10);
			else
				getRobot().getDifferentialPilot().rotate(10);	
			getRobot().getDifferentialPilot().forward();
		}
			
	}
	*/

	@Override
	public void action() {
		LCD.clear(0);
		LCD.drawString("ReturnHome", 0, 0);
		RConsole.println("=== ReturnHome ===");
		while(true)
		{
			//RConsole.println("loopReturnHome");
			rotate_to_home();
			//rotate_from_ball();
			int angle_to_ball = angle_to_ball();	
			//RConsole.println(Integer.toString(angle_to_ball));
			if(angle_to_ball < 80 || angle_to_ball > 280)
				break;
			if(10.0 * getRobot().getUltrasonic().getDistance() < 2 * CurrentPositionBox.boundaryMargin)
				break;
		}
		
		getRobot().getDifferentialPilot().stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
