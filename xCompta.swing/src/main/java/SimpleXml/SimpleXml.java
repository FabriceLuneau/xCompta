package SimpleXml;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class SimpleXml extends JFrame {
	private static final long serialVersionUID = 1L;
	public SimpleXml() {
		super();
		JFileChooser choix = new JFileChooser();
		choix.showOpenDialog(null);
		File file = choix.getSelectedFile();

		setTitle("Simple Xml");
		setSize(500, 500);

		TableSimpleXmlViewer table = new TableSimpleXmlViewer(file);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		scrollPane.add(table);

		setContentPane(scrollPane);

		pack();

		setVisible(true);
		table.requestFocus();

		requestFocus();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new SimpleXml();

	}
}
