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
		DifferentialPilot pilot = new DifferentialPilot(40, 15, Motor.B, Motor.C);
		DistanceAnalyser analyser = new DistanceAnalyser();
		pilot.setAcceleration(400);
		Sound.playTone(440, 50);
		
		Robot robot = new Robot();
		robot.getDistance().continuous();
		
		
		while (Button.readButtons() == 0) {
			Delay.msDelay(200);
			int distance = robot.getDistance().getDistance();
			int decision = analyser.addMeasurement(distance);
			LCD.clear();
			LCD.drawInt(distance, 0, 1);
			LCD.drawInt(analyser.last, 0, 2);
			LCD.drawInt(analyser.lastDiff,0, 3);
			LCD.drawInt(decision, 0, 4);
			
			if (decision == DistanceAnalyser.OBJECT_IN_WAY)
				robot.kick();
			if (decision == DistanceAnalyser.STUCK){
				pilot.backward();
				Delay.msDelay(600);
				pilot.setRotateSpeed(pilot.getRotateMaxSpeed() / 3);
				pilot.rotateLeft();
				Delay.msDelay(800);
			}
			if (decision == DistanceAnalyser.NORMAL){
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
