package algorithms.search;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Represents the BestFirstSearch algorithm.
 * Inherits BreadthFirstSearch.
 */
public class BestFirstSearch extends BreadthFirstSearch
{



	/**
	 * Constructor that will initialize what it need to solve the searchable problem.
	 */
	public BestFirstSearch(){
		super();
		Comparator<AState> comparator = new Comparator<AState>() {
			@Override
			public int compare(AState o1, AState o2) {
				if (o1.getWeight() < o2.getWeight())
					return -1;
				if(o1.getWeight() > o2.getWeight())
					return 1;

				return 0;
			}
		};
		stateQueue = new PriorityQueue<AState>(comparator);
	}

	@Override
	public Solution solve(ISearchable searchable) {
		return super.solve(searchable);
	}

	public String getName(){
		return "Best First Search";
	}

}

