/**
 * Simple class to store a row,col pair
 *
 * @author D. Sheldon
 * @version 05/05/2016
 **/
public class Point {
    /**
     * The row that this Point is at
     **/
    public int row;
    /**
     * The column that this point is at
     **/
    public int col;

    /**
     * Constructor for a point given a row and a column
     *
     * @param row is the row of the point
     * @param col is the column of the point
     **/
    public Point(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean equals(Point p) {
        return (p.row == row && p.col == col);
    }
}