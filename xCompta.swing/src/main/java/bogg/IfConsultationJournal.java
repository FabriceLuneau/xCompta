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
import fr.xcompta.core.xcontext.XContextInterface;
import fr.xcompta.core.xcontext.dao.exception.XComptaObjetIntrouvableException;

public class IfConsultationJournal extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	protected JPanel panel = new JPanel();
	protected JPanel panel1 = new JPanel();
	protected JPanel panel2 = new JPanel();
	protected JScrollPane panel3 = new JScrollPane();

	protected Dimension size = new Dimension(800, 450);

	protected BComboBoxJournaux comboJournaux;
	protected FieldDate dateDebut = new FieldDate();
	protected FieldDate dateFin = new FieldDate();
	protected JCheckBox checkBoxBrouillard = new JCheckBox();

	protected TableModel tableModel;
	protected TableColumnModel columnModel;
	protected JTable tableJournal = new JTable();

	protected List detailJournal;

	protected XContextInterface xContext;

	protected Ecriture ecritureSelected;

	protected int columnClicked;
	protected int rowClicked;
	protected JPopupMenu popMenu = new JPopupMenu();
	protected JMenuItem menuItemAfficher = new JMenuItem("Afficher");
	protected JMenuItem menuItemValider = new JMenuItem("Valider");
	protected JMenuItem menuItemModifier = new JMenuItem("Modifier");
	protected JMenuItem menuItemSuppimer = new JMenuItem("Supprimer");
	protected JMenuItem menuItemActualiser = new JMenuItem("Actualiser");
	protected JMenuItem menuItemContrepasser = new JMenuItem("Contrepasser");
	protected JMenuItem menuItemImprimer = new JMenuItem("Imprimer");

	public IfConsultationJournal(XContextInterface xContext) {
		this.xContext = xContext;
		init();
	}

	private void init() {
		setTitle("Consultation des journaux");
		setSize(size);
		setClosable(true);
		setIconifiable(true);
		setResizable(true);
		setMaximizable(true);

		comboJournaux = new BComboBoxJournaux(xContext);
		comboJournaux.setToolTipText("Journal");

//		detailJournal = xContext.getDetailJournal((Journal) comboJournaux.getSelectedItem(),
//				checkBoxBrouillard.isSelected(), dateDebut.getDate(), dateFin.getDate());

		tableJournal.getTableHeader().setReorderingAllowed(false);
		tableModel = new MonTableModel();
		columnModel = new DefaultTableColumnModel();

		TableColumn column1 = new TableColumn(0, 75);
		column1.setHeaderValue("Journal");
		columnModel.addColumn(column1);

		TableColumn column2 = new TableColumn(1, 100);
		column2.setHeaderValue("Date");
		columnModel.addColumn(column2);

		TableColumn column3 = new TableColumn(2, 250);
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

		tableJournal.setModel(tableModel);
		tableJournal.setColumnModel(columnModel);

		// tableJournal.setDefaultRenderer(Object.class, new

		panel1.add(new JLabel("Journal : "));
		panel1.add(comboJournaux);

		panel2.add(new JLabel("Début : "));
		panel2.add(dateDebut);
		dateDebut.setToolTipText("Début");

		panel2.add(new JLabel("Fin : "));
		panel2.add(dateFin);
		dateFin.setToolTipText("Fin");

		panel2.add(new JLabel("inclure le bouillard"));
		panel2.add(checkBoxBrouillard);
		checkBoxBrouillard.setToolTipText("inclure le brouillard");

		panel3.add(tableJournal);

		panel.add(panel1);
		panel.add(panel2);
		panel.add(panel3);

		panel3.setViewportView(tableJournal);

		panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		setContentPane(panel);
		pack();

		setVisible(true);

		popMenu.add(menuItemActualiser);
		popMenu.add(menuItemAfficher);
		popMenu.add(menuItemModifier);
		popMenu.add(menuItemSuppimer);
		popMenu.add(menuItemValider);
		popMenu.add(menuItemContrepasser);
		popMenu.add(menuItemImprimer);

		dateDebut.addActionListener(
event -> {
				actualiser();
		});

		dateFin.addActionListener(
event -> {
				actualiser();
		});

		menuItemActualiser.addActionListener(
			event -> {
				actualiser();
		});

		menuItemAfficher.addActionListener(
				event -> {
				JInternalFrame iframe = new IfEcritureConsultation(xContext, (EcritureValidee) ecritureSelected);
				((JDesktopPane) getParent()).add(iframe);
				iframe.setVisible(true);
				iframe.toFront();
		});

		menuItemModifier.addActionListener(
event -> {
				JInternalFrame iframe = new BFormulaireEcriture(xContext, (EcritureDeBrouillard) ecritureSelected);
				((JDesktopPane) getParent()).add(iframe);
				iframe.setVisible(true);
				iframe.toFront();
		});

		menuItemSuppimer.addActionListener(
				event -> {
				Integer ecritureId = ((EcritureDeBrouillard) ecritureSelected).getId();

//				xContext.supprimerEcriture(ecritureId);
				actualiser();
		});

		menuItemValider.addActionListener(
				event -> {
//				xContext.validerEcriture(ecritureSelected.getId());
//				xContext.valider(tableecriture.);
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
					tableJournal.print();
				} catch (Exception ex) {
					System.out.println(ex);
				}
		});

		comboJournaux.addActionListener(
				event -> {
				actualiser();
		});

		checkBoxBrouillard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				actualiser();
			}
		});

		// gestion du clavier et de la sourie
		tableJournal.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_CONTEXT_MENU) {
					columnClicked = tableJournal.getSelectedColumn();
					rowClicked = tableJournal.getSelectedRow();

					popMenu.show(ke.getComponent(), 0, 0);
				}
			}
		});

		tableJournal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				try {
				Point p = me.getPoint();
				columnClicked = tableJournal.columnAtPoint(p);
				rowClicked = tableJournal.rowAtPoint(p);
				ecritureSelected = xContext
						.getEcriture(((Mouvement) detailJournal.get(rowClicked)).getEcriture().getId());

				if (ecritureSelected.getClass() == EcritureDeBrouillard.class) {
					menuItemModifier.setEnabled(true);
					menuItemValider.setEnabled(true);
					menuItemSuppimer.setEnabled(true);
					menuItemAfficher.setEnabled(false);
				} else {
					menuItemModifier.setEnabled(false);
					menuItemValider.setEnabled(false);
					menuItemSuppimer.setEnabled(false);
					menuItemAfficher.setEnabled(true);
				}

				if (SwingUtilities.isRightMouseButton(me))
					popMenu.show(me.getComponent(), me.getX(), me.getY());
			} catch(XComptaObjetIntrouvableException e) {
			//Todo manualy generated block	
			}
			}
		});
	}

	public void actualiser() {
//		detailJournal = xContext.getDetailJournal((Journal) comboJournaux.getSelectedItem(),
//				checkBoxBrouillard.isSelected(), dateDebut.getDate(), dateFin.getDate());
		((AbstractTableModel) tableModel).fireTableDataChanged();
		tableJournal.changeSelection(0, 0, false, false);
	}

	class MonTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;

		public Object getValueAt(int row, int column) {
			try {
			switch (column) {
			case 0:
				return ((Mouvement) detailJournal.get(row)).getEcriture().getJournal().getCode();
			case 1:
				return ((Mouvement) detailJournal.get(row)).getEcriture().getDate();
			case 2:
				return ((Mouvement) detailJournal.get(row)).getEcriture().getLibelle();
			case 3:
				return Compte.formatNumero(((Mouvement) detailJournal.get(row)).getCompte().getNumero(),
						preferences.nbDigitCompte);
			case 4:
				return ((Mouvement) detailJournal.get(row)).getCompte().getLibelle();
			case 5:
				if (((Mouvement) detailJournal.get(row)).getMontant() > 0)
					return ((Mouvement) detailJournal.get(row)).getMontant();
				else
					return "";
			case 6:
				if (((Mouvement) detailJournal.get(row)).getMontant() < 0)
					return ((Mouvement) detailJournal.get(row)).getMontant();
				else
					return "";
			case 7:
				return ((Mouvement) detailJournal.get(row)).getEcriture().getId();
			case 8:
				int id = ((Mouvement) detailJournal.get(row)).getEcriture().getId();
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
			return detailJournal.size();
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