package backend;

public class Cell {
	private int x;
	private int y;
	private int value = 0;
	int FutureValue;
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
	public void toggle() {
		value = (value+1)%6;
	}
	public void adressNewValue(int value) {
		this.value = value;
	}
	public void setValue() {
		this.value = FutureValue;
	}
	public void setFutureValue(int value) {
		this.FutureValue = value;
	}
}
