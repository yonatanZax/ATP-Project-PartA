package algorithms.search;


/**
 * An Interface of a searching algorithm.
 * The searching algorithms can solve 'ISearchable' problems.
 */
public  interface ISearchingAlgorithm 
{
	/**
	 * Solves the searchable object.
	 * @param searchable A searchable problem.
	 * @return The solution of the problem.
	 */
	public Solution solve(ISearchable searchable) ;

	/**
	 * Gets the name of the algorithm.
	 * @return A String of the name.
	 */
	public String getName();

	/**
	 * Gets the number of nodes the algorithm visited.
	 * @return The number of nodes the algorithm visited.
	 */
	public int getNumberOfNodesEvaluated();

}

