import processing.core.PApplet;
import processing.core.PConstants;
import java.util.ArrayList;
/**
 * Class to visualize a Checkers heck game of Checkers
 *
 * @author dsheldon
 * @since 05/09/16
 */
public class CheckersVisualizer {

    /** The PApplet that is running to draw the game to **/
    private PApplet pApp;
    /** The size of the squares on the board **/
    private int cellLen = 50;

    /**
     * Constructor for visualizer
     *
     * @param p
     *            The Processing Applet that this visualizer is drawing to
     */
    CheckersVisualizer(PApplet p) {
        pApp = p;
    }

    /**
     * Draws Min Max Tree
     */
    public void drawTree(GameNode root, GameNode selectedNode) {

        pApp.background(0);
        drawBoard(root.board, 0, 0, (float) 0.7);

        drawBoard(selectedNode.board, pApp.width - 350, 0, (float) 0.7);
        drawTurn(root.turn);
        pApp.fill(255);
        pApp.text(selectedNode.value, pApp.width - 350, 25);
        pApp.text(selectedNode.recValue, pApp.width - 350, 75);

        pApp.pushMatrix();
        pApp.translate(pApp.width / 2, pApp.height / 8);

        drawTree(root, selectedNode, pApp.width - 50);
        pApp.popMatrix();
    }


    public void drawTree(GameNode start, GameNode selectedNode, int levelWidth) {
        pApp.pushMatrix();
        pApp.translate(0, 100);
        if (start == selectedNode)
            pApp.fill(255, 255, 0);
        else
            pApp.fill(255);
        pApp.rect(0, 0, 40, 60);
        pApp.fill(255, 40, 40);
        pApp.text(start.value, 0, 0);
        pApp.text(start.recValue, 0, 20);
        pApp.translate(-levelWidth / 2 + levelWidth
                / (start.children.size() + 1), 0);
        for (GameNode child : start.children) {
            drawTree(child, selectedNode, levelWidth / start.children.size());
            pApp.translate(levelWidth / (start.children.size() + 1), 0);
        }
        pApp.popMatrix();
    }

    /**
     * Prints THINKING on the screen when the AIs are working
     */
    void printThinking () {
        pApp.fill (255);
        pApp.pushMatrix();
        pApp.translate (pApp.width/2, pApp.height/6);
        pApp.scale (5);
        pApp.text ("THINKING", 0,0);
        pApp.popMatrix();

    }

    /**
     * Draws the entire screen -- called once per draw cycle
     *
     * @param gameBoard
     *            The main gameboard
     * @param turn
     *            Whose turn it is
     * @param winner
     *            Who the winner is (1, or -1), 0 is no one won
     * @param players
     *            The AIs of the players, to get their names
     * @param scores
     *            The score of the head to head
     */
    public void drawScreen(int[][] gameBoard, int turn, int winner,
                           CheckersAI[] players, int[] scores) {
        drawBoard(gameBoard, 0, 0, 1);
        //drawTurn(turn);
        drawWinner(winner);
        drawMoves(gameBoard, turn);
        drawScores(scores, players);
        if (players[0] instanceof HumanAI) {
            Move m = ((HumanAI) players[0]).lookMove();
            highLiteMove (m);
        }
        if (players[1] instanceof HumanAI) {
            Move m = ((HumanAI) players[1]).lookMove();
            highLiteMove (m);
        }
        if (turn == 1 && players[0] instanceof TreeVisualizer) {
            TreeVisualizer tV = (TreeVisualizer) players[0];
            if (tV == null)
                System.out.println ("NULL TV");
            if (tV.getSelected() == null)
                System.out.println ("NULL Selected");
            if (tV.getRoot() == null)
                System.out.println ("NULL ROOT");
            if (tV != null && tV.getRoot() != null && tV.getSelected() != null)
                 drawTree(tV.getRoot(), tV.getSelected());
        }
        if (turn == -1 && players[1] instanceof TreeVisualizer) {
            TreeVisualizer tV = (TreeVisualizer) players[1];
            if (tV == null)
                System.out.println ("NULL TV");
            if (tV.getSelected() == null)
                System.out.println ("NULL Selected");
            if (tV.getRoot() == null)
                System.out.println ("NULL ROOT");
            if (tV != null && tV.getRoot() != null && tV.getSelected() != null)
                drawTree(tV.getRoot(), tV.getSelected());
        }

    }

    void highLiteMove (Move m) {
        pApp.stroke(255,255,0);
        pApp.noFill();
        for (Point p : m.hops) {
            pApp.rect(p.col * cellLen + 50, p.row * cellLen + 50, cellLen, cellLen);
        }
    }
    /**
     * Sets the color based on the RED/BLACK color
     *
     * @param cellColor
     *            The RED (1) or BLACK(-1) color to set it to
     */
    void fillColor(int cellColor) {
        if (cellColor > 0)
            pApp.fill(255, 0, 0);
        else
            pApp.fill(10, 10, 10);
    }

    /**
     * Returns the cell# (0..63) of the
     * @param mX
     * @param mY
     * @return The cell number 0..63 which is under the x,y position entered
     */
    public int getCellNum (int mX, int mY) {
        return (mX-50+cellLen/2)/cellLen + (mY-50+cellLen/2)/cellLen*8;
    }

    /**
     * Draws the board
     *
     * @param board
     *            The 2D array containing all the pieces
     */
    void drawBoard(int[][] board, int x, int y, float scaleFactor) {
        pApp.pushMatrix ();
        pApp.translate (x, y);
        pApp.scale(scaleFactor);
        pApp.stroke(242, 142, 20);
        int cellColor = AIHelpers.RED;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                fillColor(cellColor);
                pApp.rect(c * cellLen + 50, r * cellLen + 50, cellLen, cellLen);
                cellColor = -cellColor;
            }
            cellColor = -cellColor;
        }
        drawPieces(board);
        pApp.popMatrix();
    }

    /**
     * Draws a checker next to the board given whos turn it is
     *
     * @param turn
     *            The turn of the current player
     */
    void drawTurn(int turn) {
        if (turn == AIHelpers.RED)
            drawChecker(25, 50, turn);
        else
            drawChecker(25, pApp.height - 50, turn);
    }

    /**
     * Draws all the pieces on the board
     *
     * @param board
     *            The current board to display
     */
    void drawPieces(int[][] board) {
        pApp.stroke(255);
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (board[r][c] != 0) {
                    drawChecker(c * cellLen + 50, r * cellLen + 50, board[r][c]);
                }
            }
        }
    }

    /**
     * Draws a checker at (r,c), draws a King when the piece is a King
     *
     * @param x
     *            X location to draw the checker
     * @param y
     *            Y location to draw the checker
     * @param piece
     *            -2 BLACK KING, -1 BLACK, 1 RED, 2 RED KING
     */
    void drawChecker(float x, float y, int piece) {
        fillColor(piece);
        pApp.ellipse(x, y, (float) (cellLen * .75), (float) (cellLen * .6));
        pApp.ellipse(x, y - 5, (float) (cellLen * .75), (float) (cellLen * .6));
        if (Math.abs(piece) == 2)
            pApp.ellipse(x, y - 10, (float) (cellLen * .75),
                    (float) (cellLen * .6));

    }

    /**
     * Draw a fun winning line of checkers for the winner
     *
     * @param turn
     *            The color of the winner
     */
    void drawWinner(int turn) {
        if (turn != 0) {
            fillColor(turn);
            for (int i = 1; i <= 9; i++) {
                drawChecker(i * pApp.width / 10, pApp.height - 50, turn);
            }
        }
    }

    /**
     * Draw all possible moves for turn on the the board
     *
     * @param board
     *            The board to find the moves for
     * @param turn
     *            The turn to find the moves for
     */
    void drawMoves(int[][] board, int turn) {
        ArrayList<Move> moves = AIHelpers.getAllMoves(board, turn);
        pApp.pushMatrix();
        pApp.translate(pApp.width - 200, 50);
        for (Move m : moves) {
            pApp.text("" + m, 0, 0);
            pApp.translate(0, 30);
        }
        pApp.popMatrix();
    }

    /**
     * Draw the Win-Tie-Loss record for the current 2 players
     *
     * @param scores
     *            The array of scores [0] = P1 wins, [1] = ties, [2] = P2 wins
     * @param players
     *            The players in the current game
     */
    void drawScores(int[] scores, CheckersAI[] players) {
        pApp.textAlign(PConstants.CENTER, PConstants.CENTER);
        pApp.text(players[0].getName() + "  " + scores[0] + "-" + scores[1]
                + "-" + scores[2] + "  " + players[1].getName(), 50+175, 10);

    }

    /**
     * Draw the Win-Tie-Loss record for the entire tournament
     *
     * @param scores
     *            The array of scores [0] = P1 wins, [1] = ties, [2] = P2 wins
     * @param players
     *            All of the players players in the current game
     */
    void drawTournament(int[][][] scores, CheckersAI[] players) {
        pApp.background(0, 255, 0);
        pApp.pushMatrix();
        pApp.translate (-40,0);
        int cellW = 80;
        for (int p1I = 0; p1I < players.length; p1I++) {
            pApp.textAlign(PConstants.LEFT, PConstants.CENTER);
            pApp.fill(0);
            pApp.text("" + players[p1I].getName(), 490 + cellW * (players.length),
                    100 + p1I * 25);
            pApp.pushMatrix();
            pApp.translate(500 + p1I * cellW, 75);
            pApp.rotate(pApp.radians(-45));
            pApp.text("" + players[p1I].getName(), 0, 0);
            pApp.popMatrix();
            pApp.textAlign(PConstants.CENTER, PConstants.CENTER);
            for (int p2I = 0; p2I < players.length; p2I++) {
                pApp.fill(255);
                pApp.rect(500 + p1I * cellW, 100 + p2I * 25, cellW, 25);
                pApp.fill(0);
                pApp.text(scores[p1I][p2I][0] + "-" + scores[p1I][p2I][1] + "-"
                                + scores[p1I][p2I][2], 500 + p1I * cellW,
                        100 + p2I * 25);
            }
        }
        pApp.popMatrix();
    }

}
