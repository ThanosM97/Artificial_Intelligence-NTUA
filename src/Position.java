
public class Position {
	private double x,y;
	
	public Position(double a, double b) {
		this.x = a;
		this.y = b;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	
	public double distanceTo(Position ending) {
		return Heuristic.distance(this.y, this.x, ending.y, ending.x);
	}
	
	public void print() {
		System.out.println("X:" + this.x + " Y:"+ this.y);
	}

	@Override
	public String toString() {
		return "X:" + x + " Y:" + y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
	
	
	
	

}
