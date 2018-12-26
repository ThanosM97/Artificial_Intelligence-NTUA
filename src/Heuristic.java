/* The formula we are going to use in order to find the distance
 * between two positions is the Haversine Formula. The implementation 
 * for this in Java was found at the following link: 
 * https://rosettacode.org/wiki/Haversine_formula#Java
 */
import java.math.BigDecimal;
import java.math.RoundingMode;


public class Heuristic {
		
	    public static final double R = 6372.8; // In kilometers
	    public static double distance(double lat1, double lon1, double lat2, double lon2) {
	        double dLat = Math.toRadians(lat2 - lat1);
	        double dLon = Math.toRadians(lon2 - lon1);
	        lat1 = Math.toRadians(lat1);
	        lat2 = Math.toRadians(lat2);
	 
	        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
	        double c = 2 * Math.asin(Math.sqrt(a));
	        return BigDecimal.valueOf(R * c).setScale(3,RoundingMode.HALF_UP).doubleValue();
	    }
}
