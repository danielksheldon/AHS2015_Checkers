import processing.core.*;

import java.util.ArrayList;

public class MinMaxAI implements CheckersAI, TreeVisualizer {

    private int MAX_DEPTH = 9;

    private PApplet pApp;
    private GameNode selectedNode;
    private GameNode rootNode;

    public MinMaxAI(PApplet p, int d) {
        pApp = p;
        MAX_DEPTH = d;
    }

    /** must be called before getSelected
     * @param board
     * @param turn
     * @return
     */
    public GameNode getRoot(int[][] board, int turn) {
     //   if (rootNode == null)
     //       makeRoot (board, turn);
        return rootNode;
    }

    public void reset () {
        rootNode = null;
        selectedNode = null;
    }

    private void makeRoot (int[][] board, int turn) {
        if (rootNode != null) {
            System.out.println ("ROOT ALREADY CREATED!");
        } else {
            rootNode = new GameNode(board, turn);
            makeTree(rootNode, 0);

            ArrayList<GameNode> moveOptions = new ArrayList<GameNode>();
            for (GameNode kid : rootNode.children) {
                if (kid.recValue == rootNode.recValue) {
                    moveOptions.add(kid);
                }
            }

            if (moveOptions.size() == 0)
                selectedNode = null;
            else
                selectedNode = moveOptions.get((int) (moveOptions.size() * Math.random()));
        }
    }

    public GameNode getSelected() {
        return selectedNode;
    }

    int boardValue(int[][] board, int turn) {
        int KING_WEIGHT = 4;
        int BOARD_WEIGHT = 2;
        int value = 0;
        int numOppPieces = 0;
        int numPieces = 0;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] != 0) {
                    value = value + BOARD_WEIGHT * board[r][c] + board[r][c]
                            / 2 * KING_WEIGHT;
                    if (board[r][c] != turn && board[r][c] != 2 * turn)
                        numOppPieces++;
                    else
                        numPieces++;
                    if (c == 0 || c == 7)
                        value = value + board[r][c];
                    if (board[r][c] == 1)
                        value += (r);
                    if (board[r][c] == -1)
                        value -= (7 - r);
                }
            }
        }

        if (numOppPieces == 0) {
            return 500;
        } else if (numPieces == 0) {
            return -500;
        }
        if (turn == -1)
            value = -value;
        if (numOppPieces == 1) { // chase him down code
            int oppR = -1;
            int oppC = -1;
            for (int r = 0; r < board.length; r++) {
                for (int c = 0; c < board[0].length; c++) {
                    if (board[r][c] == -turn || board[r][c] == -2 * turn) {
                        oppR = r;
                        oppC = c;
                    }
                }
            }
            for (int r = 0; r < board.length; r++) {
                for (int c = 0; c < board[0].length; c++) {
                    if (board[r][c] == turn || board[r][c] == 2 * turn) {
                        int adder = Math.abs(r - oppR);
                        if (adder > 1)
                            value += 28 - 4 * adder;
                        adder = Math.abs(c - oppC);
                        if (adder > 2)
                            value += 28 - 4 * adder;
                    }
                }
            }
        }

        return value;
    }


    public String getName() {
        return "MinMax"+MAX_DEPTH;
    }



    public Move getMove(int[][] board, int turn) {
        // if the tree isn't made for some reason...
        makeRoot(board, turn);

        // if there is no valid move selected
        if (selectedNode == null)
            return null;
        // if the user selected a lower level branch to go to
        while (selectedNode.parent != rootNode) {
            selectedNode = selectedNode.parent;
        }

        // return the move to get to the selected node branch
        Move selectedMove = selectedNode.move;

        return selectedMove;
    }

    int getMin(ArrayList<GameNode> kids) {
        int min = kids.get(0).recValue;
        for (GameNode kid : kids)
            if (kid.recValue < min)
                min = kid.recValue;
        return min;
    }

    int getMax(ArrayList<GameNode> kids) {
        int max = kids.get(0).recValue;
        for (GameNode kid : kids)
            if (kid.recValue > max)
                max = kid.recValue;
        return max;
    }

    public void makeTree(GameNode start, int depth) {
        start.value = boardValue(start.board, rootNode.turn);
        if (Math.abs(start.value) == 500) // sooner wins are better, sooner and losses are worse
            start.value = (MAX_DEPTH - depth) * start.value;
        start.recValue = start.value;
        if (depth < MAX_DEPTH) {
            ArrayList<Move> allMoves = AIHelpers.getAllMoves(start.board,
                    start.turn);
            for (Move m : allMoves) {
                GameNode child = new GameNode(start, AIHelpers.makeMove(
                        start.board, m, start.turn), m);
                makeTree(child, depth + 1);
            }
            if (allMoves.size() > 0) {
                if (depth % 2 == 0)
                    start.recValue = getMax(start.children);
                else
                    start.recValue = getMin(start.children);
            }
        }
    }


    public void goUp() {
        if (selectedNode.parent != rootNode)
            selectedNode = selectedNode.parent;
    }

    public void goDown() {
        selectedNode = selectedNode.firstChild();
    }

    public void goLeft() {
        selectedNode = selectedNode.getPreviousSibling();
    }

    public void goRight() {
        selectedNode = selectedNode.getNextSibling();
    }

}
