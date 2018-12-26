import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class Taxigation {
	
	static ArrayList<Node> closedSet = new ArrayList<Node>();
	static TreeSet<Route> routes = new TreeSet<Route>(new RouteComparator());

	static void createKML() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("routes.kml", "utf-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<kml xmlns=\"http://earth.google.com/kml/2.1\">\r\n" + 
				"<Document>\r\n" + 
				"<name>Taxi Routes</name>\r\n" + 
				"<Style id=\"green\">\r\n" + 
				"<LineStyle>\r\n" + 
				"<color>ff009900</color>\r\n" + 
				"<width>4</width>\r\n" + 
				"</LineStyle>\r\n" + 
				"</Style>\r\n" + 
				"<Style id=\"red\">\r\n" + 
				"<LineStyle>\r\n" + 
				"<color>ff0000ff</color>\r\n" + 
				"<width>4</width>\r\n" + 
				"</LineStyle>\r\n" + 
				"</Style>");
		double minDistance = routes.first().getDistance();
		int prevId = -1;
		for (Route route : routes) {
			if (route.getDistance() == minDistance) {
				if (route.getTaxiId() != prevId) {
					System.out.println("-----------------------------------");
					System.out.println("Taxi " + route.getTaxiId() + " is near to the client.");
					System.out.println("Estimated distance is "+ route.getDistance()+" km");
					System.out.println("-----------------------------------");
					prevId = route.getTaxiId();
				}
				writer.println("<Placemark>\r\n" + 
						"<name>Taxi "+ route.getTaxiId() +"</name>\r\n" + 
						"<styleUrl>#green</styleUrl>\r\n" + 
						"<LineString>\r\n" + 
						"<altitudeMode>relative</altitudeMode>\r\n" + 
						"<coordinates>");
			} else {
				writer.println("<Placemark>\r\n" + 
						"<name>Taxi" + route.getTaxiId() +"</name>\r\n" + 
						"<styleUrl>#red</styleUrl>\r\n" + 
						"<LineString>\r\n" + 
						"<altitudeMode>relative</altitudeMode>\r\n" + 
						"<coordinates>");
			}
			for (Node r : route.getRoute()) {
				writer.println(r);
			}
			writer.println("</coordinates>\r\n" + 
					"</LineString>\r\n" + 
					"</Placemark>");
		}
		writer.println("</Document>\r\n" + 
				"</kml>");
		writer.close();  
	}
	
	static HashMap<Position, ArrayList<Node>> initMap (ArrayList<Node> nodes){
		HashMap<Position, ArrayList<Node>> map = new HashMap<Position, ArrayList<Node>>();
		
		for (int i=0; i<nodes.size(); i++) {
			Position index= new Position(nodes.get(i).getX(), nodes.get(i).getY());
			if (map.get(index) == null) {
				ArrayList<Node> myList = new ArrayList<Node>();
				map.put(index, myList);				
			}
			if (i>0 && nodes.get(i-1).getId() == nodes.get(i).getId()) {
				map.get(index).add(nodes.get(i-1));				
			}
			if (i<nodes.size()-1 && nodes.get(i+1).getId() == nodes.get(i).getId()) {
				map.get(index).add(nodes.get(i+1));
			}
		}		
		return map;
	}
	
	public static void main(String[] args)  {
		
		if (args.length != 3) {
            System.out.println("Usage: Taxigation <path/to/file/nodes.csv> <path/to/file/taxis.csv> <path/to/file/client.csv>");
            System.out.println("Input files must either be inside the project's directory or be given by their location path.");
            System.exit(2);
        }

        String nodesFile = new String(args[0]);
        String taxisFile = new String(args[1]);
        String clientFile = new String(args[2]);
		
		HashMap<Position, ArrayList<Node>> map = new HashMap<Position, ArrayList<Node>>();
		
		Client person = Client.read(clientFile);
		ArrayList<Taxi> taxis = Taxi.read(taxisFile);
		ArrayList<Node> nodes = Node.read(nodesFile);
		
		Node clientNode = new Node(person.getX(), person.getY(),0, "");
		Node goal = clientNode.findClosestNode(nodes);
		map = initMap(nodes);
		
		System.out.print("Finding nearest taxi(s) to the client");
		
		for (Taxi taxi: taxis) {
			System.out.print(".");
			Node taxiNode = null;
			taxiNode = new Node(taxi.getX(), taxi.getY(), 0, "");
			Node start = taxiNode.findClosestNode(nodes);
			
			SearchSpace fieldSpace = new SearchSpace();
			fieldSpace.initSearchSpace(start, map, goal);
			
			double minDistance = -1;
			while(true) {
				SearchNode head = fieldSpace.getHead();
			
				if (head == null) break;
				if (head.getCurrentNode() == goal ) {
					if (minDistance == -1 || (head.getPathDistance() == minDistance)) { 
						ArrayList<Node> nodesRoute = new ArrayList<Node>();	
						nodesRoute.add(taxiNode);
						nodesRoute.addAll(head.getNodePath());
						nodesRoute.add(head.getCurrentNode());
						nodesRoute.add(clientNode);
						Route route = new Route(nodesRoute,head.getPathDistance() + clientNode.distanceTo(goal) + taxiNode.distanceTo(start), taxi.getId() );
						routes.add(route);
						minDistance = head.getPathDistance();	
					} else break;
				} 
				else if (minDistance != -1 && head.getPathDistance() > minDistance) break;
				else {
					if (!closedSet.contains(head.getCurrentNode())){
						if (!fieldSpace.exists(head)) closedSet.add(head.getCurrentNode());					
						fieldSpace.addChildren(head, closedSet);
					} 
				} 
				
			}
			closedSet.clear();
		} 
		System.out.println();
		createKML();
		System.out.println("Use the routes.kml file to see all available routes on a map.");
		System.out.println("(Shortest routes' lines will appear green colored on the map)");
	}
}

