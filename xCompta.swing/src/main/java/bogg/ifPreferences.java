package bogg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ifPreferences extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	
	protected JPanel panel = new JPanel();
	protected JPanel panel1 = new JPanel();
	protected JPanel panel2 = new JPanel();

	protected Dimension size = new Dimension(400, 350);

	protected JSpinner nbDecimal = new JSpinner();

	public ifPreferences() {
		this.setSize(size);
		this.setClosable(true);
		this.setIconifiable(true);
		this.setResizable(true);
		this.setMaximizable(true);

		panel1.add(new JLabel("Nombre de décimal : "));
		panel1.add(nbDecimal);

		nbDecimal.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// nbDecimal.getV
			}
		});

		panel.add(panel1);
		panel.add(panel2);

		panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		setContentPane(panel);
		setVisible(true);
		pack();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		JDesktopPane bureau = new JDesktopPane();
		bureau.setBackground(Color.gray);

		frame.setSize(1000, 700);
		frame.setContentPane(bureau);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		bureau.add(new ifPreferences());

	}

	@Override
	public String toString() {
		return getTitle();
	}
}