import java.util.ArrayList;

/**
 * An Interface used for a Checkers game for the Computer Science classes at
 * Arlington High School Students program a Class that implements this interface
 * and then they are entered into a Round Robin tournament to see who is the
 * supreme champion!
 *
 * @author D Sheldon
 * @version 05/05/2016
 **/
public interface CheckersAI {
    /**
     * Accessor to get the name of the AI
     *
     * @return A String which contains the name of this AI
     **/
    String getName();

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
    Move getMove(int[][] board, int turn);
}

