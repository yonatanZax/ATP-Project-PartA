package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;


/**
 * Representing a searchable 2D maze.
 * Implements iSearchable.
 */
public class SearchableMaze implements ISearchable
{

	private Maze maze;
	private MazeState startState;
	private MazeState goalState;

	/**
	 * Constructor
	 * @param maze A Maze.
	 */
	public SearchableMaze(Maze maze){
		this.maze = maze;
		startState = new MazeState(maze.getStartPosition());
		goalState = new MazeState(maze.getGoalPosition());
	}

/*
	public SearchableMaze(Maze maze){
		neverNull(maze);
		this.maze = maze;
		startState = new MazeState(maze.getStartPosition());
		goalState = new MazeState(maze.getGoalPosition());
	}

	private static <S extends Maze> S neverNull(S notNull) {
		if (notNull == null) {
			throw new NullPointerException();
		}
		return notNull;
	}
*/


	public ArrayList<AState> getAllPossibleStates(AState currentState) {
		if (!(currentState instanceof MazeState) || currentState == null){
			return null;
		}
		ArrayList<AState> list = new ArrayList<AState>();
		MazeState curMazeState = (MazeState)currentState;
		Position curPos = curMazeState.getPosition();
		int curRow = curPos.getRowIndex();
		int curCol = curPos.getColumnIndex();
		int curWeigh = currentState.getWeight();
		for(int x = -1; x <= 1; x++){
			for(int y = -1; y <= 1; y++){
				if (x == 0 && y == 0) //same location
					continue;
				if (x != 0 && y != 0) { // slant
					if (mazeCharCheck(curRow + x,curCol + y)) // checks if its a wall if so wont take it
						if (mazeCharCheck(curRow ,curCol + y)){ // checks if at the same row we have 0
							list.add(new MazeState(curWeigh + 15,new Position(curRow + x, curCol + y, curPos)));
							continue;
						}
						else if (mazeCharCheck(curRow + x,curCol)){ // checks if at the same col we have 0
							list.add(new MazeState(curWeigh + 15,new Position(curRow + x, curCol + y, curPos)));
							continue;
						}
						else{
							continue;
						}
				}
				else if(mazeCharCheck(curRow + x,curCol + y)) // if its not slant, same location and it have '0'
					list.add(new MazeState(curWeigh + 10,new Position(curRow + x, curCol + y, curPos)));
			}
		}
		return list;
	}

	/**
	 * Checks if the given parameters represents a valid cell to move to in the maze.
	 * @param row The Row it checks.
	 * @param col The Column it checks.
	 * @return true given parameters represent a cell open in the maze, false otherwise.
	 */
	private boolean mazeCharCheck(int row, int col){
		try {
			return "E0S".contains(String.valueOf(maze.getCharAt(row, col)));
		}catch (Exception e){
			return false;
		}
	}


	public AState getGoalState() {
		return new MazeState(goalState);
	}

	public AState getStartState() {
		return new MazeState(startState);
	}

}



