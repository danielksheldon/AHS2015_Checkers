public interface TreeVisualizer {
	public GameNode getRoot();
	public GameNode getSelected();
	void goUp();
	void goDown();
	void goLeft();
	void goRight();
}
