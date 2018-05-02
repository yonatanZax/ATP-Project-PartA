package algorithms.mazeGenerators;


/**
 * Represent a location in a 2D dimension of ints
 *
 */
public class Position
{
	private Integer row;
	private Integer col;
	private Position parent;

	/**
	 * Constructor
	 * @param x The row
	 * @param y The column
	 */
	public Position(int x, int y){
		row = x;
		col = y;
		this.parent = null;
	}

	/**
	 * Constructor
	 * @param x The row
	 * @param y The column
	 * @param parent The parent of which we came from
	 */
	public Position(int x, int y, Position parent){
		row = x;
		col = y;
		this.parent = parent;
	}

	/**
	 * Copy Constructor
	 * @param copiedPosition the position you want to copy
	 */
	public Position(Position copiedPosition){
		if(copiedPosition != null) {
			row = copiedPosition.row;
			col = copiedPosition.col;
			parent = copiedPosition.parent;
		}
	}

	/**
	 *     [1][a][1]
	 *     [1][b][1]
	 *     [1][c][1]
	 *     In this example:
	 *     'c' is the Parent of 'b'
	 *     => 'a' is the Opposite of 'b'
	 *     "Opposite from the Parent's side"
	 */
/*
	public Position opposite() {
		if (this.row.compareTo(parent.row) != 0)
			return new Position(this.row + this.row.compareTo(parent.row), this.col, this);
		if (this.col.compareTo(parent.col) != 0)
			return new Position(this.row, this.col + this.col.compareTo(parent.col), this);
		return null;
	}
*/
	/*** Getters ***/

	public Integer getRowIndex() {	return row;	}

	public Integer getColumnIndex() {
		return col;
	}

	public Position getParent() {
		if (parent == null)
			return null;
		return new Position(parent);
	}

	/**
	 * Makes a String that represents the position.
	 * @return A String that looks like this: '{x,y}'
	 */
	@Override
	public String toString(){
		return "{" + row + "," + col + "}";
	}

	//** Equals and HashCode **
	@Override
	public boolean equals(Object other){
		if (!(other instanceof  Position) || other == null)
			return false;
		Position position = (Position)other;
		if (position.getRowIndex() == row && position.getColumnIndex()==col)
			return true;
		return false;
	}

	@Override
	public int hashCode(){
		int upTo = 1009;
		return (int)Math.pow(upTo,2) + upTo * row + col;
	}

}

