package bogg;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.Mouvement;
import fr.xcompta.core.xcontext.XContextInterface;

public class IfLettrage extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel panel = new JPanel();
	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JScrollPane panel3 = new JScrollPane();
	private JPanel panel4 = new JPanel();
	private JPanel panel5 = new JPanel();

	private Dimension size = new Dimension(950, 450);

	private JComboBox<Compte> comboCompte;
	
	private TableModel tableModel;
	private TableColumnModel columnModel;
	private JTable tableLettrage = new JTable();
	
	private JTextField textFieldSoldeLettrage = new JTextField(15);
	
	private JButton buttonLettrer = new JButton("Lettrer");
	private JButton buttonAnnuler = new JButton("Annuler");

	private List detailLettrage;
	private HashMap detailPointage = new HashMap();


	private final String[] titreColonne = { "Mouvement", "Journal", "Date",
			"Libell\u00E9", "\u00E9criture", "D\u00E9bit", "Cr\u00E9dit",
			"Lettre", "Solde" };
	
	private XContextInterface xContext;

	public IfLettrage(XContextInterface xContext) {
		this.xContext = xContext;
		
		init();
	}
	
	public IfLettrage(XContextInterface xContext, Compte compte) {
		this.xContext = xContext;
		//this.compte = compte;//pour selectionner un compte au démarrage
		
		init();
	}

	private void init() {
		setTitle("Lettrage");
		setSize(size);
		
		setClosable(true);
		setIconifiable(true);
		setResizable(true);
		setMaximizable(true);

		tableLettrage.getTableHeader().setReorderingAllowed(false);

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

		tableLettrage.setModel(tableModel);
		tableLettrage.setColumnModel(columnModel);

		// tableLettrage.setDefaultRenderer(Object.class, new
		// BoggCellRender());//ca fait vraiment planter

		comboCompte = new JComboBox(xContext.getComptes().toArray());
		
		//Selectionner un compte si passer en argument
		comboCompte.setSelectedIndex(0);

		try {
		detailLettrage = xContext
				.getLettrage(xContext.getCompte(((Compte) comboCompte
						.getSelectedItem()).getNumero()));

		panel1.add(new JLabel("Compte : "));
		panel1.add(comboCompte);
		comboCompte.setToolTipText("Compte");

		panel3.setViewportView(tableLettrage);
		panel4.add(textFieldSoldeLettrage);
		new JLabel("Solde Lettrage").setLabelFor(textFieldSoldeLettrage);
		
		textFieldSoldeLettrage.setEditable(false);
		
		panel5.add(buttonLettrer);
		panel5.add(buttonAnnuler);
		buttonLettrer.setEnabled(false);

		panel.add(panel1);
		panel.add(panel2);
		panel.add(panel3);
		panel.add(panel4);
		panel.add(panel5);

		setContentPane(panel);
		setVisible(true);
		pack();

		panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel4.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panel5.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		miseAjourTotaux();

		comboCompte.addActionListener(
				event -> {
				try {
				detailLettrage = xContext.getLettrage(xContext
						.getCompte(((Compte) comboCompte.getSelectedItem())
								.getNumero()));
				((AbstractTableModel) tableModel).fireTableDataChanged();
				//tableLettrage.changeSelection(0, 0, false, false);

				} catch (Exception e) {
					// TODO: handle exception
				}
		});

		tableLettrage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				Point p = me.getPoint();

				if (SwingUtilities.isLeftMouseButton(me)
						&& me.getClickCount() == 2) {
					int clickedRow = tableLettrage.rowAtPoint(p);
					int clickedColumn = tableLettrage.columnAtPoint(p);
				}
			}
		});
		
		tableLettrage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				int row = tableLettrage.getSelectedRow();
				int column = tableLettrage.getSelectedColumn();
				
				if (ke.getKeyCode() == KeyEvent.VK_SPACE ||
						ke.getKeyCode() == KeyEvent.VK_ENTER) {
				lettrer(row);
				miseAjourTotaux();
			}
			}
			}
);

		buttonLettrer.addActionListener(
				event -> {
				List<Mouvement> mouvements = new ArrayList<Mouvement>();

				for (int i = 0; i < detailPointage.size(); i++) {
					if ((boolean) detailPointage.get(i))
						mouvements.add((Mouvement) detailLettrage.get(i));
				}
				
				try {

				xContext.lettrer(mouvements);
				detailLettrage = xContext.getLettrage(xContext
						.getCompte(((Compte) comboCompte.getSelectedItem())
								.getNumero()));
				detailPointage = new HashMap();
				buttonLettrer.setEnabled(false);

				for (int i = 0; i < detailLettrage.size(); i++)
					detailPointage.put(i, false);
				((AbstractTableModel) tableModel).fireTableDataChanged();
				miseAjourTotaux();
				}catch (Exception e) {
					// TODO: handle exception
				}
 		});

		buttonAnnuler.addActionListener(
				event -> {
				for (int i = 0; i < detailLettrage.size(); i++)
					detailPointage.put(i, false);
				((AbstractTableModel) tableModel).fireTableDataChanged();
				miseAjourTotaux();
		});

		comboCompte.addActionListener(
				event -> {
				for (int i = 0; i < detailLettrage.size(); i++)
					detailPointage.put(i, false);
				((AbstractTableModel) tableModel).fireTableDataChanged();
				miseAjourTotaux();
		});
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void lettrer(int row){ 
				if (detailPointage.get(row) == null)
					detailPointage.put(row, false);

				if (((boolean) (detailPointage.get(row))))
					detailPointage.put(row, false);
				else
					detailPointage.put(row, true);

				((AbstractTableModel) tableModel).fireTableDataChanged();
				miseAjourTotaux();
			}

	public TableModel getTableModel() {
		return tableModel;
	}

	class MonTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		public Object getValueAt(int row, int column) {
			switch (column) {
			case 0:
				return ((Mouvement) detailLettrage.get(row)).getId();
			case 1:
				return ((Mouvement) detailLettrage.get(row)).getEcriture()
						.getJournal().getCode();
			case 2:
				return ((Mouvement) detailLettrage.get(row)).getEcriture()
						.getDate();
			case 3:
				return ((Mouvement) detailLettrage.get(row)).getEcriture()
						.getLibelle();
			case 4:
				return ((Mouvement) detailLettrage.get(row)).getEcriture()
						.getId();
			case 5:
				if (((Mouvement) detailLettrage.get(row)).getMontant() > 0)
					return ((Mouvement) detailLettrage.get(row)).getMontant();
				else
					return "";
			case 6:
				if (((Mouvement) detailLettrage.get(row)).getMontant() < 0)
					return ((Mouvement) detailLettrage.get(row)).getMontant();
				else
					return "";
			case 7:
				if (detailPointage.get(row) == null)
					detailPointage.put(row, false);

				return  detailPointage.get(row);
			case 8:
				if (row == 0)
					return ((Mouvement) detailLettrage.get(row)).getMontant();
				else {
					float montant = ((Mouvement) detailLettrage.get(row))
							.getMontant();
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
			return detailLettrage.size();
		}

		@Override
		public String getColumnName(int column) {
			return titreColonne[column];
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			//Todo faire quelque chose
		}

@Override
		public Class<?> getColumnClass(int columnIndex) {
			if (columnIndex == 7)
				return Boolean.class;
			return super.getColumnClass(columnIndex);
		}
	}

	private void miseAjourTotaux() {
		//double totalDebit, totalCredit = 0;
		double solde = 0;
		
		try {
		for (int i = 0; i < detailLettrage.size(); i++) {
			if((boolean)detailPointage.get(i)) {
				solde+= ((Mouvement) detailLettrage.get(i))
						.getMontant();

		textFieldSoldeLettrage.setText(preferences.amountFormat.format(solde));

		if (solde == 0) {
			buttonLettrer.setEnabled(true);
		} else {
			buttonLettrer.setEnabled(false);
		}
		}
		}
			} catch(NullPointerException e) {}
		//Todo faire quelque chose
	}

	@Override
	public String toString() {
		return getTitle();
	}
}

//mais Réviser la partie m�tier : le mapping et la classe Compta.Lettrage
//Tous les comptes ne sont pas lettrables 
//Les 401 411 sont lettrables au minimum
//les totaux doivent corespondre  aux lignes cochées
//soit le lettrage en cour
//faire ou veriifier la présence de la méthode isRowChecked pour les lignes coch�es
//faire un menu pop up
//item afficher contrepasser lettrer cocher decocher
