
import java.util.ArrayList;

public class GameNode {
    GameNode parent;
    ArrayList<GameNode> children;
    int value;
    int recValue;
    int depth;
    int [][] board;
    int childNum;
    int turn;
    Move move;

    public GameNode (int [][] b, int turn) {
        parent = null;
        children = new ArrayList<GameNode>(0);
        depth = 0;
        board = b;
        this.turn = turn;
    }

    public void addChild (GameNode p) {
        children.add (p);
        p.childNum = children.size()-1;
    }

    public GameNode getNextSibling() {
        if (parent != null) {
            return parent.children.get((childNum+1) % parent.children.size());
        }
        return this;
    }

    public GameNode firstChild () {
        if (children.size() == 0)
            return this;
        return children.get(0);
    }

    public GameNode getPreviousSibling() {
        if (parent != null) {
            return parent.children.get((childNum-1+parent.children.size()) % parent.children.size());
        }
        return this;
    }

    public GameNode (GameNode p, int [][] b, Move m ) {
        parent = p;
        children = new ArrayList<GameNode>(0);
        depth = p.depth+1;
        board = b;
        turn = -p.turn;
        p.addChild (this);
        move = m;
    }
}
