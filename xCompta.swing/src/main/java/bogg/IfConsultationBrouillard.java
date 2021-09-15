package bogg;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import fr.xcompta.core.base.Ecriture;
import fr.xcompta.core.xcontext.XContextInterface;

/**
 * @authorFabrice LUNEAU
 * 
 *                fenêtre , affichant toutes les écritures contenues dans le
 *                brouillard Une comboBox permet de choisir le journal Il y a un
 *                champs date début et date fin, pour limiter les résultats
 */
public class IfConsultationBrouillard extends JInternalFrame {
	private static final long serialVersionUID = 1L;

	protected JPanel panel = new JPanel();
	protected JPanel panel1 = new JPanel();
	protected JPanel panel2 = new JPanel();
	protected JScrollPane panel3 = new JScrollPane();

	protected Dimension size = new Dimension(800, 450);

	protected BComboBoxJournaux comboBoxJournaux;

	protected FieldDate dateDebut = new FieldDate();
	protected FieldDate dateFin = new FieldDate();

	protected JButton boutonValider = new JButton("Tout valider");

	protected TableModel tableModel;
	protected TableColumnModel columnModel;
	protected JTable tableBrouillard = new JTable();

	protected List brouillard = null;

	protected XContextInterface xContext;
	protected Ecriture ecritureSelected;

	protected int columnClicked;
	protected int rowClicked;
	protected JPopupMenu popMenu = new JPopupMenu();
	protected JMenuItem menuItemValider = new JMenuItem("Valider");
	protected JMenuItem menuItemModifier = new JMenuItem("Modifier");
	protected JMenuItem menuItemSuppimer = new JMenuItem("Supprimer");
	protected JMenuItem menuItemActualiser = new JMenuItem("Actualiser");
	protected JMenuItem menuItemContrepasser = new JMenuItem("Contrepasser");
	protected JMenuItem menuItemImprimer = new JMenuItem("Imprimer");

	public IfConsultationBrouillard(XContextInterface xContext) {
		this.xContext = xContext;
		// brouillard = xContext.getBrouillard(journal, debut, fin);2

		comboBoxJournaux = new BComboBoxJournaux(xContext);
		comboBoxJournaux.setAllOption(true);
		comboBoxJournaux.setSelectedIndex(0);

		panel1.add(new JLabel("Journal : "));
		panel1.add(comboBoxJournaux);
		comboBoxJournaux.setToolTipText("Journal");

		panel2.add(new JLabel("D�but : "));
		panel2.add(dateDebut);
		dateDebut.setToolTipText("Déut");

		panel2.add(new JLabel("Fin : "));
		panel2.add(dateFin);
		dateFin.setToolTipText("Fin");

		panel2.add(boutonValider);

		panel3.add(tableBrouillard);

		panel.add(panel1);
		panel.add(panel2);
		panel.add(panel3);

		panel3.setViewportView(tableBrouillard);

		panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		setContentPane(panel);

		setVisible(true);

//		initialiserTable();
//		initialiserMenuPopUp();

		pack();
//	}
		/*
		 * private void initialiserTable() {
		 * tableBrouillard.getTableHeader().setReorderingAllowed(false); tableModel =
		 * new MonTableModel(); columnModel = new DefaultTableColumnModel();
		 * 
		 * TableColumn column1 = new TableColumn(0, 75);
		 * column1.setHeaderValue("Journal"); columnModel.addColumn(column1);
		 * 
		 * TableColumn column2 = new TableColumn(1, 100);
		 * column2.setHeaderValue("Date"); columnModel.addColumn(column2);
		 * 
		 * TableColumn column3 = new TableColumn(2, 250);
		 * column3.setHeaderValue("Libellé"); columnModel.addColumn(column3);
		 * 
		 * TableColumn column4 = new TableColumn(3, 100);
		 * column4.setHeaderValue("Compte"); columnModel.addColumn(column4);
		 * 
		 * TableColumn column5 = new TableColumn(4, 250);
		 * column5.setHeaderValue("Libellécompte"); columnModel.addColumn(column5);
		 * 
		 * TableColumn column6 = new TableColumn(5, 100);
		 * column6.setHeaderValue("Déit"); columnModel.addColumn(column6);
		 * 
		 * TableColumn column7 = new TableColumn(6, 100);
		 * column7.setHeaderValue("Cr�dit"); columnModel.addColumn(column7);
		 * 
		 * TableColumn column8 = new TableColumn(7, 50);
		 * column8.setHeaderValue("N° Ecriture"); columnModel.addColumn(column8);
		 * 
		 * tableBrouillard.setModel(tableModel);
		 * tableBrouillard.setColumnModel(columnModel); //
		 * tableBrouillard.setDefaultRenderer(Object.class, new // BoggCellRender()); }
		 * 
		 * private void initialiserMenuPopUp() { popMenu.add(menuItemActualiser);
		 * popMenu.add(menuItemModifier); popMenu.add(menuItemSuppimer);
		 * popMenu.add(menuItemValider); popMenu.add(menuItemContrepasser);
		 * popMenu.add(menuItemImprimer);
		 * 
		 * dateDebut.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { actualiser(); } });
		 * 
		 * dateFin.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { actualiser(); } });
		 * 
		 * menuItemActualiser.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { actualiser(); } });
		 * 
		 * menuItemModifier.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { JInternalFrame iframe = new
		 * IfEcritureSaisie(xContext, (EcritureDeBrouillard) ecritureSelected);
		 * ((JDesktopPane) getParent()).add(iframe); iframe.setVisible(true);
		 * iframe.toFront(); } });
		 * 
		 * menuItemSuppimer.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { Integer ecritureId = ((EcritureDeBrouillard)
		 * ecritureSelected).getId(); xContext.supprimerEcriture(ecritureId);
		 * actualiser(); } });
		 * 
		 * menuItemValider.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) {
		 * xContext.validerEcriture((EcritureDeecritureSelected)); actualiser(); } });
		 * 
		 * menuItemContrepasser.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { JInternalFrame iframe = new
		 * IfEcritureSaisie(xContext, ecritureSelected.contrepasser()); ((JDesktopPane)
		 * getParent()).add(iframe); iframe.setVisible(true); iframe.toFront(); } });
		 * 
		 * menuItemImprimer.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { try { tableBrouillard.print(); } catch
		 * (Exception ex) { System.out.println(ex); } } });
		 * 
		 * comboBoxJournaux.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent ae) { actualiser(); } });
		 * 
		 * boutonValider.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent ae) { List<Ecriture> ecritures =
		 * getEcritureList();
		 * 
		 * for (Ecriture ecriture : ecritures) { xContext.validerEcriture(ecriture); }
		 * 
		 * actualiser(); } }); // gestion du clavier et de la sourie addKeyListener(new
		 * KeyAdapter() {
		 * 
		 * @Override public void keyReleased(KeyEvent ke) { if (ke.getKeyCode() ==
		 * KeyEvent.VK_CONTEXT_MENU) { // columnClicked = getSelectedColumn(); //
		 * rowClicked = getSelectedRow();
		 * 
		 * popMenu.show(ke.getComponent(), 0, 0); } } });
		 * tableBrouillard.addMouseListener(new MouseAdapter() {
		 * 
		 * @Override public void mouseClicked(MouseEvent me) { Point p = me.getPoint();
		 * columnClicked = tableBrouillard.columnAtPoint(p); rowClicked =
		 * tableBrouillard.rowAtPoint(p); ecritureSelected =
		 * xContext.getEcriture(((Mouvement)
		 * brouillard.get(rowClicked)).getEcriture().getId());
		 * 
		 * menuItemModifier.setEnabled(true); menuItemValider.setEnabled(true);
		 * menuItemSuppimer.setEnabled(true);
		 * 
		 * if (SwingUtilities.isRightMouseButton(me)) popMenu.show(me.getComponent(),
		 * me.getX(), me.getY()); } }); }
		 * 
		 * public void actualiser() { // brouillard = xContext.getBrouillard((Journal)
		 * comboBoxJournaux.getSelectedItem(), dateDebut.getDate(), dateFin.getDate();
		 * ((AbstractTableModel) tableModel).fireTableDataChanged();
		 * tableBrouillard.changeSelection(0, 0, false, false);// Attention placer //
		 * ceci dans le // table model }
		 * 
		 * public List<Ecriture> getEcritureList() { /* List<Integer> listEcriture = new
		 * ArrayList<Integer>();
		 * 
		 * for (int i = 0; i < tableBrouillard.getRowCount(); i++) { int id =
		 * (((Mouvement) brouillard.get(i)).getEcriture().getId()); boolean trouve =
		 * false;
		 * 
		 * for (int j = 0; j < listEcriture.size(); j++) { if (listEcriture.get(j) == id
		 * && trouve == false) trouve = true; }
		 * 
		 * if (!trouve) listEcriture.add(id); } return listEcriture;
		 */
//		return null;
	}

//	class MonTableModel extends AbstractTableModel {
//		private static final long serialVersionUID = 1L;

//		public Object getValueAt(int row, int column) {
//			switch (column) {
//			case 0:
//				return ((Mouvement) brouillard.get(row)).getEcriture().getJournal().getCode();
//			case 1:
//				return ((Mouvement) brouillard.get(row)).getEcriture().getDate();
//			case 2:
//				return ((Mouvement) brouillard.get(row)).getEcriture().getLibelle();
//			case 3:
//				return Compte.formatNumero(((Mouvement) brouillard.get(row)).getCompte().getNumero(),
//						preferences.nbDigitCompte);
//			case 4:
//				return ((Mouvement) brouillard.get(row)).getCompte().getLibelle();
//			case 5:
//				if (((Mouvement) brouillard.get(row)).getMontant() > 0)
//					return ((Mouvement) brouillard.get(row)).getMontant();
//				else
//					return "";
//			case 6:
//				if (((Mouvement) brouillard.get(row)).getMontant() < 0)
//					return ((Mouvement) brouillard.get(row)).getMontant();
//				else
//					return "";
//			case 7:
//				return ((Mouvement) brouillard.get(row)).getEcriture().getId();
//			default:
//				return "Erreur !";
//			}
}

//		public int getColumnCount() {
//			return 9;
//		}

//		public int getRowCount() {
//			if (brouillard != null) {
//				return brouillard.size();
//			} else {
//				return 0;
//			}
//		}

//		@Override
//		public boolean isCellEditable(int row, int column) {
//			return false;
//		}
//	}

//	@Override
//	public String toString() {
//		return getTitle();
//	}
//	*/
//}

//isoler le code d'initialisation de la fenetre et du menu pop up
//le menu pop up ne marche pas
//a l'initialisationvidela table est vide