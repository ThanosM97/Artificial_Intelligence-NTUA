import java.util.Comparator;

public class RouteComparator implements Comparator<Route> {
	public RouteComparator() {}

	@Override
	public int compare(Route o1, Route o2) {
			return (o1.getDistance() < o2.getDistance()) ? -1 : 1;
	}
}
