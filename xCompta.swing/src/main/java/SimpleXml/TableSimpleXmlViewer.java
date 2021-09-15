package SimpleXml;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author Fabrice LUNEAU
 * 
 *         Permet d'afficher un fichier XML , de façon minimaliste
 * 
 */
public class TableSimpleXmlViewer extends JTable {
	private static final long serialVersionUID = 1L;
	
	protected List elementList = new ArrayList<String[]>();

	protected TableModel tableModel;
	protected TableColumnModel columnModel;

	public TableSimpleXmlViewer() {
		init();
	}

	public TableSimpleXmlViewer(File file) {
		init();
		loadData(file);
	}

	private void init() {
		this.getTableHeader().setReorderingAllowed(false);
		tableModel = new MonTableModel();
		columnModel = new DefaultTableColumnModel();

		TableColumn column1 = new TableColumn(0, 50);
		column1.setHeaderValue("Property");
		columnModel.addColumn(column1);

		TableColumn column2 = new TableColumn(1, 50);
		column2.setHeaderValue("Value");
		columnModel.addColumn(column2);

		this.setModel(tableModel);
		this.setColumnModel(columnModel);

		changeSelection(0, 0, false, false);

		requestFocusInWindow();
	}

	public void loadData(File file) {
		try {
			elementList = new ArrayList<String[]>();
			SAXReader reader = new SAXReader();
			Document document = reader.read(file);
			Element root = document.getRootElement();

			List list = new ArrayList<String[]>();

			for (Iterator i = root.elementIterator(); i.hasNext();) {
				Element element = (Element) i.next();

				String[] tab = new String[2];
				tab[0] = element.getName();
				tab[1] = element.getText();

				elementList.add(tab);
			}
		} catch (Exception e) {
			//Todo faire quelque chose avec les exceptions
		}
		((AbstractTableModel) tableModel).fireTableDataChanged();
	}

	public void saveAs(String filename) {
		try {
			XMLWriter writer;
			writer = new XMLWriter(new FileWriter(filename));

			Document doc = null;
			Element root = null;
			Element element = null;

			doc = DocumentHelper.createDocument();
			root = doc.addElement("configuration");
			root.addText("\n");

			for (int i = 0; i < getRowCount(); i++) {
				root.addText("\t");
				element = root.addElement(getValueAt(i, 0).toString());
				element.addText(getValueAt(i, 1).toString());
				root.addText("\n");
			}

			writer.write(doc);
			writer.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void injectParameter(String parameter, String value) {
		for (int i = 0; i < getRowCount(); i++) {
			String chaine = (String) getValueAt(i, 1);
			System.out.println(chaine + "-"
					+ chaine.matches(".*" + parameter + ".*"));

			if (chaine.matches(".*" + parameter + ".*")) {
				setValueAt(chaine.replaceAll(parameter, value), i, 1);
				((AbstractTableModel) tableModel).fireTableDataChanged();
			}
		}
	}

	@Override
	public Object getValueAt(int ligne, int colonne) {
		return ((String[]) (elementList.get(ligne)))[colonne];
	}

	class MonTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		
		public int getColumnCount() {
			return 2;
		}

		public int getRowCount() {
			return elementList.size();
		}

		public Object getValueAt(int ligne, int colonne) {
			return ((String[]) (elementList.get(ligne)))[colonne];
		}

		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			String[] ligne;
			ligne = (String[]) elementList.get(rowIndex);
			ligne[columnIndex] = value.toString();
			elementList.set(rowIndex, ligne);
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return true;
		}
	}
}