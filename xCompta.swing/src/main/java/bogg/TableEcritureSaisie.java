package bogg;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
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
import fr.xcompta.core.base.Mouvement;
import fr.xcompta.core.xcontext.XContextInterface;

/**
 * @author Fabrice LUNEAU
 * 
 *         JTable permettant de saisir les mouvement d'une Ecriture. A partir
 *         d'un objet Société
 */
/**
 * @author Fabrice
 *
 */
/**
 * @author Fabrice
 * 
 */
/**
 * @author Fabrice
 * 
 */
public class TableEcritureSaisie extends JTable {
	private static final long serialVersionUID = 1L;

	protected TableModel tableModel;
	protected TableColumnModel columnModel;

	protected XContextInterface xContext;
	protected List<Compte> comptes;
	protected EcritureDeBrouillard ecriture;

	protected int columnClicked;
	protected int rowClicked;

	protected JPopupMenu popUpMenu = new JPopupMenu();
	protected JMenuItem menuItemCopier = new JMenuItem("Copier ligne");
	protected JMenuItem menuItemCouper = new JMenuItem("Couper ligne");
	protected JMenuItem menuItemColler = new JMenuItem("Coller ligne");
	protected JMenuItem menuItemSupprimer = new JMenuItem("Suprrimer ligne");
	protected JMenuItem menuItemInserer = new JMenuItem("Insérer une ligne");
	protected JMenuItem menuItemContrepartie = new JMenuItem("contrepartie");

	protected Mouvement pressePapier = null;

	protected final String[] titreColonne = { "Compte", "Libellé", "Débit", "Crédit" };

	/**
	 * @param xContext
	 */
	public TableEcritureSaisie(XContextInterface xContext) {
		this.xContext = xContext;
		comptes = xContext.getComptes();
		ecriture = new EcritureDeBrouillard();

		initialiserTable();
		initialiserPopMenu();
	}

	public TableEcritureSaisie(XContextInterface xContext, EcritureDeBrouillard ecriture) {
		this.xContext = xContext;
		this.ecriture = ecriture;
		comptes = xContext.getComptes();

		initialiserTable();
		initialiserPopMenu();
	}

	private void initialiserTable() {
		getTableHeader().setReorderingAllowed(false);

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

		setModel(tableModel);
		setColumnModel(columnModel);

		setEnabled(true);

		setRowSelectionAllowed(false);
		setColumnSelectionAllowed(false);
		setCellSelectionEnabled(true);

		changeSelection(0, 0, false, false);
	}

	private void initialiserPopMenu() {
		// Création du menu
		popUpMenu.add(menuItemCopier);
		popUpMenu.add(menuItemColler);
		popUpMenu.add(menuItemCouper);
		popUpMenu.add(menuItemInserer);
		popUpMenu.add(menuItemSupprimer);
		popUpMenu.add(menuItemContrepartie);

		// Gestiondes des événements pour les items du menu
		menuItemInserer.addActionListener(event -> {
			ajouterLigne(rowClicked);
		});

		menuItemSupprimer.addActionListener(event -> {
			supprimerLigne(rowClicked);
		});

		menuItemCopier.addActionListener(event -> {
			try {
				pressePapier = ecriture.getMouvements().get(rowClicked);
			} catch (NullPointerException npe) {
				// todo faire quelque chose
			}
		});

		menuItemCouper.addActionListener(event -> {
			pressePapier = ecriture.getMouvements().get(rowClicked);
			ecriture.supprimerMouvement(rowClicked);
			((AbstractTableModel) tableModel).fireTableDataChanged();
		});

		menuItemColler.addActionListener(event -> {

//					ecriture.ajouterMouvementA(Mouvement(rowClicked, new Mouvement()));

			ecriture.getMouvements().set(rowClicked, pressePapier);
			pressePapier = null;
			((AbstractTableModel) tableModel).fireTableDataChanged();
		});

		menuItemContrepartie.addActionListener(event -> {
			try {
				if (!ecriture.estEquilibree()) {
					Mouvement mouvement = ecriture.getMouvements().get(rowClicked);
					mouvement.setMontant(ecriture.getSolde() * -1);
					((AbstractTableModel) tableModel).fireTableDataChanged();
				}
			} catch (NullPointerException npe) {
				// Todo faire quelque chose
			}
		});
		// Gestion des événements pour le clavier et la sourie

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				if (pressePapier == null) {
					menuItemColler.setEnabled(false);
				} else {
					menuItemColler.setEnabled(true);
				}

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

	public void supprimerLigne(int ligne) {
		ecriture.supprimerMouvement(ligne);
		((AbstractTableModel) tableModel).fireTableDataChanged();
	}

	public void ajouterLigne(int ligne) {
		ecriture.ajouterMouvementA(rowClicked, new Mouvement());
		((AbstractTableModel) tableModel).fireTableDataChanged();
	}

	public void setEcriture(EcritureDeBrouillard ecriture) {
		this.ecriture = ecriture;
		((AbstractTableModel) tableModel).fireTableDataChanged();
		changeSelection(0, 0, false, false);
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

	public EcritureDeBrouillard getEcriture() {
		return ecriture;
	}

	public void effacer() {
		ecriture = new EcritureDeBrouillard();
		((AbstractTableModel) tableModel).fireTableDataChanged();
		changeSelection(0, 0, false, false);
	}

	class MonTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;

		public Ecriture getEcriture() {
			return ecriture;
		}

		public Object getValueAt(int row, int column) {
			if (ecriture.getMouvements() == null)
				return "";

			if (row >= ecriture.getMouvements().size())
				return "";

			Mouvement mouvement = ecriture.getMouvements().get(row);

			switch (column) {
			case 0:
				if (mouvement.getCompte() != null) {
					return Compte.formatNumero(mouvement.getCompte().getNumero(), preferences.nbDigitCompte);
				} else
					return "";
			case 1:
				if (mouvement.getCompte() != null)
					return mouvement.getCompte().getLibelle();
				else
					return "";
			case 2:
				if (mouvement.getMontant() > 0)
					if (mouvement.getMontant() != 0) {
						return preferences.amountFormat.format(mouvement.getMontant());
					}
				return "";
			case 3:
				if (mouvement.getMontant() < 0)
					if (mouvement.getMontant() != 0) {
						return preferences.amountFormat.format(mouvement.getMontant());
					}
				return "";
			default:
				return "Erreur !";
			}
		}

		public int getColumnCount() {
			return titreColonne.length;
		}

		public int getRowCount() {
			if (ecriture.getMouvements() == null)
				return 1;
			else
				return ecriture.getMouvements().size() + 1;
		}

		@Override
		public String getColumnName(int column) {
			return titreColonne[column];
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			if (column == 1) // Seul la column libelle n'est pas editable
				return false;
			else
				return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int,
		 * int)
		 */
		@Override
		public void setValueAt(Object valeur, int row, int column) {
			if (ecriture.getMouvements() == null) {
				ecriture.ajouterMouvementALaFin(new Mouvement());
			}

			if (row >= ecriture.getMouvements().size()) {
				ecriture.ajouterMouvementALaFin(new Mouvement());
			}

			Mouvement mouvement = ecriture.getMouvements().get(row);

			switch (column) {
			case 0:
				Compte compte = null;

				try {
					compte = xContext.getCompte((String) valeur);
				} catch (Exception e) {
					/// mannulaluy toto generated block pas bo du tout
					e.printStackTrace();
				}

				if (compte == null) {
					JOptionPane.showMessageDialog(null, "Ce compte n'existe pas !");
					return;
				} else {
					if (compte.isUtilisable()) {
						mouvement.setCompte(compte);

						fireTableDataChanged();
						changeSelection(row, 2, false, false);
					} else {
						JOptionPane.showMessageDialog(null, "Ce compte n'est pas utlisable !");
					}
				}
				break;
			case 2:
				if (mouvement.getCompte() != null) {
					try {
						valeur = ((String) valeur).replace(',', '.'); // internationalisation

						mouvement.setMontant(Float.parseFloat((String) valeur));

						fireTableDataChanged();
						changeSelection(row + 1, 0, false, false);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Saisie non valide !");
					}
				}
				break;
			case 3:
				if (mouvement.getCompte() != null) {
					try {
						valeur = ((String) valeur).replace(',', '.'); // internationalisation
						mouvement.setMontant(Float.parseFloat((String) valeur) * -1);

						fireTableDataChanged();
						changeSelection(row + 1, 0, false, false);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Saisie non valide !");
					}
				}
				break;
			default:
				// erreur
			}
		}
	}
}