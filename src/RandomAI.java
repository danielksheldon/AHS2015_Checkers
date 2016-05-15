import java.util.ArrayList;

public class RandomAI implements CheckersAI {
  /**
   Returns "DKS Random 1.0"
   @return A String which contains the name of this AI
  **/
  public String getName () {
    return "DKS Random 1.0";
  }
  /** 
   Returns a randomly selected VALID move for this turn on this board
   @param board is an 8x8 grid with 0s for empty spots, 1s for RED, and -1s for BLACK 
   @param turn is 1 for RED and -1 for BLACK
   @return A Move which is the selected VALID Move for this turn on this board, null if no valid move is remaining
  **/
  public Move getMove (int [][] board, int turn) {
    ArrayList<Move> allMoves = AIHelpers.getAllMoves (board, turn);
    if (allMoves.size() > 0) {
       return allMoves.get((int)(allMoves.size()*Math.random()));
    }
    else return null;
  }
}