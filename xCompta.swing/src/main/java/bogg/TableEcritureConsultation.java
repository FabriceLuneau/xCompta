package bogg;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import fr.xcompta.core.base.Ecriture;
import fr.xcompta.core.base.Mouvement;
import fr.xcompta.core.xcontext.XContextInterface;

public class TableEcritureConsultation extends JTable {
	protected TableModel tableModel;
	private static final long serialVersionUID = 1L;

	protected TableColumnModel columnModel;

	protected XContextInterface xContext;
	protected Ecriture ecriture;

	protected int columnClicked;
	protected int rowClicked;

	protected JPopupMenu popUpMenu = new JPopupMenu();
	protected JMenuItem menuItemCopier = new JMenuItem("Copier");

	protected final String[] titreColonne = { "Compte", "Libellee", "Debit",
			"Credit" };

	public TableEcritureConsultation(Ecriture ecriture) {
		super();
		this.ecriture = ecriture;
		initialiseTable();
	}

	private void initialiseTable() {
		this.getTableHeader().setReorderingAllowed(false);
		tableModel = new MonTableModel();
		columnModel = new DefaultTableColumnModel();

		TableColumn column1 = new TableColumn(0, 200);
		column1.setHeaderValue(titreColonne[0]);
		columnModel.addColumn(column1);
		TableColumn column2 = new TableColumn(1, 1000);
		column2.setHeaderValue(titreColonne[1]);
		columnModel.addColumn(column2);
		TableColumn column3 = new TableColumn(2, 300);
		column3.setHeaderValue(titreColonne[2]);
		columnModel.addColumn(column3);
		TableColumn column4 = new TableColumn(3, 300);
		column4.setHeaderValue(titreColonne[3]);
		columnModel.addColumn(column4);

		this.setModel(tableModel);
		this.setColumnModel(columnModel);
		// this.setDefaultRenderer(Object.class, new BoggCellRender());
		this.setEnabled(true);
		this.setRowSelectionAllowed(false);
		this.setColumnSelectionAllowed(false);
		this.setCellSelectionEnabled(true);
	}

	private void initialiserPopMenu() {
		// Cr�ation du menu
		popUpMenu.add(menuItemCopier);

		// Gestiondes des  évènements pour les items du menu
		menuItemCopier.addActionListener(
				event -> {
				// code

		});

		// gestion du clavier et de la sourie
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_CONTEXT_MENU) {
					columnClicked = getSelectedColumn();
					rowClicked = getSelectedRow();

					popUpMenu.show(ke.getComponent(), 0, 0);
				}
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				Point p = me.getPoint();
				columnClicked = columnAtPoint(p);
				rowClicked = rowAtPoint(p);

				if (SwingUtilities.isRightMouseButton(me))
					popUpMenu.show(me.getComponent(), me.getX(), me.getY());
			}
		});
	}

	public void setEcriture(Ecriture ecritureC) {
		ecriture = ecritureC;
	}

	public TableModel getTableModel() {
		return tableModel;
	}

	public double getTotalDebit() {
		return ecriture.getTotalDebit();
	}

	public double getTotalCredit() {
		return ecriture.getTotalCredit();
	}

	public Ecriture getEcriture() {
		return ecriture;
	}

	class MonTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;

		public Ecriture retournerEcriture() {
			return ecriture;
		}

		public Object getValueAt(int ligne, int colonne) {
			Mouvement mouvement =  ecriture.getMouvements().get(
					ligne);

			switch (colonne) {
			case 0:
				if (mouvement.getCompte() != null)
					return mouvement.getCompte().getNumero();
				else
					return "";
			case 1:
				if (mouvement.getCompte() != null)
					return (mouvement.getCompte().getLibelle());
				else
					return "";
			case 2:
				if (mouvement.getMontant() > 0)
					if (mouvement.getMontant() != 0)
						return mouvement.getMontant();
				return "";
			case 3:
				if (mouvement.getMontant() < 0)
					if (mouvement.getMontant() != 0)
						return mouvement.getMontant() * -1;
				return "";
			default:
				return "Erreur !";
			}
		}

		public int getColumnCount() {
			return titreColonne.length;
		}

		public int getRowCount() {
			return ecriture.getMouvements().size();
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
}