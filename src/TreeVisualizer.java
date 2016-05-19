public interface TreeVisualizer {
	public GameNode getRoot(int[][]board, int turn);
	public GameNode getSelected();
	void goUp();
	void goDown();
	void goLeft();
	void goRight();
	void reset ();
}
