import processing.core.*;

import java.util.ArrayList;

public class KBranchVis implements CheckersAI{

	int MAX_DEPTH = 9;

	PApplet pApp;
	GameNode selectedNode;
	GameNode rootNode;
	CheckersVisualizer otherVis;
	int rootTurn;
	
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

		if (numOppPieces == 0){
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
						adder = Math.abs (c-oppC);
						if (adder > 2)
							value += 28 - 4 * adder;
					}
				}
			}
		}

		return value;
	}

	
	
	public KBranchVis(PApplet p, CheckersVisualizer oVis, int d) {
		pApp = p;
		otherVis = oVis;
		
	}

	
	
	public String getName () {
		return "Branch Vis AI";
	}
	
	public Move getMove(int[][] board, int turn) {
		if (selectedNode.parent == rootNode) {
			return selectedNode.move;
		} else {
			ArrayList<Move> moveOptions = new ArrayList<Move>();
			for (GameNode kid : rootNode.children) {
				if (kid.recValue == rootNode.recValue)
					moveOptions.add(kid.move);
			}
			return moveOptions.get((int)(moveOptions.size()*Math.random()));
		}
//		return null;
	}

	public void drawTree(int[][] board, int turn) {
		pApp.background(0);
		otherVis.drawBoard(board, 0, 0, (float) 0.7);

	    otherVis.drawBoard(selectedNode.board, pApp.width - 350, 0, (float) 0.7);
		otherVis.drawTurn(turn);
		pApp.fill(255);
		pApp.text(selectedNode.value, pApp.width - 350, 25);
		pApp.text(selectedNode.recValue, pApp.width - 350, 75);

		pApp.pushMatrix();
		pApp.translate(pApp.width / 2, pApp.height / 8);
		drawTree(rootNode, pApp.width - 50);
		pApp.popMatrix();
	}

	int getMin (ArrayList<GameNode> kids) {
		int min = kids.get(0).recValue;
		for (GameNode kid : kids)
			if (kid.recValue  < min)
				min = kid.recValue;
		return min;
	}
	
	int getMax (ArrayList<GameNode> kids) {
		int max = kids.get(0).recValue;
		for (GameNode kid : kids)
			if (kid.recValue > max)
				max = kid.recValue;
		return max;
	}
	
	public void makeTree(GameNode start, int depth) {
		start.value = boardValue(start.board, rootNode.turn);
		if (Math.abs(start.value) == 500)  // sooner wins are better, sooner losses are worse
			start.value = (MAX_DEPTH-depth)*start.value;
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
				if (depth%2==0)
					start.recValue = getMax (start.children);
				else
					start.recValue = getMin (start.children);
			}
		}
	}

	public void drawTree(GameNode start, int levelWidth) {
		pApp.pushMatrix ();
		pApp.translate(0, 100);
		if (start == selectedNode)
			pApp.fill(255, 255, 0);
		else
			pApp.fill(255);
		pApp.rect(0, 0, 40, 60);
		pApp.fill(255, 40, 40);
		pApp.text(start.value, 0, 0);
		pApp.text(start.recValue, 0, 20);
		pApp.translate(-levelWidth / 2 + levelWidth/(start.children.size()+1), 0);
		for (GameNode child : start.children) {
			drawTree(child, levelWidth / start.children.size());
			pApp.translate(levelWidth / (start.children.size()+1), 0);
		}
		pApp.popMatrix();
	}


	void goUp() {
		if (selectedNode != rootNode)
			selectedNode = selectedNode.parent;
	}

	void goDown() {

		selectedNode = selectedNode.firstChild();
	}

	void goLeft() {
		selectedNode = selectedNode.getPreviousSibling();
	}

	void goRight() {
		selectedNode = selectedNode.getNextSibling();
	}

	void newVis(int[][] board, int t) {
		rootTurn = t;
		rootNode = new GameNode(board, t);
		makeTree(rootNode, 0);
		selectedNode = rootNode;
	}
}
