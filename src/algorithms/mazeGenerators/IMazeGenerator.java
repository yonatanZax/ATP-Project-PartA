package algorithms.mazeGenerators;

/**
 * An interface to MazeGenerators
 */
public  interface IMazeGenerator 
{
	/**
	 * Creates a Maze and return it.
	 * @param rows  the number of rows the maze will have.
	 * @param cols  the number of columns the maze will have
	 * @return  A Maze
	 */
	public Maze generate(int rows, int cols) ;


	/**   This method measures how long it takes to generate a random maze.
	 *	 @param rows  the number of rows the maze will have.
	 *	 @param cols  the number of columns the maze will have
	 *    @return  the time it took to create a maze with the given size
	 */
	public long measureAlgorithmTimeMillis(int rows, int cols) ;


}

