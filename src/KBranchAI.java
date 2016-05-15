
import java.util.ArrayList;

class KBranchAI implements CheckersAI {

	/** The MAXIMUM depth of the search tree **/
	int MAX_DEPTH = 1;

	/** The number of branches kept at each level **/
	int BRANCH_K = 1;

	public KBranchAI(int depth, int branch) {
		MAX_DEPTH = depth;
		BRANCH_K = branch;
	}

	/**
	 * Accessor to get the name of the AI
	 * 
	 * @return "Look Ahead AI 1.0"
	 **/
	public String getName() {
		return "K Branch AI 1." + MAX_DEPTH + "." + BRANCH_K;
	}

	/**
	 * Method to access what depth this AI checks down to
	 * @return The maximum depth of search for this AI
	 */
	public int getDepth () {
		return MAX_DEPTH;
	}
	
	/**
	 * Method to retrieve a VALID move for a particular player, on this board
	 * 
	 * @param board
	 *            is an 8x8 grid with 0s for empty spots, 1s for WHITE, and -1s
	 *            for BLACK
	 * @param turn
	 *            is 1 for WHITE and -1 for BLACK
	 * @return A int which is the selected VALID Move for this turn on this
	 *         board, 0..63 indicating spot on board, or -1 if none are
	 *         available
	 **/
	public Move getMove(int[][] board, int turn) {
		ArrayList<Move> allMoves = AIHelpers.getAllMoves(board, turn);
		if (allMoves.size() == 0) {
			return null; // There is no valid play from this board for this turn
		}
		if (allMoves.size() == 1) { // this is only 1 move -- do it
			return allMoves.get(0);
		}

		int bestMove = 0;
		int bestValue = getRecBoardValue(
				AIHelpers.makeMove(board, allMoves.get(0), turn), turn, 1);
		for (int i = 1; i < allMoves.size(); i++) {
			int nextValue = getRecBoardValue(
					AIHelpers.makeMove(board, allMoves.get(i), turn), turn, 1);
			if (nextValue > bestValue) {
				bestMove = i;
				bestValue = nextValue;
			}
		}
		return allMoves.get(bestMove);
	}

	int boardValue(int[][] board, int turn) {
		int KING_WEIGHT = 4;
		int BOARD_WEIGHT = 2;
		int value = 0;
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				value = value + BOARD_WEIGHT * board[r][c] + board[r][c] / 2
						* KING_WEIGHT;
				if (c == 0 || c == 7)
					value = value + board[r][c];
				if (board[r][c] == 1)
					value += (r);
				if (board[r][c] == -1)
					value -= (7-r);
			}
		}
		if (turn == -1)
			value = -value;
		return value;
	}

	int getRecBoardValue(int[][] board, int turn, int curDepth) {
		// reached maximum depth -- take the value of the current board
		if (curDepth == MAX_DEPTH) {
			int curValue = boardValue(board, turn);
			return curValue;
		}

		int depthTurn = turn - 2 * turn * (curDepth % 2);
		// no more moves to play -- terrible board for this players turn
		ArrayList<Move> allMoves = AIHelpers.getAllMoves(board, depthTurn);
		if (allMoves.size() == 0) {
			if (turn == depthTurn)
				return 10000; // this is a losing game -- don't choose it
			else
				return 0; // this is a winning game -- choose it!
		}

		int bestMove = 0;
		int bestValue = getRecBoardValue(
				AIHelpers.makeMove(board, allMoves.get(0), depthTurn), turn,
				curDepth + 1);
		for (int i = 1; i < allMoves.size(); i++) {
			int nextValue = getRecBoardValue(
					AIHelpers.makeMove(board, allMoves.get(i), depthTurn),
					turn, curDepth + 1);
			if (curDepth % 2 == 0) { // if an Even Depth -- MAXIMIZE
				if (nextValue > bestValue) {
					bestMove = i;
					bestValue = nextValue;
				}
			} else { // if an ODD depth -- MINIMIZE
				if (nextValue < bestValue) {
					bestMove = i;
					bestValue = nextValue;
				}
			}
		}
		return bestValue;
	}

}