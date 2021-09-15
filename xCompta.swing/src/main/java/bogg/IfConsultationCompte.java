package bogg;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
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
import fr.xcompta.core.base.Ecriture;
import fr.xcompta.core.base.EcritureDeBrouillard;
import fr.xcompta.core.base.EcritureValidee;
import fr.xcompta.core.base.Mouvement;
import fr.xcompta.core.base.exception.ValidationEcritureException;
import fr.xcompta.core.xcontext.XContextInterface;
import fr.xcompta.core.xcontext.dao.exception.XComptaObjetIntrouvableException;

/**
 * @author Fabrice LUNEAU
 * 
 *         Fenêtre contenant les mouvements d'un compte
 * 
 */
public class IfConsultationCompte extends JInternalFrame {
	//private static final org.slf4j.Logger logger = 	.getLogger		.class);
	
	private static final long serialVersionUID = 1L;
	protected JPanel panel = new JPanel();
	protected JPanel panel1 = new JPanel();
	protected JPanel panel2 = new JPanel();
	protected JScrollPane panel3 = new JScrollPane();

	protected Dimension size = new Dimension(800, 600);

	protected JComboBox<Compte> comboComptes;
	protected FieldDate champsDateDebut = new FieldDate();
	protected FieldDate champsDateFin = new FieldDate();
	protected JCheckBox checkBoxBrouillard = new JCheckBox();

	protected TableModel tableModel;
	protected TableColumnModel columnModel;
	protected JTable tableCompte = new JTable();

	protected List detailCompte;

	protected XContextInterface xContext;

	protected Ecriture ecritureSelected;
	protected int columnClicked;
	protected int rowClicked;

	protected JPopupMenu popMenu = new JPopupMenu();
	protected JMenuItem menuItemAfficher = new JMenuItem("Afficher");
	protected JMenuItem menuItemActualiser = new JMenuItem("Actualiser");
	protected JMenuItem menuItemValider = new JMenuItem("Valider");
	protected JMenuItem menuItemContrepasser = new JMenuItem("Contrepasser");
	protected JMenuItem menuItemImprimer = new JMenuItem("Imprimer");

	private void init() {
		this.setTitle("Consultation des comptes");
		this.setSize(size);
		this.setClosable(true);
		this.setIconifiable(true);
		this.setResizable(true);
		this.setMaximizable(true);

		tableCompte.getTableHeader().setReorderingAllowed(false);
		tableModel = new MonTableModel();
		columnModel = new DefaultTableColumnModel();

		tableCompte.setModel(tableModel);
		tableCompte.setColumnModel(columnModel);

		TableColumn column1 = new TableColumn(0, 75);
		column1.setHeaderValue("Journal");
		columnModel.addColumn(column1);

		TableColumn column2 = new TableColumn(1, 150);
		column2.setHeaderValue("Date");
		columnModel.addColumn(column2);

		TableColumn column3 = new TableColumn(2, 300);
		column3.setHeaderValue("Libellé");
		columnModel.addColumn(column3);

		TableColumn column4 = new TableColumn(3, 100);
		column4.setHeaderValue("Compte");
		columnModel.addColumn(column4);

		TableColumn column5 = new TableColumn(4, 250);
		column5.setHeaderValue("Libellé compte");
		columnModel.addColumn(column5);

		TableColumn column6 = new TableColumn(5, 100);
		column6.setHeaderValue("Débit");
		columnModel.addColumn(column6);

		TableColumn column7 = new TableColumn(6, 100);
		column7.setHeaderValue("Crédit");
		columnModel.addColumn(column7);

		TableColumn column8 = new TableColumn(7, 50);
		column8.setHeaderValue("N° Ecriture");
		columnModel.addColumn(column8);

		TableColumn column9 = new TableColumn(8, 100);
		column9.setHeaderValue("Statut");
		columnModel.addColumn(column9);

		tableCompte.setModel(tableModel);
		tableCompte.setColumnModel(columnModel);

		panel1.add(new JLabel("Journal : "));
		panel1.add(comboComptes);
		comboComptes.setToolTipText("Journal");

		panel2.add(new JLabel("Date de début : "));
		panel2.add(champsDateDebut);
		champsDateDebut.setToolTipText("Date de début");

		panel2.add(new JLabel("Date de fin : "));
		panel2.add(champsDateFin);
		champsDateFin.setToolTipText("Date de fin");

		panel2.add(new JLabel("inclure le brouillard"));
		panel2.add(checkBoxBrouillard);
		checkBoxBrouillard.setToolTipText("Inclure le brouillard");

		panel3.add(tableCompte);
		tableCompte.setToolTipText("Liste des mouvements");

		panel.add(panel1);
		panel.add(panel2);
		panel.add(panel3);

		panel3.setViewportView(tableCompte);

		panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		setContentPane(panel);
		pack();

		setVisible(true);

		popMenu.add(menuItemAfficher);
		popMenu.add(menuItemActualiser);
		popMenu.add(menuItemValider);
		popMenu.add(menuItemContrepasser);
		popMenu.add(menuItemImprimer);

		champsDateDebut.addActionListener(
event -> {
				actualiser();
		});

		champsDateFin.addActionListener(
event -> {
				actualiser();
		});

		menuItemAfficher.addActionListener(
event -> {
				JInternalFrame iframe;

				if (ecritureSelected.getClass() == EcritureDeBrouillard.class) {
					iframe = new BFormulaireEcriture(xContext, (EcritureDeBrouillard) ecritureSelected);
				} else if (ecritureSelected.getClass() == EcritureValidee.class) {
					iframe = new IfEcritureConsultation(xContext, (EcritureValidee) ecritureSelected);
				} else {
					return;
				}

				((JDesktopPane) getParent()).add(iframe);
				iframe.setVisible(true);
				iframe.toFront();
				iframe.requestFocus();
		});

		menuItemActualiser.addActionListener(
event -> {
				actualiser();
		});

		menuItemValider.addActionListener(
			event -> { 
				try {
					xContext.validerEcriture((EcritureDeBrouillard)ecritureSelected);
				} catch (ValidationEcritureException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				actualiser();
		});

		menuItemContrepasser.addActionListener(
event -> {
				JInternalFrame iframe = new BFormulaireEcriture(xContext, ecritureSelected.contrepasser());
				((JDesktopPane) getParent()).add(iframe);
				iframe.setVisible(true);
				iframe.toFront();
		});

		menuItemImprimer.addActionListener(
				event -> {
				try {
					tableCompte.print();
				} catch (Exception ex) {
					System.out.println(ex);
				}
		});

		comboComptes.addActionListener(
event -> {
				actualiser();
		});

		checkBoxBrouillard.addActionListener(
				event -> {
				actualiser();
		});

		// gestion du clavier et de la sourie
		tableCompte.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				try {
				if (ke.getKeyCode() == KeyEvent.VK_CONTEXT_MENU) {
					columnClicked = tableCompte.getSelectedColumn();
					rowClicked = tableCompte.getSelectedRow();

					ecritureSelected = xContext
							.getEcriture(((Mouvement) detailCompte.get(rowClicked)).getEcriture().getId());

					popMenu.show(ke.getComponent(), 0, 0);
				}
			} catch(XComptaObjetIntrouvableException e) {
			//Todo manually generated block	
			}
				
			}
		});

		tableCompte.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				try {
				Point p = me.getPoint();
				columnClicked = tableCompte.columnAtPoint(p);
				rowClicked = tableCompte.rowAtPoint(p);

				ecritureSelected = xContext
						.getEcriture(((Mouvement) detailCompte.get(rowClicked)).getEcriture().getId());

				if (SwingUtilities.isRightMouseButton(me))
					popMenu.show(me.getComponent(), me.getX(), me.getY());
			} catch(XComptaObjetIntrouvableException e) {
				//Todo faire quelque chose
				
			}
				
			}
		});
	}

	public void actualiser() {
//		detailCompte = xContext.getDetailCompte((Compte) comboComptes.getSelectedItem(),
//				checkBoxBrouillard.isSelected(), champsDateDebut.getDate(), champsDateFin.getDate());
		((AbstractTableModel) tableModel).fireTableDataChanged();
		tableCompte.changeSelection(0, 0, false, false);
	}

	class MonTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;

		public Object getValueAt(int row, int column) {
			try {
			switch (column) {
			case 0:
				return ((Mouvement) detailCompte.get(row)).getEcriture().getJournal().getCode();
			case 1:
				return ((Mouvement) detailCompte.get(row)).getEcriture().getDate();
			case 2:
				return ((Mouvement) detailCompte.get(row)).getEcriture().getLibelle();
			case 3:
				return fr.xcompta.core.base.Compte.formatNumero(
						((Mouvement) detailCompte.get(row)).getCompte().getNumero(), preferences.nbDigitCompte);
			case 4:
				return ((Mouvement) detailCompte.get(row)).getCompte().getLibelle();
			case 5:
				if (((Mouvement) detailCompte.get(row)).getMontant() > 0)
					return ((Mouvement) detailCompte.get(row)).getMontant();
				else
					return "";
			case 6:
				if (((Mouvement) detailCompte.get(row)).getMontant() < 0)
					return ((Mouvement) detailCompte.get(row)).getMontant();
				else
					return "";
			case 7:
				return ((Mouvement) detailCompte.get(row)).getEcriture().getId();
			case 8:
				int id = ((Mouvement) detailCompte.get(row)).getEcriture().getId();
				Ecriture ecriture = xContext.getEcriture(id);

				if (ecriture.getClass() == EcritureValidee.class)
					return "Validée";
				else
					return "Préenregistrée";
			default:
				return "Erreur !";
			}
		} catch(XComptaObjetIntrouvableException e) {
			return "erreur"; 
		}
			
		}

		public int getColumnCount() {
			return 9;
		}

		public int getRowCount() {
			return detailCompte.size();
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