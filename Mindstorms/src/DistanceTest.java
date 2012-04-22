import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import robot.Robot;
import position.DistanceAnalyser;


public class DistanceTest {
	public static void main(String[] args) {
		DifferentialPilot pilot = new DifferentialPilot(40, 15, Motor.A, Motor.B);
		DistanceAnalyser analyser = new DistanceAnalyser();
		pilot.setAcceleration(300);
		Sound.playTone(440, 50);
		
		Robot robot = new Robot();
		robot.getDistance().continuous();
		
		
		while (Button.readButtons() == 0) {
			Delay.msDelay(300);
			int distance = robot.getDistance().getDistance();
			/*if(analyser.addMeasurement(distance))
				robot.kick();*/
			LCD.clear();
			LCD.drawInt(distance, 0, 1);
			LCD.drawInt(analyser.last, 0, 2);
			LCD.drawInt(analyser.lastDiff,0, 3);
			
			
			if (distance < 40){
				pilot.setRotateSpeed(pilot.getRotateMaxSpeed() / 3);
				pilot.rotateLeft();
			} else {
				pilot.forward();
			}
			
			
			
			
			/*
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
			*/
		}
		
	}
}
