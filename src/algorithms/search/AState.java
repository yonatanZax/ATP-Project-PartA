package algorithms.search;

/**
 * An abstract class representing a state in a searchable problem.
 */
public abstract class AState
{
	// Weight is used to allow Priority Queue
	private int weight;
	private String stateString;
	private AState parent;

	/**
	 * Constructor with no use of weight, sets the weight as 0.
	 */
	public AState(String s,AState p){
		weight = 0;
		stateString = s;
		parent = p;
	}

	/**
	 * 	/**
	 * Constructor with the use of weight, sets the weight as w.
	 * @param w The cost of moving from a neighbor state to this state.
	 */
	public AState(String s,int w,AState p){
		weight = w;
		stateString = s;
		parent = p;
	}

	/**
	 * Compare an object to 'this' AState.
	 * @param aState The object we want to compare with.
	 * @return true if they represents the same state, false otherwise.
	 */
	public boolean equals(Object aState) {
		if (aState != null && (aState instanceof  AState)) {
			return stateString.equals(aState.toString());
		}
		return false;
	}

	/**
	 * Returns a unique int representing this AState in the given problem.
	 */
	public int hashCode() {
		return stateString.hashCode();
	}

	/**
	 * Makes a String representing this state.
	 * @return A String representing this state.
	 */
	public String toString(){
		return stateString;
	}

	/**
	 * Gets the state which we got to this state from.
	 * @return The 'AState' we got to this state from.
	 */
	public AState getPredecessor(){
		return  parent;
	}

	/**
	 * Gets the cost of the moving to this state.
	 * @return weight.
	 */
	public int getWeight() {
		return weight;
	}
}

