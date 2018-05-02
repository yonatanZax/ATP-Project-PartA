package algorithms.search;


import algorithms.mazeGenerators.Position;

/**
 * Represents a state in a 2D maze.
 */
public class MazeState extends AState {

	/**
	 * The position of the state.
	 */
	private Position position;

	/*** Constructors ***/
	// Weight is used to allow Priority Queue


	public MazeState(int weight,Position p){
		super(p.toString() ,weight);
		position = new Position(p);
	}

	public MazeState(Position p){
		super(p.toString());
		position = p;
	}

	/**
	 * Copy Constructor.
	 * @param other The MazeState we wish to copy.
	 */


	public MazeState(MazeState other) {
		super(other.toString(), other.getWeight());
		position = new Position(other.position);
	}

	@Override
	public int hashCode() {
		return position.hashCode();
	}

	@Override
	public String toString() {
		return position.toString();
	}

	@Override
	public AState getPredecessor() {
		if(position.getParent() == null)
			return null;
		return new MazeState(position.getParent());
	}

	public Position getPosition() {
		return new Position(position);
	}

}

