import lejos.nxt.Button;
import robot.Robot;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.*;

public class HelloWorld {
	public static void main(String[] args) {
//		Delay.msDelay(1000);
		DifferentialPilot pilot = new DifferentialPilot(40, 15, Motor.A, Motor.B);
//		pilot.setTravelSpeed(100000000);
//		pilot.setAcceleration(800);
//		pilot.forward();
//		Delay.msDelay(500);
//		pilot.stop();
		Sound.playTone(440, 50);
		
		Robot robot = new Robot();
		int direction = 5;
		
		Motor.B.suspendRegulation();
		/*
		int val = Motor.B.getTachoCount();
		LCD.drawInt(val, 0, 0);
		Motor.B.rotate(-60);
		val = Motor.B.getTachoCount();
		LCD.drawInt(val, 0, 1);
		Motor.B.stop();
		LCD.drawInt(val, 0, 2);
		Button.readButtons();
		while (Button.readButtons() == 0) {
			Motor.B.suspendRegulation();
			LCD.drawInt(Motor.B.getTachoCount(), 0, 3);
			LCD.drawInt(Motor.B.getPosition(), 0, 4);
		}
		*/
		
		while (Button.readButtons() == 0) {
			direction = robot.getSeeker().getDirection();
			int[] values = robot.getSeeker().getSensorValues();
			LCD.clear();
			for(int i=0; i<5; ++i) {
				LCD.drawInt(values[i], 0, i);
			}
			if (direction == 0) {
				pilot.stop();
				Sound.playTone(3000, 10);
				robot.kick();
			}
			if (direction < 3) {
				pilot.setRotateSpeed(pilot.getRotateMaxSpeed() / 3);
				pilot.rotateLeft();
				Sound.playTone(440, 10);
			} else if (direction < 4) {
				pilot.setRotateSpeed(pilot.getRotateMaxSpeed() / 6);
				pilot.rotateLeft();
				Sound.playTone(440, 10);
			} else if (direction < 5) {
				pilot.setRotateSpeed(pilot.getRotateMaxSpeed() / 15);
				pilot.rotateLeft();
				Sound.playTone(440, 10);
			} else if (direction > 7) {
				pilot.setRotateSpeed(pilot.getRotateMaxSpeed() / 3);
				pilot.rotateRight();
				Sound.playTone(1500, 10);
			} else if (direction > 6) {
				pilot.setRotateSpeed(pilot.getRotateMaxSpeed() / 6);
				pilot.rotateRight();
				Sound.playTone(1500, 10);
			} else if (direction > 5) {
				pilot.setRotateSpeed(pilot.getRotateMaxSpeed() / 15);
				pilot.rotateRight();
				Sound.playTone(1500, 10);
			} else {
				pilot.setRotateSpeed(pilot.getRotateMaxSpeed() / 15);
				pilot.forward();
			}
		}
		
	}
}