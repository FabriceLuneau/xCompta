package bogg;

import javax.swing.JTextField;

public abstract class Field extends JTextField {
	private static final long serialVersionUID = 1L;
	protected	boolean		nullValue = true;


	Field() {
		super();
	}

	Field(String value) {
		super(value);
	}

	public boolean isNullValue() {
		return nullValue;
	}

	public void setNullValue(boolean nullValue) {
		this.nullValue = nullValue;
	}
}