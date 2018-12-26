import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class SearchNode {

	private ArrayList<Node> nodePath ;
	private Node currentNode;
	private double pathDistance ;
	private double heuristicValue;
	
	public SearchNode(Node curNode, ArrayList<Node> currentPath, double currentDistance ) {
		this.nodePath = currentPath;
		this.pathDistance = currentDistance;
		this.currentNode = curNode;
	}

	public ArrayList<Node> getNodePath() {
		return nodePath;
	}

	public double getPathDistance() {
		return pathDistance;
	}

	public void initHeuristicValue(Node goal) {
		this.heuristicValue =  this.currentNode.distanceTo(goal);
	}
	
	public double getRealPlusHeuristic() {
		return (BigDecimal.valueOf(this.heuristicValue + this.pathDistance).setScale(3,RoundingMode.HALF_UP).doubleValue());
	}

	public Node getCurrentNode() {
		return this.currentNode;
	}

	@Override
	public String toString() {
		return "searchNode currentNode=" + currentNode + "  pathdistance: "+ pathDistance+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currentNode == null) ? 0 : currentNode.hashCode());
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
		SearchNode other = (SearchNode) obj;
		if (currentNode == null) {
			if (other.currentNode != null)
				return false;
		} else if (currentNode.getX()!=other.getCurrentNode().getX())
			return false;
		else if (currentNode.getY()!= other.getCurrentNode().getY())
			return false;
		return true;
	}
	

}
