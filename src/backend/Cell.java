package backend;

public class Cell {
	private int x;
	private int y;
	private int value = 0;
	public Cell(int x,int y) {
		this.x = x;
		this.y = y;
	}
	public Cell(int x,int y,int value) {
		this.x = x;
		this.y = y;
		this.value=value;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getValue() {
		return value;
	}
}
