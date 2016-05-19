/**
 * A class of helpful functions to write an AI for the game of Checkers. To be
 * used with the CheckerAI interface
 *
 * @author Daniel Sheldon
 **/

import java.util.ArrayList;

public class AIHelpers {


    static final int RED = 1;
    static final int BLACK = -1;
    static final int EMPTY = 0;

    static public int[][] startBoard = {
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {-1, 0, -1, 0, -1, 0, -1, 0},
            {0, -1, 0, -1, 0, -1, 0, -1},
            {-1, 0, -1, 0, -1, 0, -1, 0}
    };

    static public int[][] kingBoard = {{0, 0, -2, 0, -2, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 2, 0, 2, 0, 2, 0, 2}};

    static public int[][] jumpBoard = {{1, 0, 1, 0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0, 0, 0, 1}, {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 0, 0, -1, 0, 1, 0, 0}, {0, 0, 1, 0, 0, 0, 0, 0},
            {0, -1, 0, -1, 0, -1, 0, -1}, {-1, 0, -1, 0, -1, 0, -1, 0},
            {0, -1, 0, -1, 0, -1, 0, -1}};

    static public int[][] bigBoard = {{1, 0, 1, 0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0, 0, 0, 1}, {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, -1, 0, 1, 0, -1}, {-1, 0, -1, 0, -1, 0, -1, 0},
            {0, -1, 0, 0, 0, -1, 0, -1}};

    static public int[][] oddBoard = {
            {1, 0, 1, 0, 1, 0, 1, 0,},
            {0, 0, 0, 1, 0, 0, 0, 1,},
            {1, 0, 0, 0, 1, 0, 1, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0,},
            {0, -1, 0, -1, 0, -1, 0, -1,},
            {0, 0, 2, 0, -1, 0, -1, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0,},
    };


    static public int[][] endBoard3 = {{0, 0, 0, 0, 0, 0, -2, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 2, 0},
            {0, 0, 0, 0, 0, 2, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}};

    static public int[][] endBoard2 = {
            {0, 0, 0, 0, 0, 0, -2, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 2, 0},
            {0, 0, 0, 0, 0, 2, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}};

    static public int[][] endBoard = {
            {0, 0, 0, 0, -2, 0, 2, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 2, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}};

    static public int[][] tripCheck = {
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 1, 0, 0, 0, 0 },
            { 0, 0, 0, 0, -1, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, -1, 0 },
            { 0, 0, 0, -1, 0, -1, 0, 0 },
            {-1, 0, -1, 0, -1, 0, -1, 0 },
            { 0, -1, 0, 0, 0, 0, 0, -1 }
    };


    private static ArrayList<Move> lastMoves;
    private static int lastTurn;
    private static int[][] lastBoard;

    /**
     * A method to retrieve all the valid moves for a particular checkers player
     * on a particular board.
     *
     * @param board is a grid of all pieces on a checkerboard, 0=empty, 1=RED,
     *              2=RED KING, -1=BLACK, -2=BLACK KING
     * @param turn  is the color of the player to get the valid moves for 1=RED,
     *              -1=BLACK
     * @return ArrayList<Move> with *all* of the valid moves for this player on
     * this board
     **/
    public static ArrayList<Move> getAllMoves(int[][] board, int turn) {
        if (turn != lastTurn || !sameBoard (board, lastBoard)) {
            ArrayList<Move> retMoves = new ArrayList<Move>(0);
            for (int r = 0; r < board.length; r++) {
                for (int c = 0; c < board[r].length; c++) {
                    Move m = new Move(r, c);
                    getJumps(retMoves, m, board, turn);
                }
            }
            if (retMoves.size() == 0)
                for (int r = 0; r < board.length; r++) {
                    for (int c = 0; c < board[r].length; c++) {
                        Move m = new Move(r, c);
                        getMoves(retMoves, m, board, turn);
                    }
                }
            lastTurn = turn;
            lastBoard = board;
            lastMoves = retMoves;
        }
        return lastMoves;
    }

    /**
     *
     */
    private static boolean sameBoard (int [][] oldBoard, int[][] newBoard) {
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
                if (oldBoard[r][c] != newBoard[r][c])
                    return false;
        return true;
    }
    /**
     * A simple validation helper function
     *
     * @param row is the row to validate (0..7)
     * @param col is the col to validate (0..7)
     * @return true if row and col are both within 0..7, false otherwise
     **/
    private static boolean onBoard(int row, int col) {
        return (row >= 0 && row <= 7 && col >= 0 && col <= 7);
    }

    /**
     * Function to check if a cell value is a King
     *
     * @param cell is the value of a board spot, -2..2
     * @return true if cell is equal to 2 or -2, false otherwise
     **/
    private static boolean isAKing(int cell) {
        return (cell == -2 || cell == 2);
    }

    /**
     * @param moves
     * @param m
     * @param board
     * @param toRow
     * @param toCol
     * @param turn
     * @return true if there is a valid jump from here, false otherwise
     */
    private static void addJump(ArrayList<Move> moves, Move m,
                                   int[][] board, int toRow, int toCol, int turn) {
        int row = m.lastRow();
        int col = m.lastCol();
        if (onBoard(toRow, toCol)) {
            if (board[toRow][toCol] == EMPTY) {
                if ((board[(row + toRow) / 2][(col + toCol) / 2] == -turn || board[(row + toRow) / 2][(col + toCol) / 2] == -2
                        * turn)) {

                    Move newMove = m.copy();
                    newMove.addPoint(toRow, toCol);
                    Move oneAhead = new Move (row, col);
                    oneAhead.addPoint(toRow, toCol);

                    int[][] jumpBoard = makeMove(board, oneAhead, turn);
                    if (!getJumps(moves, newMove, jumpBoard, turn)) {
                        moves.add(newMove);
                    }
                }
            }
        }
    }

    private static boolean getJumps(ArrayList<Move> moves, Move curMove,
                                    int[][] board, int turn) {
        int rowDelta = turn;
        int row = curMove.lastRow();
        int col = curMove.lastCol();
        int numMoves = moves.size();
        // if the piece at this r,c is the right color
        if (turn > 0 && board[row][col] > 0 || turn < 0 && board[row][col] < 0) {
            // single jumps
            addJump(moves, curMove, board, row + 2 * rowDelta,
                    col + 2, turn);
            addJump(moves, curMove, board, row + 2 * rowDelta,
                    col - 2, turn);
            if (isAKing(board[row][col])) {
                // backwards jumps
                addJump(moves, curMove, board, row - 2 * rowDelta,
                        col - 2, turn);
                addJump(moves, curMove, board, row - 2 * rowDelta,
                        col + 2, turn);
            }
        }
        return moves.size() > numMoves;
    }

    private static void addMove(ArrayList<Move> moves, Move curMove,
                                int[][] board, int toRow, int toCol, int turn) {
        if (onBoard(toRow, toCol) && board[toRow][toCol] == EMPTY) {
            Move newMove = curMove.copy();
            newMove.addPoint(toRow, toCol);
            moves.add(newMove);
        }
    }

    private static void getMoves(ArrayList<Move> moves, Move curMove,
                                 int[][] board, int turn) {
        int rowDelta = turn;
        int row = curMove.lastRow();
        int col = curMove.lastCol();
        // if the piece at this r,c is the right color
        if (turn > 0 && board[row][col] > 0 || turn < 0 && board[row][col] < 0) {
            // single hops
            addMove(moves, curMove, board, row + rowDelta, col + 1, turn);
            addMove(moves, curMove, board, row + rowDelta, col - 1, turn);
            if (isAKing(board[row][col])) {
                // "backwards" hops
                addMove(moves, curMove, board, row - rowDelta, col + 1, turn);
                addMove(moves, curMove, board, row - rowDelta, col - 1, turn);
            }
        }
    }

    /**
     * Method to make a VALID Move, m on a board for a given players turn
     *
     * @param board is a grid of all pieces on a checkerboard, 0=empty, 1=RED,
     *              2=RED KING, -1=BLACK, -2=BLACK KING
     * @param m     is a valid Move for the player represented by turn
     * @param turn  is the color of the player to get the valid moves for 1=RED,
     *              -1=BLACK
     * @return A new board with the Move made -- all jumped pieces removed, and
     * all new Kings upgraded
     **/
    static public int[][] makeMove(int[][] board, Move m, int turn) {
        int[][] newBoard = new int[8][8];
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
                newBoard[r][c] = board[r][c];
        Point lastH = m.hops.get(0);
        int piece = newBoard[lastH.row][lastH.col];
        for (Point p : m.hops) {
            if (turn == RED && p.row == 7)
                piece = 2;
            if (turn == BLACK && p.row == 0)
                piece = -2;
            newBoard[p.row][p.col] = piece;
            newBoard[lastH.row][lastH.col] = 0;
            if (Math.abs(lastH.row - p.row) == 2)
                newBoard[(lastH.row + p.row) / 2][(lastH.col + p.col) / 2] = 0;
            lastH = p;
        }

        return newBoard;
    }
}

