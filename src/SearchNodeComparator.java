import java.util.Comparator;

public class SearchNodeComparator implements Comparator<SearchNode> {
	public SearchNodeComparator() {}

	@Override
	public int compare(SearchNode o1, SearchNode o2) {
			return (o1.getRealPlusHeuristic() < o2.getRealPlusHeuristic()) ? -1 : 1;
		
	}

}
