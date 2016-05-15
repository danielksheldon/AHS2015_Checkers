
import java.util.ArrayList;

public class HumanAI implements CheckersAI {
    Move curMove;
    boolean doneMoving;
    String name;
    int turn;

    public HumanAI(int turn) {
        doneMoving = false;
        curMove = new Move();
        name = "Human AI";
        this.turn = turn;
    }

    public HumanAI(String name, int turn) {
        this.name = name;
        this.turn = turn;
        curMove = new Move();
    }

    /**
     * Accessor to get the name of the AI
     *
     * @return A String which contains the name of this AI
     **/
    public String getName() {
        return name;
    }

    /**
     * Method to retrieve a VALID move for a particular player, on this board
     *
     * @param board
     *            is an 8x8 grid with 0s for empty spots, 1s for RED, and -1s
     *            for BLACK, 2 & -2 for Kings
     * @param turn
     *            is 1 for RED and -1 for BLACK
     * @return A Move which is the selected VALID Move for this turn on this
     *         board, null if none are available
     **/
    public Move getMove(int[][] board, int turn) {
        if (doneMoving) {
            doneMoving = false;
            Move retMove = curMove.copy();
            curMove = new Move();
            return retMove;
        }
        return null;
    }

    /**
     * Used to return the next move (or partial move) without taking a turn
     *
     * @return The Move that has been selected but not yet submitted
     */
    public Move lookMove() {
        return curMove.copy();
    }

    /**
     * Method to tell a Human AI what spots to hop to next
     *
     * @param cellNum
     *            The next spot to hop to, if you send the last spot again, it
     *            will make the move
     */
    public void clicked(int cellNum, int[][] board) {
        ArrayList<Move> allMoves = AIHelpers.getAllMoves(board, turn);
        doneMoving = false;
        for (Move m : allMoves) {
            if (m.equals(curMove))
                doneMoving = true;
        }
        if (!doneMoving) {
            if (curMove.hops.size() > 0 && cellNum % 8 == curMove.lastCol()
                    && cellNum / 8 == curMove.lastRow()) {
                if (curMove.hops.size() == 1)
                    curMove = new Move();
            } else {
                Move nextHop = curMove.copy();
                nextHop.addPoint(cellNum / 8, cellNum % 8);
                for (Move m : allMoves) {
                    if (nextHop.hops.size() == 1) {
                        if (m.hops.get(0).row == nextHop.lastRow()
                                && m.hops.get(0).col == nextHop.lastCol()) {
                            curMove = nextHop;
                        }
                    } else if (m.startsWith(nextHop)) {
                        curMove = nextHop;
                    }
                }
            }

        }
    }

}
