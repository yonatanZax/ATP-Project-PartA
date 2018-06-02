package algorithms.search;


import algorithms.mazeGenerators.Position;

import java.io.Serializable;

/**
 * Represents a state in a 2D maze.
 */
public class MazeState extends AState implements Serializable {

	/**
	 * The position of the state.
	 */
	private Position position;

	/*** Constructors ***/
	// Weight is used to allow Priority Queue


	public MazeState(int weight,Position p, MazeState parent){
		super(p.toString() , weight, parent);
		position = new Position(p);
	}

	public MazeState(Position p,MazeState parent){
		super(p.toString(),parent);
		position = p;
	}

	/**
	 * Copy Constructor.
	 * @param other The MazeState we wish to copy.
	 */
	public MazeState(MazeState other) {
		super(other.toString(), other.getWeight(),other.getPredecessor());
		position = new Position(other.position);
	}

	@Override
	public int hashCode() {
		return position.hashCode();
	}

	public Position getPosition() {
		return new Position(position);
	}

}

