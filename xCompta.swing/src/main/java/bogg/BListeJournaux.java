package bogg;

import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import fr.xcompta.core.base.Journal;
import fr.xcompta.core.xcontext.XContextInterface;

public class BListeJournaux extends JInternalFrame {
	private static final long serialVersionUID = 1L;

	private JScrollPane scrollPane = new JScrollPane();

	private TableModel tableModel;
	private TableColumnModel columnModel;
	private JTable tableJournaux = new JTable();

	private final String[] titreColonne = { "Code", "Libelle" };

	private List<Journal> journaux;
	private XContextInterface xContext;

	
	public BListeJournaux(XContextInterface xContext) {
		this.xContext = xContext;
		this.journaux = xContext.getJournaux();

		setTitle("Liste des journaux");
		setClosable(true);
		setIconifiable(true);
		setResizable(false);
		setMaximizable(true);

		initialiserTable();

		scrollPane.add(tableJournaux);
		scrollPane.setViewportView(tableJournaux);
		setContentPane(scrollPane);

		setVisible(true);
		pack();
	}

	private void initialiserTable() {
		tableModel = new TableModelListeJournaux();

		columnModel = new DefaultTableColumnModel();
		TableColumn column1 = new TableColumn(0, 50);
		column1.setHeaderValue(titreColonne[0]);
		columnModel.addColumn(column1);
		TableColumn column2 = new TableColumn(1, 150);
		column2.setHeaderValue(titreColonne[1]);
		columnModel.addColumn(column2);

		tableJournaux.setModel(tableModel);
		tableJournaux.setColumnModel(columnModel);

		tableJournaux.getTableHeader().setReorderingAllowed(false);
		tableJournaux.setCellSelectionEnabled(true);

		tableJournaux.changeSelection(0, 0, false, false);
		tableJournaux.requestFocusInWindow();
	}

private 	class TableModelListeJournaux extends AbstractTableModel {
		private static final long serialVersionUID = 1L;

		public Object getValueAt(int ligne, int colonne) {
			Journal journal = journaux.get(ligne);

			switch (colonne) {
			case 0:
				return journal.getCode();
			case 1:
				return journal.getLibelle();
			default:
				return "Erreur !";
			}
		}

		public int getColumnCount() {
			return titreColonne.length;
		}

		public int getRowCount() {
			return journaux.size();
		}

		@Override
		public String getColumnName(int colonne) {
			return titreColonne[colonne];
		}

		@Override
		public boolean isCellEditable(int ligne, int colonne) {
			return false;
		}

	}

	@Override
	public String toString() {
		return getTitle();
	}
}

//Pas de menu pop up, int�grer un menu avec les items
//Afficher consulter supprimer
//la fenêtre  ne se ferme pas