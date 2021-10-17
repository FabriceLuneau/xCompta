package bogg;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.xcompta.core.base.Compte;
import fr.xcompta.core.xcontext.XContextInterface;
import fr.xcompta.core.xcontext.dao.exception.XComptaObjetExisteDejaException;
import fr.xcompta.core.xcontext.dao.exception.XComptaObjetIntrouvableException;
import fr.xcompta.core.xcontext.dao.exception.XComptaSauvegardeException;

public class BFormulaireCompte extends JInternalFrame {
	private static final long serialVersionUID = 1L;

	private JPanel panel = new JPanel();
	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JPanel panel3 = new JPanel();
	private JPanel panel4 = new JPanel();

	private JTextField fieldsNumero = new JTextField(10);
	private JTextField fieldLibelle = new JTextField(25);
	private JCheckBox checkBoxUtilisable = new JCheckBox();
	private JButton buttonSauver = new JButton("Créer");
	private JButton buttonAnnuler = new JButton("Annuler");
	private JButton buttonSupprimer = new JButton("Supprimer");

	private XContextInterface xContext;
	private Compte compte;

	private static Dimension size = new Dimension(400, 200);

	public BFormulaireCompte(XContextInterface xContext) {
		this.xContext = xContext;
		compte = new Compte();

		init();

		buttonSauver.setText("Créer");
		buttonSupprimer.setEnabled(false);
		fieldsNumero.setEditable(true);

		buttonSauver.addActionListener(event -> {
			sauver();
		});

		buttonAnnuler.addActionListener(event -> {
			effacer();
		});
	}

	public BFormulaireCompte(XContextInterface xContext, Compte compte) {
		this.xContext = xContext;
		this.compte = compte;

		charger(compte);

		init();

		buttonSauver.setText("Modifier");
		fieldsNumero.setEditable(false);

		buttonSauver.addActionListener(event -> {
			modifierCompte();
		});

		buttonSupprimer.addActionListener(event -> {
			supprimer();
		});

		buttonAnnuler.addActionListener(event -> {
			try {
				charger(xContext.getCompte(fieldsNumero.getText()));
			} catch (XComptaObjetIntrouvableException e) {
				JOptionPane.showMessageDialog(getParent(), "Erreur interne lors du rafraichissement du formulaire");
			}
		});
	}

	private void init() {
		setSize(size);

		setClosable(true);
		setIconifiable(true);

		setResizable(false);

		panel.add(panel1);
		panel.add(panel2);
		panel.add(panel3);
		panel.add(panel4);

		JLabel label;

		label = new JLabel("Numéro : ");
		panel.add(label);
		panel1.add(fieldsNumero);
		label.setLabelFor(fieldsNumero);

		label = new JLabel("Libell : ");
		panel2.add(label);
		panel2.add(fieldLibelle);
		label.setLabelFor(fieldLibelle);
		
		panel3.add(checkBoxUtilisable);
		checkBoxUtilisable.setToolTipText("Utilisable");
		label = new JLabel("Utilisable : ");
		panel3.add(label);
		label.setLabelFor(checkBoxUtilisable);

		panel4.add(buttonSauver);
		panel4.add(buttonAnnuler);
		panel4.add(buttonSupprimer);

		panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel3.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel4.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		setContentPane(panel);
		panel.setVisible(true);
		pack();

		requestFocus();
	}

	public void sauver() {
		if (fieldsNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(getParent(), "Le champs code est vide");
			return;
		}

		try {
			compte.setNumero(fieldsNumero.getText());
			compte.setLibelle(fieldLibelle.getText());
			compte.setUtilisable(checkBoxUtilisable.isSelected());

			xContext.sauverCompte(compte) ;

			JOptionPane.showMessageDialog(getParent(), compte + " sauvé");
		} catch(XComptaObjetExisteDejaException e) {
			JOptionPane.showMessageDialog(getParent(), "Le compte existe djÃ ");
		} catch (XComptaSauvegardeException e) {
			JOptionPane.showMessageDialog(getParent(), "Erreur de sauvegarde interne");
		}
	}

	public void modifierCompte() {
		try {
			compte = xContext.getCompte(fieldsNumero.getText());
			compte.setLibelle(fieldLibelle.getText());
			xContext.mettreAJourCompte(compte);

			JOptionPane.showMessageDialog(getParent(), compte + " modifi");
			dispose();
		} catch (XComptaObjetIntrouvableException e) {
			JOptionPane.showMessageDialog(getParent(), "Erreur lors de la sauvegarde de " + compte);
		} /*
			 * catch (XComptaMiseAJourException e) {
			 * JOptionPane.showMessageDialog(getParent(), "Erreur lors de la sauvegarde de "
			 * +compte ); }
			 */
	}

	public void supprimer() {
		try {
			compte = xContext.getCompte(fieldsNumero.getText());
			xContext.supprimerCompte(compte);

			JOptionPane.showMessageDialog(getParent(), compte + "supprim");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(getParent(), "Erreur lores de la suppression de " + compte);
		}
	}

	public void effacer() {
		fieldsNumero.setText("");
		fieldLibelle.setText("");
	}

	private void charger(Compte compte) {
		if (compte != null) {
			fieldsNumero.setText(compte.getNumero());
			fieldLibelle.setText(compte.getLibelle());
			checkBoxUtilisable.setSelected(compte.isUtilisable());
		}
	}

	@Override
	public String toString() {
		return getTitle();
	}
}