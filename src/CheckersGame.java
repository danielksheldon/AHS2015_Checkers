/**
 * Class that contains all elements of a checkers game, including references to the AIs
 * Able to play a full game or take an individual turn
 * @author dsheldon
 * @since 05/11/16
 */
import java.util.ArrayList;

public class CheckersGame {


    /** Whose turn it is? RED = 1, BLACK = -1 **/
    public int turn;
    /**
     * Who won the most recent game -- 0 is a tie or the game is currently
     * playing
     **/
    public int winner;
    /**
     * Array of AIs available for tournament -- add your AIs to this list to get
     * them playing
     **/
    public CheckersAI[] players;
    /** The game state can be INTRO, PLAYING, or GAMEOVER **/
    int gameState;
    /** The available gameStates **/
    final int INTRO = 0, PLAYING = 1, GAMEOVER = 2;
    /**
     * board is an 8x8 grid with 0s for empty spots, 1s for RED, and -1s for
     * BLACK, 2 & -2 for Kings
     **/
    public int[][] board;

    /**
     * Count of the # of pieces before the move
     */
    int lastCount;

    /**
     * The number of turns since the last piece was captured
     */
    int drawTurns;

    /**
     * The # of turns without a capture that lead to a draw
     */
    final int DRAWCOUNT = 40;

    /**
     * The next move an AI will take, when the time is ready -- speeds things up rather than waiting on AIs
     */
    Move nextMove;


    /**
     * Constructor for a CheckersGame
     *
     * @param starterBoard
     *            The starting board conditions
     * @param p1
     *            The AI for Player 1
     * @param p2
     *            The AI for Player 2
     */
    public CheckersGame(int[][] starterBoard, CheckersAI p1, CheckersAI p2) {
        players = new CheckersAI[2];
        players[0] = p1;
        players[1] = p2;

        board = new int[8][8];
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
                board[r][c] = starterBoard[r][c];
        lastCount = countPieces();
        winner = 0;
        turn = 1;
        drawTurns = 0;
        gameState = PLAYING;
        nextMove = null;
        if (players[0] instanceof TreeVisualizer)
            ((TreeVisualizer)players[0]).reset();
        if (players[1] instanceof TreeVisualizer)
            ((TreeVisualizer)players[1]).reset();
    }

    void playGame() {
        while (gameState == PLAYING)
            takeTurn();
    }

    /**
     * Counts the number of non-empty spots on the board
     *
     * @return The count of non-empty spots (or pieces) on the board
     */
    public int countPieces() {
        int count = 0;
        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board[r].length; c++)
                if (board[r][c] != 0)
                    count++;
        return count;
    }

    public void prepTurn () {
        if (gameState == PLAYING) {
            // If the player doesn't have a move -- game over
            if (nextMove == null) {
                if (players[getPI()] instanceof TreeVisualizer)
                    ((TreeVisualizer)players[getPI()]).reset();
                if (AIHelpers.getAllMoves(board, turn).size() > 0) {
                    nextMove = players[getPI()].getMove(board, turn);
                }
            }
        }
    }

    boolean isValidMove (Move m) {
        ArrayList<Move> allMoves = AIHelpers.getAllMoves(board, turn);
        for (Move goodM : allMoves ) {
            if (m.equals (goodM))
                return true;
        }
        return false;
    }



    public void takeTurn() {
        if (gameState == PLAYING) {

            // If the player doesn't have a move -- game over
            if (AIHelpers.getAllMoves(board, turn).size() == 0) {
                gameState = GAMEOVER;
                winner = -turn;
            } else {
                prepTurn();
                if (nextMove != null) {
                    if (isValidMove(nextMove)) {

                        board = AIHelpers.makeMove(board, nextMove, turn);
                        turn = -turn;
                        nextMove = null;
                        if (AIHelpers.getAllMoves(board, turn).size() == 0) {
                            gameState = GAMEOVER;
                            winner = -turn;
                        }
                        if (lastCount == countPieces()) {
                            drawTurns++;
                            if (drawTurns >= DRAWCOUNT)
                                gameState = GAMEOVER;
                        } else {
                            drawTurns = 0;
                            lastCount = countPieces();
                        }
                    } else {
                        System.out.println ("Invalid Move: " + nextMove);
                    }
                } else {

                }
            }
        }
    }

    int getPI () {
        if (turn == -1)
            return 1;
        return 0;
    }

    void goUp () {
        if (players[getPI()] instanceof TreeVisualizer) {
            ((TreeVisualizer) players[getPI()]).goUp();
        }
    }
    void goDown () {
        if (players[getPI()] instanceof TreeVisualizer) {
            ((TreeVisualizer) players[getPI()]).goDown();
        }
    }
    void goLeft () {
        if (players[getPI()] instanceof TreeVisualizer) {
            ((TreeVisualizer) players[getPI()]).goLeft();
        }
    }
    void goRight () {
        if (players[getPI()] instanceof TreeVisualizer) {
            ((TreeVisualizer) players[getPI()]).goRight();
        }
    }

    void goSelected () {
        if (players[getPI()] instanceof TreeVisualizer) {
            board = ((TreeVisualizer) players[getPI()]).getSelected().board;
            turn = ((TreeVisualizer) players[getPI()]).getSelected().turn;
            nextMove = null;
        }
    }


    /**
     * User input selecting which move to make
     * @param cell What cell was clicked
     */
    public void clicked(int cell) {
        if (cell >= 0 && cell < 64) {
            if (players[getPI()] instanceof HumanAI)
                ((HumanAI) players[getPI()]).clicked(cell, board);
        }
    }



    /**
     * Whether or not the game is still playing
     * @return true if the game is playing, false otherwise
     */
    public boolean isPlaying() {
        return gameState == PLAYING;
    }

    public boolean needsTime () {
        return nextMove == null;
    }

    public boolean isGameOver() {
        return gameState == GAMEOVER;
    }

    public int getWinner() {
        return winner;
    }

    static String boardTo(int[][] board) {
        String h = "Ww.rR";
        String retString = "";
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                retString += h.substring(board[r][c] + 2, board[r][c] + 3);
            }
            retString += "\n";
        }
        return retString;
    }

    public String toString() {
        String retString = "";
        retString += players[0].getName() + " vs. " + players[1].getName()
                + "\n";
        if (isPlaying())
            if (turn == 1)
                retString += "RED Turn\n";
            else
                retString += "BLACK turn\n";
        else if (winner == 0)
            retString += "TIE GAME";
        else if (winner == 1)
            retString += "RED WIN";
        else if (winner == -1)
            retString += "BLACK WIN";
        retString += boardTo(board);
        return retString;
    }

}

