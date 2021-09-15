package bogg;

import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FieldDate extends Field {
	private static final long serialVersionUID = 1L;

	private LocalDate minimum = null;
	private LocalDate maximum = null;

	public FieldDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date date = new Date();
				
						//LocalDate.now();
				
				setText(dateFormat.format(date));
	    
		init();
	}

	public FieldDate(LocalDate date) {
		setText(date.toString());

		init();
	}

	private void init() {
		setColumns(10);

//		addActionListener(event -> {
//				testInput();
//		});

//		this.addFocusListener(new FocusListener() {
//			public void focusGained(FocusEvent fe) {
//			}

//			public void focusLost(FocusEvent fe) {
//				testInput();
//			}
//		});
	}

//	public void formatInpu	t() {
//		setText(this.getText().trim());
//		setText(this.getText().replace("-", "/"));
//		setText(this.getText().replace(" ", "/"));
//		setText(this.getText().replace(".", "/"));
//	}

//	public boolean inputIsValid() {
//		LocalDate date = LocalDate.parse(gettText()); 

//		if(mi(nimum != null && maximum  != null {
//			return date. 
//		})
//		if (getText().trim().compareTo("") == 0 && nullValue == true)
//			return true;

//		if (getText().trim().compareTo("") == 0 && nullValue == false)
//			return false;

//		if (maximum != null) {
//			if (DateUtil.stringToDate(getText()).after(maximum))
//				return false;
//		}

//		if (minimum != null) {
//			if (DateUtil.stringToDate(getText()).before(minimum))
//				return false;
//		}

//		if (DateUtil.stringToDate(getText()) != null)
//			return true;
//		else
//			return false;
//		return true;
//	}

//	public void testInput() {
//		if (inputIsValid())
//			setBackground(Color.white);
//		else
//			setBackground(Color.pink);
//	}

	public LocalDate getValeur() {
		LocalDate date;
		
		
		date= LocalDate.parse(getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		
		return date;
		
	}

	public void setValeur(LocalDate date) {
		setText(date.toString());
	}

	public void setValeur(String date) {
		setText(date);
	}

	public LocalDate getMaximum() {
		return maximum;
	}

	public void setMaximum(LocalDate maximum) {
		this.maximum = maximum;
	}

	public LocalDate getMinimum() {
		return minimum;
	}

	public void setMinimum(LocalDate minimum) {
		this.minimum = minimum;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		final JPanel panel = new JPanel();
		final JPanel panel1 = new JPanel();
		final JPanel panel2 = new JPanel();

		final FieldDate fieldDate = new FieldDate();
		final JButton button = new JButton("Tester");

		frame.setSize(500, 125);
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel.add(panel1);
		panel.add(panel2);

		JLabel label = new JLabel("Date");
		panel1.add(label);
		panel1.add(fieldDate);
		label.setLabelFor(fieldDate);

		panel1.setLayout(new FlowLayout(FlowLayout.LEFT));

		panel2.add(button);
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		frame.setVisible(true);

		button.addActionListener(event -> {
			System.out.println(fieldDate.getValeur());
		});
	}
}