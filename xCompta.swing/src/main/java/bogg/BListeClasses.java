package bogg;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.slf4j.LoggerFactory;

import fr.xcompta.core.base.Classe;
import fr.xcompta.core.xcontext.XContextInterface;

/**
 * @author Fabrice
 * 
 * Affiche la liste des classes de comptes pas d'interactions avec les autres boites
 *
 */
public class BListeClasses extends JInternalFrame {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BListeClasses.class);

	private static final long serialVersionUID = 1L;

	private JScrollPane panel = new JScrollPane();
	private JTable tableClasses;

	private XTableModelClasse tableModel;

	private Dimension size = new Dimension(500, 500);

	private XContextInterface xContext;
	private List<Classe> classes;

	
	
	public BListeClasses(XContextInterface xContext) {
		this.xContext = xContext;
		classes = xContext.getClasses();

		init();
	}

	private void init() {
		setTitle("Liste des classes");
		setSize(size);
		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);

		initialiserTable();

		panel.add(tableClasses);
		panel.setViewportView(tableClasses);
		setContentPane(panel);
		pack();

		setVisible(true);
		requestFocusInWindow();
	}

	private void initialiserTable() {
		tableModel = new XTableModelClasse(classes);
		TableColumnModel columnModel = new DefaultTableColumnModel();

		TableColumn column1 = new TableColumn(0, 100);
		column1.setHeaderValue("Num\u00E9ro");
		columnModel.addColumn(column1);

		TableColumn column2 = new TableColumn(1, 500);
		column2.setHeaderValue("Libell\u00E9");
		columnModel.addColumn(column2);

		tableClasses = new JTable(tableModel, columnModel);

		tableClasses.setEnabled(true);
		tableClasses.getTableHeader().setReorderingAllowed(false);

		tableClasses.changeSelection(0, 0, false, false);
		tableClasses.requestFocusInWindow();
	}

	private final class XTableModelClasse extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private String[] titreColonne = { "Numéro", "Libellé"};
		private java.util.List<Classe> classes;

		public XTableModelClasse(List<Classe> classes) {
			this.classes = classes;
		}

		public Object getValueAt(int row, int column) {
			Classe classe = classes.get(row);

			switch (column) {
			case 0:
				return classe.getNumero();
			case 1:
				return classe.getLibelle();
			default:
				return "Erreur !";
			}
		}

		public int getColumnCount() {
			return titreColonne.length;
		}

		public int getRowCount() {
			return classes.size();
		}

		@Override
		public String getColumnName(int column) {
			return titreColonne[column];
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	}

	@Override
	public String toString() {
		return getTitle();
	}
}