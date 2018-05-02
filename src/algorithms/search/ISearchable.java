package algorithms.search;

import java.util.ArrayList;
import java.util.List;

/**
 * An Interface of a searching problem.
 */
public  interface ISearchable 
{

	/**
	 * Gets all the neighbor states of 'currentState'.
	 * @param currentState The State we want to get his neighbors.
	 * @return A List of all the neighbors of currentState.
	 */
	public ArrayList<AState> getAllPossibleStates(AState currentState) ;

	/**
	 * Gets the GoalState of the searchable problem
	 * @return 'AState' representing the Goal of the searchable problem.
	 */
	public AState getGoalState() ;

	/**
	 * Gets the GoalState of the searchable problem
	 * @return 'AState' representing the Goal of the searchable problem.
	 */
	public AState getStartState() ;


}

