/**
 * Teacher created AI to play Checkers in the tournament -- distributed as part
 * of the example
 *
 * @author dsheldon
 * @since 05/09/16
 */
import java.util.ArrayList;
public class DKS_CheckersAI implements CheckersAI {
    /**
     * @return "DKS 1.0"
     **/
    public String getName() {
        return "DKS 1.0";
    }

    /**
     * Returns the "goodness" value of this board for this player
     *
     * @param board The checkers board of the game
     * @param turn Who's value of the board
     * @return A value associated with how good this board is for the player turn
     */
    private int boardValue(int[][] board, int turn) {
        int sum = 0;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                sum += board[r][c];
            }
        }
        return sum*turn;
    }

    /**
     * Picks a move that is the longest jump of the available jumps, if not, a
     * random move
     *
     * @param board
     *            is an 8x8 grid with 0s for empty spots, 1s for RED, and -1s
     *            for BLACK
     * @param turn
     *            is 1 for RED and -1 for BLACK
     * @return A Move which is the selected VALID Move for this turn on this
     *         board, null if no valid move is remaining
     **/
    public Move getMove(int[][] board, int turn) {
        ArrayList<Move> allMoves = AIHelpers.getAllMoves(board, turn);
        if (allMoves.size() > 0) {
            Move bestMove = allMoves.get(0);
            int bestValue = boardValue(
                    AIHelpers.makeMove(board, bestMove, turn), turn);
            for (Move m : allMoves) {
                int[][] nextBoard = AIHelpers.makeMove(board, m, turn);
                int nextVal = boardValue(nextBoard, turn);
                if (nextVal > bestValue) {
                    bestMove = m;
                    bestValue = nextVal;
                }
            }
            return bestMove;
        } else
            return null;
    }
}