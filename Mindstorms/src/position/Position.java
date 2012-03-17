package position;


public class Position {

	private Complex coordinates;
	private Complex rotation;  // rotation.getNorm() == 1;

	public Position(Complex coordinates, Complex rotation) {
		super();
		this.coordinates = coordinates;
		this.rotation = rotation;
	}
	
	public Position() {
		this(new Complex(), new Complex(1, 0));
	}
	
	public Complex getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(Complex coordinates) {
		this.coordinates = coordinates;
	}
	public Complex getRotation() {
		return rotation;
	}

	public void setRotation(Complex rotation) {
		this.rotation = rotation;
	}

	public void update(double alpha, double distance) {
		coordinates = coordinates.add(rotation.mul(Complex.fromPolar(distance, Math.PI / 2 + alpha / 2)));
		
		rotation = rotation.mul(Complex.fromPolar(1, alpha));
		rotation = rotation.div(rotation.getNorm());
	}
	
}
