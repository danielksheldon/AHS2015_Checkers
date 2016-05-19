/**
 * Created by dsheldon on 5/19/16.
 */
public class Board {
    int r01;
    int r23;
    int r45;
    int r67;


    public Board (int a, int b, int c, int d) {
        r01 = a;
        r23 = b;
        r45 = c;
        r67 = d;
    }

    public Board (int [][] board) {
        int num = 1;
        for (int r = 0; r <= 1; r++) {
            for (int c = (r + 1) % 2; c < 8; c = c + 2) {
                r01 += getVal (board[r][c])*num;
                r23 += getVal (board[r+2][c])*num;
                r45 += getVal (board[r+4][c])*num;
                r67 += getVal (board[r+6][c])*num;
                num *= 8;
            }
        }
    }


    static private int getVal (int cell) {
        if (cell == 0)
            return 0;
        if (cell == 1)
            return 1;
        if (cell == 2)
            return 3;
        if (cell == -1)
            return 5;
        if (cell == -2)
            return 7;
        return 0;
    }
    static private void setPiece (int[][] board, int r, int c, int val) {
         if ((val&1) == 1) { // 1 == PIECES HERE, 0 = NO Piece
            board[r][c] = 1;
            if ((val&2) == 2) // 0 = REG, 1 = KING
                board[r][c] *= 2;
            if ((val&4) == 4) // 0 = RED, 1 = WHITE
                board[r][c] *= -1;
        }
    }

    public int[][] getBoard() {
        int[][] board = new int[8][8];
        for (int r = 0; r <= 1; r++) {
            for (int c = (r + 1) % 2; c < 8; c = c + 2) {
                setPiece(board, r, c, r01);
                r01 /= 8;
                setPiece(board, r + 2, c, r23);
                r23 /= 8;
                setPiece(board, r + 4, c, r45);
                r45 /= 8;
                setPiece(board, r + 6, c, r67);
                r67 /= 8;
            }
        }
        return board;
    }

    public String toString() {
        return CheckersGame.boardTo(getBoard());
    }
}
