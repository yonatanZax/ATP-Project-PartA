package algorithms.mazeGenerators;

import java.util.Random;


public class SimpleMazeGenerator extends AMazeGenerator
{
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public SimpleMazeGenerator(){
		super();
	}

	@Override
	public Maze generate(int col, int row) {
		if(row < 1 || col < 1)
			return null;

		char[][] maz = new char[row][col];
		// Init maze ( 2D char array ) with random '0's and '1's
		for (int i = 0; i < row; i++) maz[i] = randomBinnaryString(col).toCharArray();

		// Select random point and open as start node
		Random rg = new Random();
		int x = rg.nextInt(row);
		// Avoid the Random of x = 0 / 1 / row-1
		if (x > row / 2){ x -= 2; }
		else{ x += 2; }
		// Set startPosition,goalPosition
		Position startPosition = new Position(x, 0);
		Position goalPosition = createGoalPosition(maz,startPosition);

		maz[startPosition.getRowIndex()][startPosition.getColumnIndex()] = 'S';
		maz[goalPosition.getRowIndex()][goalPosition.getColumnIndex()] = 'E';
		try {
			return new Maze(maz, startPosition, goalPosition);
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
	}


	/**
	 *     This method receives char[][] "maz" and a startPosition
	 *     It builds a random path filled with '0's
	 *     Returns the goalPosition of this path
	 * @return
	 * @param maz
	 * @param startPosition
	 */
	private static Position createGoalPosition(char[][] maz , Position startPosition){
		Position goalPosition = null;
		int currentRow = startPosition.getRowIndex();
		int currentCol = 1;
		int maxRow = maz.length -2;
		int maxCol = maz[0].length -2;

		// Checks if path runs Up or Down
		int direction =1; // "Up-Right"
		if (currentRow>(maxRow/2))
			direction = -1; // "Down-Right"

		/* Building stairs like:
			[1][1][1][1][1][1]
			[S][0][1][1][1][1]
			[1][0][0][1][1][1]
			[1][1][0][0][1][1]
			[1][1][1][E][1][1]
		*/

		while(currentCol<=maxCol && currentRow<=maxRow && currentRow>=2){
			maz[currentRow][currentCol] = '0';
			currentRow = currentRow+direction;
			maz[currentRow][currentCol] = '0';
			currentCol++;
		}

		goalPosition = new Position(currentRow,currentCol);
		maz[goalPosition.getRowIndex()][goalPosition.getColumnIndex()] = 'E';
		return goalPosition;
	}



	/**
	 * <!-- begin-user-doc -->
	 *     This method receives a number "m"
	 *     It builds a random binary number of '0's and '1's
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	private static String randomBinnaryString(int m) {
		Random rg = new Random();
		String binaryString = Integer.toString(rg.nextInt(2));

		while (binaryString.length()<m)
			binaryString+=Integer.toString(rg.nextInt(2));

		return binaryString;
	}

}

