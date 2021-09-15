package bogg;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.xcontext.XContextInterface;
import fr.xcompta.core.xcontext.dao.exception.XComptaObjetIntrouvableException;

/**
 * @author Fabrice LUNEAU
 * 
 *         Fen�tre interne, contenant un arbre des comptes, de la soci�t�
 * 
 */
public class IfArbreDesComptes2 extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	protected JScrollPane panel = new JScrollPane();
	protected MutableTreeNode racine = new DefaultMutableTreeNode(
			"Plan de compte");
	protected JTree arbreDesComptes = new JTree(racine);
	protected Dimension size = new Dimension(500, 500);

	protected XContextInterface xContext;

	protected List<Compte> comptes;

	protected int columnClicked;
	protected int rowClicked;

	protected JPopupMenu popUpMenu = new JPopupMenu();
	protected JMenuItem menuItemAfficher = new JMenuItem("Afficher");

	protected int indexNoeudClasse1 = 0;
	protected int indexNoeudClasse2 = 0;
	protected int indexNoeudClasse3 = 0;
	protected int indexNoeudClasse4 = 0;
	protected int indexNoeudClasse5 = 0;
	protected int indexNoeudClasse6 = 0;
	protected int indexNoeudClasse7 = 0;
	protected int indexNoeudClasse8 = 0;
	protected int indexNoeudClasse9 = 0;
	protected int indexNoeudClients = 0;
	protected int indexNoeudFournisseurs = 0;
	protected int indexNoeudFournisseursImmobilisations = 0;

	public IfArbreDesComptes2(XContextInterface xContext) {
		this.comptes = xContext.getComptes();
		
		init();
	}
	
	private void init() {
		try {
		setTitle("Arbre des comptes");
		setSize(size);
		setClosable(true);
		setIconifiable(true);
		setResizable(true);
		setMaximizable(true);

		MutableTreeNode noeudClasse1 = new DefaultMutableTreeNode(
				xContext.getClasse((short) 1));
		MutableTreeNode noeudClasse2 = new DefaultMutableTreeNode(
				xContext.getClasse((short) 2));
		MutableTreeNode noeudClasse3 = new DefaultMutableTreeNode(
				xContext.getClasse((short) 3));
		MutableTreeNode noeudClasse4 = new DefaultMutableTreeNode(
				xContext.getClasse((short) 4));
		MutableTreeNode noeudClasse5 = new DefaultMutableTreeNode(
				xContext.getClasse((short) 5));
		MutableTreeNode noeudClasse6 = new DefaultMutableTreeNode(
				xContext.getClasse((short) 6));
		MutableTreeNode noeudClasse7 = new DefaultMutableTreeNode(
				xContext.getClasse((short) 7));
		MutableTreeNode noeudClasse8 = new DefaultMutableTreeNode(
				xContext.getClasse((short) 8));
		MutableTreeNode noeudClasse9 = new DefaultMutableTreeNode(
				xContext.getClasse((short) 9));

		MutableTreeNode noeudClients = new DefaultMutableTreeNode("411 Clients");
		MutableTreeNode noeudFournisseurs = new DefaultMutableTreeNode(
				"401 Fournisseurs");
		MutableTreeNode noeudFournisseursImmobilisations = new DefaultMutableTreeNode(
				"404 Fournisseurs d'immobilisations");

		racine.insert(noeudClasse1, 0);
		racine.insert(noeudClasse2, 1);
		racine.insert(noeudClasse3, 2);
		racine.insert(noeudClasse4, 3);
		racine.insert(noeudClasse5, 4);
		racine.insert(noeudClasse6, 5);
		racine.insert(noeudClasse7, 6);
		racine.insert(noeudClasse8, 7);
		racine.insert(noeudClasse9, 8);

		String chaineNoeud;

		for (Compte compte:comptes) {
			chaineNoeud = Compte.formatNumero(compte.getNumero(),
					preferences.nbDigitCompte) + " " + compte.getLibelle();

			switch (compte.getClasse()) {
			case 1:
				noeudClasse1.insert(new DefaultMutableTreeNode(chaineNoeud),
						indexNoeudClasse1++);
				break;
			case 2:
				noeudClasse2.insert(new DefaultMutableTreeNode(chaineNoeud),
						indexNoeudClasse2++);
				break;
			case 3:
				noeudClasse3.insert(new DefaultMutableTreeNode(chaineNoeud),
						indexNoeudClasse3++);
				break;
			case 4:
				if (compte.getNumero().matches("401.*")) {
					if (indexNoeudFournisseurs == 0)
						noeudClasse4.insert(noeudFournisseurs,
								indexNoeudClasse4++);
					noeudFournisseurs.insert(new DefaultMutableTreeNode(
							chaineNoeud), indexNoeudFournisseurs++);
					break;
				}

				if (compte.getNumero().matches("404.*")) {
					if (indexNoeudFournisseursImmobilisations == 0)
						noeudClasse4.insert(noeudFournisseursImmobilisations,
								indexNoeudClasse4++);
					noeudFournisseursImmobilisations.insert(
							new DefaultMutableTreeNode(chaineNoeud),
							indexNoeudFournisseursImmobilisations++);
					break;
				}

				if (compte.getNumero().matches("411.*")) {
					if (indexNoeudClients == 0)
						noeudClasse4.insert(noeudClients, indexNoeudClasse4++);
					noeudClients.insert(
							new DefaultMutableTreeNode(chaineNoeud),
							indexNoeudClients++);
					break;
				}

				noeudClasse4.insert(new DefaultMutableTreeNode(chaineNoeud),
						indexNoeudClasse4++);
				break;
			case 5:
				noeudClasse5.insert(new DefaultMutableTreeNode(chaineNoeud),
						indexNoeudClasse5++);
				break;
			case 6:
				noeudClasse6.insert(new DefaultMutableTreeNode(chaineNoeud),
						indexNoeudClasse6++);
				break;
			case 7:
				noeudClasse7.insert(new DefaultMutableTreeNode(chaineNoeud),
						indexNoeudClasse7++);
				break;
			case 8:
				noeudClasse8.insert(new DefaultMutableTreeNode(chaineNoeud),
						indexNoeudClasse8++);
				break;
			case 9:
				noeudClasse9.insert(new DefaultMutableTreeNode(chaineNoeud),
						indexNoeudClasse9++);
				break;
				default:
					//Erreur
			}
		}

		add(panel);
		panel.add(arbreDesComptes);
		panel.setViewportView(arbreDesComptes);

		pack();

		setVisible(true);

		arbreDesComptes.expandRow(0);// Ouvre l'abre � la racine

		arbreDesComptes.requestFocus();

		initialiserPopUpMenu();
	} catch(XComptaObjetIntrouvableException e) {
		//tdo manually generated block
}
	}

	private void initialiserPopUpMenu() {
		// Cr�ation du menu
		popUpMenu.add(menuItemAfficher);

		// Gestiondes des �v�nements pour les items du menu
		menuItemAfficher.addActionListener(
				event -> {
		});

		// gestion du clavier et de la sourie
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_CONTEXT_MENU) {
					popUpMenu.show(ke.getComponent(), 0, 0);
				}
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				Point p = me.getPoint();

				if (SwingUtilities.isRightMouseButton(me)) {
					popUpMenu.show(me.getComponent(), me.getX(), me.getY());
				}
			}
		});
	}

	@Override
	public String toString() {
		return getTitle();
	}
	
	public class monTreeModel implements TreeModel {

//		@Override
		public void addTreeModelListener(TreeModelListener l) {
			// TODO Auto-generated method stub
			
		}

//		@Override
		public Object getChild(Object parent, int index) {
			// TODO Auto-generated method stub
			return null;
		}

//		@Override
		public int getChildCount(Object parent) {
			// TODO Auto-generated method stub
			return 0;
		}

//		@Override
		public int getIndexOfChild(Object parent, Object child) {
			// TODO Auto-generated method stub
			return 0;
		}

//		@Override
		public Object getRoot() {
			// TODO Auto-generated method stub
			return null;
		}

//		@Override
		public boolean isLeaf(Object node) {
			// TODO Auto-generated method stub
			return false;
		}

//		@Override
		public void removeTreeModelListener(TreeModelListener l) {
			// TODO Auto-generated method stub
			
		}

//		@Override
		public void valueForPathChanged(TreePath path, Object newValue) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean equals(Object arg0) {
			// TODO Auto-generated method stub
			return super.equals(arg0);
		}

		@Override
		public int hashCode() {
			// TODO Auto-generated method stub
			return super.hashCode();
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return super.toString();
		}
	
}
}