package bogg;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.Mouvement;
import fr.xcompta.core.xcontext.XContextInterface;

public class IfDeLettrage extends JInternalFrame {
	private static final long serialVersionUID = 1L;

	protected JPanel panel = new JPanel();
	protected JPanel panel1 = new JPanel();
	protected JPanel panel2 = new JPanel();
	protected JScrollPane panel3 = new JScrollPane();

	protected Dimension size = new Dimension(950, 450);

	protected JComboBox comboCompte;

	protected TableModel tableModel;
	protected TableColumnModel columnModel;
	protected JTable tableDeLettrage = new JTable();

	protected List detailDeLettrage;

	protected Compte compte;
	protected XContextInterface xContext;

	protected int columnClicked;
	protected int rowClicked;
	protected JPopupMenu popMenu = new JPopupMenu();
	protected JMenuItem menuItemDelettrer = new JMenuItem("Delettrer");
	protected JMenuItem menuItemActualiser = new JMenuItem("Actualiser");

	protected final String[] titreColonne = { "Mouvement", "Journal", "Date", "Libell\u00E9", "\u00E9criture",
			"D\u00E9bit", "Cr\u00E9dit", "Lettre", "Solde" };

	public IfDeLettrage(XContextInterface xContext) {
		this.xContext = xContext;

		init();
	}

	private void init() {
		setTitle("Delettrage");
		setSize(size);
		setClosable(true);
		setIconifiable(true);
		setResizable(true);
		setMaximizable(true);

		comboCompte = new JComboBox(xContext.getComptes().toArray());
		comboCompte.setSelectedIndex(0);
		try {
			detailDeLettrage = xContext
					.getDeLettrage(xContext.getCompte(((Compte) comboCompte.getSelectedItem()).getNumero()));

			tableDeLettrage.getTableHeader().setReorderingAllowed(false);
			tableModel = new MonTableModel();
			columnModel = new DefaultTableColumnModel();

			TableColumn column1 = new TableColumn(0, 100);
			column1.setHeaderValue(titreColonne[0]);
			columnModel.addColumn(column1);

			TableColumn column2 = new TableColumn(1, 100);
			column2.setHeaderValue(titreColonne[1]);
			columnModel.addColumn(column2);

			TableColumn column3 = new TableColumn(2, 200);
			column3.setHeaderValue(titreColonne[2]);
			columnModel.addColumn(column3);

			TableColumn column4 = new TableColumn(3, 600);
			column4.setHeaderValue(titreColonne[3]);
			columnModel.addColumn(column4);

			TableColumn column5 = new TableColumn(4, 150);
			column5.setHeaderValue(titreColonne[4]);
			columnModel.addColumn(column5);

			TableColumn column6 = new TableColumn(5, 250);
			column6.setHeaderValue(titreColonne[5]);
			columnModel.addColumn(column6);

			TableColumn column7 = new TableColumn(6, 250);
			column7.setHeaderValue(titreColonne[6]);
			columnModel.addColumn(column7);

			TableColumn column8 = new TableColumn(7, 100);
			column8.setHeaderValue(titreColonne[7]);
			columnModel.addColumn(column8);

			TableColumn column9 = new TableColumn(8, 250);
			column9.setHeaderValue(titreColonne[8]);
			columnModel.addColumn(column9);

			tableDeLettrage.setModel(tableModel);
			tableDeLettrage.setColumnModel(columnModel);

//		tableDeLettrage.setDefaultRenderer(Object.class, new BoggCellRender());
			// //Ca fait planter

		} catch (Exception e) {

			// TODO manuallyu generated block
			e.printStackTrace();

		}

		panel1.add(new JLabel("Compte : "));
		panel1.add(comboCompte);
		comboCompte.setToolTipText("Compte");

		panel3.setViewportView(tableDeLettrage);

		panel.add(panel1);
		panel.add(panel2);
		panel.add(panel3);

		setContentPane(panel);
		setVisible(true);
		pack();

		panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		popMenu.add(menuItemDelettrer);
		popMenu.add(menuItemActualiser);

		comboCompte.addActionListener(event -> {
			try {
				detailDeLettrage = xContext
						.getDeLettrage(xContext.getCompte(((Compte) comboCompte.getSelectedItem()).getNumero()));
				((AbstractTableModel) tableModel).fireTableDataChanged();
			} catch (Exception ey) {
				// TODO: handle exception
				ey.printStackTrace();

			}
		});

		// gestion du clavier et de la sourie
		tableDeLettrage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_CONTEXT_MENU) {
					columnClicked = tableDeLettrage.getSelectedColumn();
					rowClicked = tableDeLettrage.getSelectedRow();

					popMenu.show(ke.getComponent(), 0, 0);
				}
			}
		});

		tableDeLettrage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				Point p = me.getPoint();

				if (SwingUtilities.isRightMouseButton(me)) {
					int clickedRow = tableDeLettrage.rowAtPoint(p);
					int clickedColumn = tableDeLettrage.columnAtPoint(p);

					popMenu.show(me.getComponent(), me.getX(), me.getY());
				}
			}
		});

		menuItemDelettrer.addActionListener(event -> {
			int idLettrage = ((Mouvement) detailDeLettrage.get(rowClicked)).getIdLettrage();

			List<Mouvement> lettrage = new ArrayList<Mouvement>();
			for (int i = 0; i < detailDeLettrage.size(); i++) {
				if (((Mouvement) detailDeLettrage.get(i)).getIdLettrage() == idLettrage)
					lettrage.add((Mouvement) detailDeLettrage.get(i));
			}

			try {
				xContext.deLettrer(lettrage);
				detailDeLettrage = xContext
						.getDeLettrage(xContext.getCompte(((Compte) comboCompte.getSelectedItem()).getNumero()));
				((AbstractTableModel) tableModel).fireTableDataChanged();
				tableDeLettrage.changeSelection(0, 0, false, false);
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		});

		menuItemActualiser.addActionListener(event -> {
			try {
				detailDeLettrage = xContext
						.getDeLettrage(xContext.getCompte(((Compte) comboCompte.getSelectedItem()).getNumero()));
				((AbstractTableModel) tableModel).fireTableDataChanged();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		});
	}

	public TableModel retournerTableModel() {
		return tableModel;
	}

	class MonTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;

		public Object getValueAt(int row, int column) {
			switch (column) {
			case 0:
				return ((Mouvement) detailDeLettrage.get(row)).getId();
			case 1:
				return ((Mouvement) detailDeLettrage.get(row)).getEcriture().getJournal().getCode();
			case 2:
				return ((Mouvement) detailDeLettrage.get(row)).getEcriture().getDate();
			case 3:
				return ((Mouvement) detailDeLettrage.get(row)).getEcriture().getLibelle();
			case 4:
				return ((Mouvement) detailDeLettrage.get(row)).getEcriture().getId();
			case 5:
				if (((Mouvement) detailDeLettrage.get(row)).getMontant() > 0)
					return ((Mouvement) detailDeLettrage.get(row)).getMontant();
				else
					return "";
			case 6:
				if (((Mouvement) detailDeLettrage.get(row)).getMontant() < 0)
					return ((Mouvement) detailDeLettrage.get(row)).getMontant();
				else
					return "";
			case 7:
				return ((Mouvement) detailDeLettrage.get(row)).getIdLettrage();
			case 8:
				if (row == 0)
					return ((Mouvement) detailDeLettrage.get(row)).getMontant();
				else {
					float montant = ((Mouvement) detailDeLettrage.get(row)).getMontant();
					montant += (Float) getValueAt(row - 1, 8);
					return montant;
				}
			default:
				return "Erreur !";
			}
		}

		public int getColumnCount() {
			return titreColonne.length;
		}

		public int getRowCount() {
			return detailDeLettrage.size();
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