package algorithms.search;

import java.util.ArrayList;

/**
 * Represents a path that solves the searchable problem.
 */
public class Solution
{
	/* a list of the 'AStates' we need to take to complete the problem in the right order from start to goal.*/
	private ArrayList<AState> aStateList;

	/**
	 * Constructor
	 * @param list The list of steps we need to take.
	 */
	public Solution(ArrayList<AState> list){
		aStateList = new ArrayList<AState>(list);
	}

	/**
	 * Gets the solution as an arrayList.
	 * @return An arrayList with the path.
	 */
	public ArrayList<AState> getSolutionPath(){
		return new ArrayList<AState>(aStateList);
	}


	/**
	 *  Print Format:
	 *	@return
	 *  "Solution path:
	 		1. {3,0}\n
	 		2. {3,1}\n
	 		3. {3,2}\n
	 		4. {3,3}\n
	 		5. {3,4}\n
	 		6. {2,4}\n
	 		7. {1,4}\n"
	 */
	public String toString(){
		int stateCount = 1;
		String s = "Solution path:\n";
		for (AState state:aStateList) {
			s += stateCount + ". ";
			stateCount++;
			s += state.toString() + "\n";
		}
		return s;
	}

}

