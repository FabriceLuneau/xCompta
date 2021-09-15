package etatViewer;


public class Cell {
	protected String	value;

	
	public Cell() {}
	
	public Cell(String value) {
		this.value = value;		
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}
}