package algorithms.mazeGenerators;

/**
 * An abstract class that represents a generator for mazes.
 * each class the inherit this class will need to implement the method 'generate' with an algorithm that generate a maze.
 */
public abstract class AMazeGenerator implements IMazeGenerator
{

	public AMazeGenerator(){
	}

	/**
	 * Creates a Maze and return it.
	 * @param rows  the number of rows the maze will have.
	 * @param cols  the number of columns the maze will have
	 * @return  A Maze
	 */
	public abstract Maze generate(int rows, int cols) ;


	/**   This method measures how long it takes to generate a random maze.
	 *	 @param rows  the number of rows the maze will have.
	 *	 @param cols  the number of columns the maze will have
	 *    @return  the time it took to create a maze with the given size
	 */
	public long measureAlgorithmTimeMillis(int rows, int cols) {
		long start = System.currentTimeMillis();
		generate(rows, cols);
		long finished = System.currentTimeMillis();
		return finished - start;
	}

}

