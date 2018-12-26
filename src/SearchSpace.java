import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

public class SearchSpace {

	private TreeSet<SearchNode> frontSpace = new TreeSet<SearchNode>(new SearchNodeComparator());
	private HashMap<Position, ArrayList<Node>> streetsMap = new HashMap<Position, ArrayList<Node>>();
	private Node goal;
	
	public SearchSpace() {}
	
	public TreeSet<SearchNode> getFrontSpace() {
		return frontSpace;
	}
	
	public SearchNode getHead() {		
		return frontSpace.pollFirst();
	}
	
	public void initSearchSpace(Node start, HashMap<Position, ArrayList<Node>> map, Node end) {
		this.streetsMap = map;
		this.goal = end;
		ArrayList<Node> nodePath = new ArrayList<Node>();
		SearchNode startSearchNode = new SearchNode(start, nodePath, 0);
		frontSpace.add(startSearchNode);
	}
	
	public void addChildren(SearchNode parent, ArrayList<Node> closedSet) {
		Position key = new Position(parent.getCurrentNode().getX(), parent.getCurrentNode().getY());
		for (Node child : streetsMap.get(key)) {
			if (!(closedSet.contains(child))) {				
				ArrayList<Node> newPath = new ArrayList<Node>();
				newPath.addAll(parent.getNodePath());
				newPath.add(parent.getCurrentNode()); 
				SearchNode childSearchNode = new SearchNode(child,newPath, parent.getPathDistance()+ parent.getCurrentNode().distanceTo(child));				
				childSearchNode.initHeuristicValue(this.goal);
				Iterator<SearchNode> it = frontSpace.iterator();
				boolean flag = true;
				ArrayList<SearchNode> removeThis = new ArrayList<SearchNode>();
				while (it.hasNext()) {
					SearchNode log = it.next();
					if (log.equals(childSearchNode) && childSearchNode.getRealPlusHeuristic() > log.getRealPlusHeuristic() ) {
						flag = false;
					}
					if (log.equals(childSearchNode) && childSearchNode.getRealPlusHeuristic() < log.getRealPlusHeuristic() ) {
						removeThis.add(log);
					}
				}
				if (flag && removeThis.isEmpty()) frontSpace.add(childSearchNode);
				else if (!removeThis.isEmpty()) {
					frontSpace.removeAll(removeThis);
					frontSpace.add(childSearchNode);
				}			
			}
		}
	}
	
	public boolean exists(SearchNode head) {
		Iterator<SearchNode> iter = frontSpace.iterator();
		while (iter.hasNext()) {
			SearchNode log = iter.next();
			if (log.equals(head)) return true;
		}
		return false;
	}
}
	

