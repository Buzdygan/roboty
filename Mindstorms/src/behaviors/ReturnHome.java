package behaviors;

import lejos.nxt.LCD;
import lejos.nxt.comm.RConsole;
import position.CurrentPositionBox;
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
		double ball_angle = getRobot().getSeeker().getAngle();
		RConsole.println("ball_angle");
		RConsole.println(Double.toString(ball_angle));
		if (ball_angle == Float.NaN)
		ball_angle = 180.0;
		return ((int)robot_angle + 90 - (int)ball_angle) % 360;
	}

	@Override
	public boolean takeControl() {
		int angle_to_ball = angle_to_ball();
		RConsole.println("takeControlReturnHome");
		RConsole.println(Integer.toString(angle_to_ball));
		return angle_to_ball > 180;
	}
	
	private void rotate_to_home()
	{
		while(true)
		{
			int robot_angle = (int)(getRobot().getCompass().getDegreesCartesian());
			int how_far = Math.abs(robot_angle - 180);
			RConsole.println("how far");
			RConsole.println(Integer.toString(how_far));
			if(how_far < 3)
			{
				break;
			}
			if(how_far < 30)
				getRobot().getDifferentialPilot().setSpeedRate(0.2);
			getRobot().getDifferentialPilot().rotate(180 - robot_angle, true);
			
		}
	}
	
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

	@Override
	public void action() {
		suppressed = false;
		
		while(!suppressed)
		{
			rotate_to_home();
			getRobot().getDifferentialPilot().setSpeedRate(0.6);
			getRobot().getDifferentialPilot().forward();
			//rotate_from_ball();
			int angle_to_ball = angle_to_ball();
			RConsole.println("loopReturnHome");
			RConsole.println(Integer.toString(angle_to_ball));
			if(angle_to_ball > 20 && angle_to_ball < 160)
				break;
			if(10.0 * getRobot().getUltrasonic().getDistance() < 2 * CurrentPositionBox.boundaryMargin)
				break;
			Thread.yield();
		}
		
		getRobot().getDifferentialPilot().stop();
	}

	@Override
	public void suppress() {
		suppressed = true;
	}

}
