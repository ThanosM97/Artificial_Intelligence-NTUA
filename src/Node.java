import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Node extends Position {
	private int id;
	private String name;
	
	public Node(double x, double y, int identity, String avenue) {
		super(x,y);
		this.id = identity;
		this.name = avenue;
	}

	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void print() {
		System.out.println("X:" + this.getX() + " Y:" + this.getY() + " id:" + this.id + " name:" + this.name);
	}

	@Override
	public String toString() {
		return this.getX() + "," + this.getY() + "," + 0;
	}

	public static ArrayList<Node> read(String csvFile) {
		BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        Node aNode = null;
        ArrayList<Node> nodes = new ArrayList<Node>();

        try {
            br = new BufferedReader(new FileReader(csvFile));
            line = br.readLine();
            while ((line = br.readLine()) != null) {

                String[] values = line.split(cvsSplitBy);
                double a = Double.parseDouble(values[0]);
                double b = Double.parseDouble(values[1]);
                int c = Integer.parseInt(values[2]);
                String d = (values.length == 4) ? values[3] : " ";	

                aNode = new Node(a,b,c,d);
                nodes.add(aNode);
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return nodes;
	}
	
	public Node findClosestNode(ArrayList<Node> nodes) {
		double minDistance = -1;
		double currentDistance;
		Node closestNode = null;
		for (Node currentNode : nodes) {
			currentDistance = this.distanceTo(currentNode);
			if (currentDistance < minDistance || minDistance == -1) {
				minDistance = currentDistance;
				closestNode = currentNode;
			}
		}
		return closestNode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
