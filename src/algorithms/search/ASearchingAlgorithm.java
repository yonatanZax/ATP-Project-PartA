package algorithms.search;

import java.util.*;

/**
 * An abstract class that represents an algorithm of solving a graph searching problem.
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm
{

	protected int visitedNodes; // keep the number of nodes we visited.
	protected AbstractQueue<AState> stateQueue; // holds the states in a queue that each algorithm can choose what type of queue it needs

	/**
	 * Constructor that will initialize what it need to solve the searchable problem.
	 */
	public ASearchingAlgorithm(){
		visitedNodes = 0;
	}

	/**
	 * Gets the next 'AState' in the queue, while keeping the 'visitedNodes' updated.
	 * @return
	 */
	protected AState popOpenList() {
		visitedNodes++;
		return stateQueue.poll();
	}



	/**
	 * Solves the searchable object.
	 * @param searchable A searchable problem.
	 * @return The solution of the problem.
	 */
	public abstract Solution solve(ISearchable searchable) ;

	protected void clear(){
		visitedNodes = 0;
		stateQueue.clear();
	}


	/**
	 * Gets the number of nodes the algorithm visited.
	 * @return The number of nodes the algorithm visited.
	 */
	public int getNumberOfNodesEvaluated() {
		return visitedNodes;
	}

	/**
	 * Create a solution from a given 'AState'
	 * @param result An 'AState' representing the goalState after the algorithm found it.
	 * @return The solution
	 */
	protected Solution formSolution(AState result) {
		if(result == null)
			return null;
		ArrayList<AState> solutionList = new ArrayList<AState>();
		solutionList.add(result);
		AState parent = result.getPredecessor();
		while(parent != null){
			solutionList.add(parent);
			parent = parent.getPredecessor();
		}
		// Reverse list
		Collections.reverse(solutionList);
		return new Solution(solutionList);
	}
}

