package algorithms.mazeGenerators;


/**    The maze is represented as a 2D char array.
 *     Zero ('0') = Pass
 *     One ('1') = Wall
 *     Start ('S') = The starting position
 *     End ('E') = The "Goal" position
 */
public class Maze
{

	private char[][] map; //the actual maze
	private Position startPostion; // representing the start Position
	private Position goalPosition; // representing the exit Position


	/**
	 * A Constructor to create a new maze
	 * @param map represents the 2D maze. can't be null
	 * @param sp represent the start Position, can't be null, need to match the 'S' in the map
	 * @param gp represent the exit Position, can't be null, need to match the 'S' in the map
	 * @throws Exception if a given parameter isn't valid it will throw an 'Exception' with the problem message.
	 */
	public Maze(char[][] map, Position sp, Position gp) throws Exception {
		if(map == null || sp == null || gp == null) // check for null parameters
			throw new Exception("Maze error: can't initial Maze with null arguments");
		// check if the map really have 'E' and 'S' at goalPosition and StartPosition and the map only contains '1', '0', 'S' and 'E'.
		if (!checkPositionsCorrectness(map,sp,gp) || !checkMapCorrectness(map))
			throw new Exception("Maze error: can't initial Maze with wrong positions arguments");
	    this.map = map;
	    startPostion = sp;
	    goalPosition = gp;
	}

	/*** Getters ***/
	
	public Position getStartPosition() {
        return new Position(startPostion);
	}


	public Position getGoalPosition(){
		return new Position(goalPosition);
	}


	/**
	 *     Print method: simply prints the char 2D array values
	 *     Example: A random maze (6 by 6)
	 *
	 *     			011101
	 *     			S00101
	 *     			010001
	 *     			001111
	 *     			100E00
	 *     			110110
	 */
	public void print() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++)
                System.out.print(map[i][j]);
            System.out.println();
        }
	}



	/**
	 * 	Gets what is in the map at the location given in the parameters.
	 * 	@param row The row you want to check.
	 * 	@param col The column you want to check.
	 * 	@return Returns the char, if the parameters are not in the map will return '\0' .
	 */
	public char getCharAt(int row, int col){
		if ((row < map.length && col < map[0].length) && row >= 0 && col >= 0){
			return map[row][col];
		}
		return '\0';
	}


	/**
	 * Check if the map really have 'E' and 'S' at goalPosition and StartPosition.
	 * @param map the map it checks.
	 * @param sp the start Position.
	 * @param gp the Exit Postion.
	 * @return true if it really have 'E' and 'S' at the spesified locations, else false.
	 */
	private boolean checkPositionsCorrectness(char[][] map, Position sp, Position gp) {
		try{
			int spRow = sp.getRowIndex();
			int spCol = sp.getColumnIndex();
			int gpRow = gp.getRowIndex();
			int gpCol = gp.getColumnIndex();
			if(map[spRow][spCol] == 'S' && map[gpRow][gpCol] == 'E')
				return true;
		}catch(Exception e){
			return false;
		}
		return false;
	}

	/**
	 * Checks the given map contains only: '1', '0', 'S' and 'E'.
	 * @param map The map you want to check.
	 * @return true if the map comstains only '1', '0', 'S' and 'E',
	 * 			false if any cell contains something else.
	 */
	private boolean checkMapCorrectness(char[][] map) {
		for(int i = 0; i < map.length; i ++){
			for(int j = 0; j < map[i].length; j ++){
				if (map[i][j] != 'E' && map[i][j] != 'S' && map[i][j] != '1' && map[i][j] != '0')
					return false;
			}
		}
		return true;
	}

}

