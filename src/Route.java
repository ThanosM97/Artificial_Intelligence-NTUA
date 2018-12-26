import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Route {
	
	private ArrayList<Node> route;
	private double distance;
	private int taxiId;
	
	public Route(ArrayList<Node> nodesRoute, double nodesDistance,int taxi) {
		this.route = nodesRoute;
		this.distance = nodesDistance;
		this.taxiId = taxi;
	}
		
	public double getDistance() {
		return BigDecimal.valueOf(distance).setScale(3,RoundingMode.HALF_UP).doubleValue();
	}

	public ArrayList<Node> getRoute() {
		return route;
	}

	public int getTaxiId() {
		return taxiId;
	}

}