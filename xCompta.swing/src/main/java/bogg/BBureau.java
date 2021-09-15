package bogg;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.slf4j.LoggerFactory;

import fr.xcompta.core.base.Classe;
import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.Ecriture;
import fr.xcompta.core.base.EcritureDeBrouillard;
import fr.xcompta.core.base.EcritureValidee;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.xcontext.XContextInterface;
import fr.xcompta.core.xcontext.dao.exception.XComptaObjetIntrouvableException;

/** 
* @authorFabrice LUNEAU
 * 
 *                Fenetre principal de l'application
 */
public class BBureau extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3442386982934630333L;

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BBureau.class);

	private static JDesktopPane bureau = new JDesktopPane();

	private XContextInterface xContext;

	public BBureau(XContextInterface xContext) {
		this.xContext = xContext;

		init();
	}

	private void init() {
		try {
			bureau.setBackground(Color.gray);
			setSize(1000, 700);
			setContentPane(bureau);

			setTitle(xContext.getName());

			setJMenuBar(new JMenuBar());

			ajouterMenuClasse();
			ajouterMenuJournal();
			ajouterMenuCompte();
			ajouterMenuEcriture();
			ajouterMenuTraitement();
			ajouterMenuImporter();
			ajouterMenuExporter();

			// La fenetre
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			setVisible(true);

			addWindowListener(new WindowListener() {

				@Override
				public void windowOpened(WindowEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void windowIconified(WindowEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void windowDeiconified(WindowEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void windowDeactivated(WindowEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void windowClosing(WindowEvent e) {
					if (JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir quitter ?", "Quitter",
							JOptionPane.YES_NO_OPTION) == 0) {
						xContext.close();

						System.exit(0);
					}

				}

				@Override
				public void windowClosed(WindowEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void windowActivated(WindowEvent e) {
					// TODO Auto-generated method stub

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JMenuItem ajouterMenuItem(String nomMenu, String nomMenuItem, ActionListener al) {
		JMenu menu = getMenu(nomMenu);

		if (menu == null) {
			menu = new JMenu(nomMenu);
			getJMenuBar().add(menu);

			logger.info("Création du menu " + nomMenu);
		}

		if (!menuItemExists(menu, nomMenuItem)) {
			menu.add(new JMenuItem(nomMenuItem));
			getMenuItem(menu, nomMenuItem).addActionListener(al);

			logger.info("Ajout du menu " + nomMenuItem + " au menu " + nomMenu);
		} else {
			logger.error("L'item de  menu " + nomMenuItem + "�xiste d�j� !");
		}

		return getMenuItem(menu, nomMenuItem);
	}

	public JMenu getMenu(String nom) {
		for (int i = 0; i < getJMenuBar().getMenuCount(); i++) {
			JMenu menu = getJMenuBar().getMenu(i);

			if (menu.getText().trim().equalsIgnoreCase(nom.trim())) {
				return menu;
			}
		}
		return null;
	}

	public JMenuItem getMenuItem(JMenu menu, String nomMenuItem) {
		for (int i = 0; i < menu.getItemCount(); i++) {
			if (menu.getItem(i).getText().equalsIgnoreCase(nomMenuItem)) {
				return menu.getItem(i);
			}
		}
		return null;
	}

	public boolean menuItemExists(JMenu menu, String nomMenuItem) {
		return (getMenuItem(menu, nomMenuItem) != null);
	}

	public void ajouterMenuClasse() {
		ActionListener al;
		
		al = (event -> {
			JInternalFrame fenetreInterne = new BFormulaireClasse(xContext);
			bureau.add(fenetreInterne);
			fenetreInterne.setVisible(true);
			fenetreInterne.toFront();
			fenetreInterne.requestFocus();
		});
		ajouterMenuItem("Classe", "Cr�er une classes", al);
		
		/*al = (event -> {
				try {
					Short numero = Short.parseShort(JOptionPane.showInputDialog(bureau, "Veuillez entrer le numero de la  classe", ""));

					Classe classe = xContext.getClasse(numero);
					
					JInternalFrame fenetreInterne = new 	(xContext, classe);
					
bureau.add(fenetreInterne);
					fenetreInterne.setVisible(true);
					fenetreInterne.toFront();
					fenetreInterne.requestFocus();

fenetreInterne = new BFormulaireClasse(xContext, classe);
				} catch (Exception e) {
					// TODO: handle exception
				}
				});*/
ajouterMenuItem("Classe", "Modifier une classe", al);

al = (event -> {
			JInternalFrame fenetreInterne = new BListeClasses(xContext);
			bureau.add(fenetreInterne);
			fenetreInterne.setVisible(true);
			fenetreInterne.toFront();
			fenetreInterne.requestFocus();
		});
		ajouterMenuItem("Classe", "Lister les classes", al);
	}

	public void ajouterMenuJournal() {
		final String NOM = "Journal";
		ActionListener al;

		al = event -> {
			JInternalFrame fenetreInterne = new BFormulaireJournal(xContext);
			bureau.add(fenetreInterne);
			fenetreInterne.setVisible(true);

			fenetreInterne.toFront();
			fenetreInterne.requestFocus();
		};
		ajouterMenuItem(NOM, "Créer un journal", al);

		al = event -> {
			try {
				String code = JOptionPane.showInputDialog(bureau, "Veuillez entrer le code du journal.", "");

				Journal journal = xContext.getJournal(code);

				JInternalFrame fenetreInterne;
				fenetreInterne = new BFormulaireJournal(xContext, journal);
				bureau.add(fenetreInterne);
				fenetreInterne.setVisible(true);
				fenetreInterne.toFront();
				fenetreInterne.requestFocus();
			} catch (XComptaObjetIntrouvableException ex) {
//todo faire quelque chose
			}
		};
		ajouterMenuItem(NOM, "Modifier un journal", al);

		al = vent -> {
			JInternalFrame fenetreInterne = new BListeJournaux(xContext);
			bureau.add(fenetreInterne);
			fenetreInterne.setVisible(true);

			fenetreInterne.toFront();
			fenetreInterne.requestFocus();

		};
		ajouterMenuItem("Journal", "Liste des journaux", al);

		al = event -> {
			JInternalFrame fenetreInterne = new IfConsultationJournal(xContext);
			bureau.add(fenetreInterne);
			fenetreInterne.setVisible(true);

			fenetreInterne.toFront();
			fenetreInterne.requestFocus();
		};
		ajouterMenuItem("Journal", "Consulter un journal", al);
	}

	public void ajouterMenuCompte() {
		final String NOM = "Compte";
		ActionListener al;

		al = event -> {
			JInternalFrame fenetreInterne = new BFormulaireCompte(xContext);
			bureau.add(fenetreInterne);
			fenetreInterne.setVisible(true);

			fenetreInterne.toFront();
			fenetreInterne.requestFocus();
		};
		ajouterMenuItem(NOM, "Crer un compte", al);

		al = event -> {
			try {
				String numero = JOptionPane.showInputDialog(bureau, "Veuillez entrer le numero du compte.", "");

				Compte compte = xContext.getCompte(numero);
				// Todo gérer les mauvaises entrées ou remplacer par une liste
				// déroulante/combobox

				JInternalFrame fenetreInterne;
				fenetreInterne = new BFormulaireCompte(xContext, compte);
				bureau.add(fenetreInterne);
				fenetreInterne.setVisible(true);
				fenetreInterne.toFront();
				fenetreInterne.requestFocus();
			} catch (XComptaObjetIntrouvableException ex) {
//Todo faire quelque chose
			}
		};
		ajouterMenuItem(NOM, "Modifier un compte", al);
/*
		al = event -> {
			//JInternalFrame fenetreInterne = new IfPlanComptable(xContext);
			bureau.add(fenetreInterne);
			fenetreInterne.setVisible(true);

			fenetreInterne.toFront();
			fenetreInterne.requestFocus();
		};*/
		ajouterMenuItem(NOM, "Afficher le plan comptable", al);

		al = event -> {
			JInternalFrame fenetreInterne = new IfArbreDesComptes(xContext);
			bureau.add(fenetreInterne);
			fenetreInterne.setVisible(true);

			fenetreInterne.toFront();
			fenetreInterne.requestFocus();

		};
		ajouterMenuItem(NOM, "Afficher l'arbre des comptes", al);

		/*al = event -> {
			JInternalFrame fenetreInterne = new IfConsultationCompte(xContext);
			bureau.add(fenetreInterne);
			fenetreInterne.setVisible(true);

			fenetreInterne.toFront();
			fenetreInterne.requestFocus();
		};*/
		ajouterMenuItem(NOM, "Consulter un compte", al);

		al = event -> {
//				Todo faire quelque chose
		};
		ajouterMenuItem(NOM, "Générer les soldes", al);

		al = event -> {
			// dodo à faire
		};
		ajouterMenuItem(NOM, "Réinitialiser les soldes", al);
	}

	public void ajouterMenuEcriture() {
		final String NOM = "Ecriture";
		ActionListener al;

		al = event -> {
			try {
				String saisie = JOptionPane.showInputDialog(bureau, "Veuillez entrer le numéro d'écriture.", "");

				Ecriture ecriture = null;
				int numeroEcriture = Integer.parseInt(saisie);
				ecriture = xContext.getEcriture(numeroEcriture);

				JInternalFrame fenetreInterne;
				if (ecriture.getClass().equals(EcritureValidee.class)) {
					fenetreInterne = new IfEcritureConsultation(xContext, (EcritureValidee) ecriture);
				} else {
					fenetreInterne = new BFormulaireEcriture(xContext, (EcritureDeBrouillard) ecriture);
				}

				bureau.add(fenetreInterne);
				fenetreInterne.setVisible(true);
				fenetreInterne.toFront();
				fenetreInterne.requestFocus();

			} catch (XComptaObjetIntrouvableException ex) {
//Todo faire quelque chose
			}
		};
		ajouterMenuItem(NOM, "Afficher écriture", al);

		al = event -> {
			JInternalFrame fenetreInterne = new BFormulaireEcriture(xContext);
			bureau.add(fenetreInterne);
			fenetreInterne.setVisible(true);

			fenetreInterne.toFront();
			fenetreInterne.requestFocus();

		};
		ajouterMenuItem(NOM, "Saie écriture", al);

		al = event -> {

			JInternalFrame fenetreInterne = new IfConsultationBrouillard(xContext);
			bureau.add(fenetreInterne);
			fenetreInterne.setVisible(true);

			fenetreInterne.toFront();
			fenetreInterne.requestFocus();
		};
		ajouterMenuItem(NOM, "Valider brouillard", al);
	}

	public void ajouterMenuTraitement() {
		final String NOM = "Traitements";
		ActionListener al;

		al = event -> {
			JInternalFrame fenetreInterne = new IfLettrage(xContext);
			bureau.add(fenetreInterne);
			fenetreInterne.setVisible(true);

			fenetreInterne.toFront();
			fenetreInterne.requestFocus();

		};
		ajouterMenuItem(NOM, "Lettrage", al);

		al = event -> {
			JInternalFrame fenetreInterne = new IfDeLettrage(xContext);
			bureau.add(fenetreInterne);
			fenetreInterne.setVisible(true);

			fenetreInterne.toFront();
			fenetreInterne.requestFocus();

		};
		ajouterMenuItem(NOM, "Délettrage", al);
	}

	public void ajouterMenuImporter() {
		ActionListener al;

		al = event -> {
			// todo
		};
	}

	public void ajouterMenuExporter() {
		ActionListener al;
	}

	public void ajouterMenuFenetre() {
		final String NOM = "Fenêtres";
		ActionListener al;

		al = event -> {
			JInternalFrame[] frames = bureau.getAllFrames();

			for (int i = 0; i < frames.length; i++) {
				try {
					frames[i].setIcon(true);
				} catch (Exception e) {
					// Todo faire quelque chose
				}
			}
		};
		ajouterMenuItem(NOM, "Réduire toutes les fenêtres", al);

		al = event -> {
			JInternalFrame[] frames = bureau.getAllFrames();

			try {
				for (int i = 0; i < frames.length; i++) {
					frames[i].setIcon(false);
					frames[i].setLocation(i * 25, i * 25);
					frames[i].toFront();
				}
			} catch (Exception e) {
				// todo faire quelque chose
			}
		};
		ajouterMenuItem(NOM, "Fenêtre", al);

		al = event -> {
			JInternalFrame[] frames = bureau.getAllFrames();

			JInternalFrame fenetreInterne = new IfListFrames(frames);

			bureau.add(fenetreInterne);
			fenetreInterne.setVisible(true);

			fenetreInterne.toFront();
			fenetreInterne.requestFocus();
		};
		ajouterMenuItem(NOM, "Cascade", al);

	}

	public void ajouterMenuAdministration() {
		ActionListener al;
	}
}
