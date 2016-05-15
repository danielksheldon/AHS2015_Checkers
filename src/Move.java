import java.util.ArrayList;

/**
 * A class to store and access the information for a complete VALID checkers
 * Move
 * 
 * @author D. Sheldon
 * @version 05/05/2016
 **/
public class Move {
	/**
	 * A List of all the hops in this Move -- 0 is where the the Move started,
	 * and the final hop is where the piece is left
	 **/
	public ArrayList<Point> hops;

	/**
	 * Default Constructor -- only use when you are about to add Points
	 **/
	public Move() {
		hops = new ArrayList<Point>(0);
	}

	/**
	 * Standard Constructor of a Move -- takes in the first jump point
	 * 
	 * @param row
	 *            is the row location of where the move starts
	 * @param col
	 *            is the column location of where the move start
	 **/
	public Move(int row, int col) {
		hops = new ArrayList<Point>(0);
		addPoint(row, col);
	}

	/**
	 * Accessor (getter) getting the last row of the final hop in the Move
	 * 
	 * @return 0..7 the row location of the final hop, -1 if no hops in Move
	 **/
	public int lastRow() {
		if (hops.size() > 0)
			return hops.get(hops.size() - 1).row;
		return -1;
	}

	/**
	 * Accessor (getter) getting the last column of the final hop in the Move
	 * 
	 * @return 0..7 the column location of the final hop, -1 if no hops in Move
	 **/
	public int lastCol() {
		if (hops.size() > 0)
			return hops.get(hops.size() - 1).col;
		return -1;
	}

	/**
	 * Mutator (setter) which adds a new hop into this Move
	 * 
	 * @param row
	 *            is the row location of the new hop
	 * @param col
	 *            is the column location of the new hop
	 **/
	public void addPoint(int row, int col) {
		Point p = new Point(row, col);
		hops.add(p);
	}

	/**
	 * Makes a copy of this Move
	 * 
	 * @return A new Move which is a copy of this Move
	 **/
	public Move copy() {
		Move m = new Move();
		for (Point p : hops)
			m.addPoint(p.row, p.col);
		return m;
	}

	/**
	 * Standard toString
	 * 
	 * @return String representation of this Move -- for example "{(3,2)(3,4)}"
	 **/
	public String toString() {
		String retString = "{";
		for (Point point : hops) {
			retString = retString + "(" + (point.row + 1) + ","
					+ (point.col + 1) + ")";
		}
		return retString + "}";
	}

	boolean startsWith(Move m) {
		for (int i = 0; i < m.hops.size(); i++)
			if (!m.hops.get(i).equals(hops.get(i)))
				return false;
		return true;
	}
	
	boolean equals(Move m) {
		if (m.hops.size() != hops.size())
			return false;
		for (int i = 0; i < m.hops.size(); i++)
			if (!m.hops.get(i).equals(hops.get(i)))
				return false;
		return true;
	}
}
