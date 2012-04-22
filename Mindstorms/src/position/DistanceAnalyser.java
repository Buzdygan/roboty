package position;

import java.lang.Math.*;

public class DistanceAnalyser {
	
	public int last;
	public int lastDiff;
	
	public static int NORMAL = 0;
	public static int OBJECT_IN_WAY = 1;
	public static int STUCK = 2; 
	
	public DistanceAnalyser()
	{
		last = 0;
		lastDiff = 0;
	}
	
	public int addMeasurement(int dist){
		if (dist == 255)
			return NORMAL;
		int decision = NORMAL;
		int newDiff = dist - last;
		last = dist;
		if(newDiff < 0 && dist < 30 && lastDiff - newDiff > 10)
			decision = OBJECT_IN_WAY;
		if(Math.abs(newDiff) < 3 && Math.abs(lastDiff) < 3)
			decision = STUCK;
		lastDiff = newDiff;
		return decision;
	}
	

}
