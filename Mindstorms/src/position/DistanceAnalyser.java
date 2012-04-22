package position;

public class DistanceAnalyser {
	
	public int last;
	public int lastDiff;
	
	public DistanceAnalyser()
	{
		last = 0;
		lastDiff = 0;
	}
	
	public boolean addMeasurement(int dist){
		/* returns true if thinks that object has appeared */
		int newDiff = dist - last;
		last = dist;
		if(newDiff < 0 && dist < 30 && lastDiff - newDiff > 10)
			return true;
		else return false;
	}
	

}
