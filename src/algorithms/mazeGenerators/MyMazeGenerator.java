package algorithms.mazeGenerators;
import java.util.*;

/**
 * This class creates random Maze with Prim algorithm for generating mazes.
 */
public class MyMazeGenerator extends AMazeGenerator
{

	public MyMazeGenerator(){
	}

	@Override
	public Maze generate(int row, int col){
		if(row < 1 || col < 1)
			return null;

		// Build maze and initialize with only walls ('1')
		StringBuilder s = new StringBuilder(col);
		for (int x = 0; x < col; x++)
			s.append('1');
		char[][] maz = new char[row][col];
		for (int x = 0; x < row; x++) maz[x] = s.toString().toCharArray();

		// Select random point and open as start node
		Position goalPosition = null;
		Position startPosition = new Position((int) (Math.random() * row), 0, null);
		maz[startPosition.getRowIndex()][startPosition.getColumnIndex()] = 'S';

		// Iterate through direct neighbors of node
		ArrayList<Position> frontier = new ArrayList<Position>();
		iterate(maz,startPosition,frontier);

		Position last = null;
		while (!frontier.isEmpty()) {

			// Pick current node at random
			Position cu = frontier.remove((int) (Math.random() * frontier.size()));
			Position op = opposite(cu);
			try {
				// If both node and its opposite are walls
				if (maz[cu.getRowIndex()][cu.getColumnIndex()] == '1') {
					if (maz[op.getRowIndex()][op.getColumnIndex()] == '1') {
						// Open path between the nodes
						maz[cu.getRowIndex()][cu.getColumnIndex()] = '0';
						maz[op.getRowIndex()][op.getColumnIndex()] = '0';
						// Store last node in order to mark it later
						last = op;
						// Iterate through direct neighbors of node, same as earlier
						iterate(maz,op,frontier);
					}
				}
			} catch (Exception e) { // Ignore NullPointer and ArrayIndexOutOfBounds
			}

			// If algorithm has resolved, mark end node
			if (frontier.isEmpty()) {
				maz[last.getRowIndex()][last.getColumnIndex()] = 'E';
				goalPosition = new Position(last.getRowIndex(), last.getColumnIndex(), last.getParent());
			}
		}

		try {
			return new Maze(maz, startPosition, goalPosition);
		}catch (Exception e){
			return null;
		}
	}

	private void iterate(char[][] maz,Position p, ArrayList<Position> frontier){
		for (int x = -1; x <= 1; x++)
			for (int y = -1; y <= 1; y++) {
				if (x == 0 && y == 0 || x != 0 && y != 0)
					continue;
				try {
					if (maz[p.getRowIndex() + x][p.getColumnIndex() + y] == '0') continue;
				} catch (Exception e) { // ignore ArrayIndexOutOfBounds
					continue;
				}
				// Add eligible points to frontier
				frontier.add(new Position(p.getRowIndex() + x, p.getColumnIndex() + y, p));
			}
	}


	public Position opposite(Position p) {
		if(p != null && p.getParent() != null) {
			if (p.getRowIndex().compareTo(p.getParent().getRowIndex()) != 0)
				return new Position(p.getRowIndex() + p.getRowIndex().compareTo(p.getParent().getRowIndex()), p.getColumnIndex(), p);
			if (p.getColumnIndex().compareTo(p.getParent().getColumnIndex()) != 0)
				return new Position(p.getRowIndex(), p.getColumnIndex() + p.getColumnIndex().compareTo(p.getParent().getColumnIndex()), p);
		}
		return null;
	}


}




