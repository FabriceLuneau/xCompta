package bogg;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.xcompta.core.base.Classe;
import fr.xcompta.core.xcontext.XContextInterface;
import fr.xcompta.core.xcontext.dao.exception.XComptaMiseAJourException;
import fr.xcompta.core.xcontext.dao.exception.XComptaObjetExisteDejaException;
import fr.xcompta.core.xcontext.dao.exception.XComptaObjetIntrouvableException;
import fr.xcompta.core.xcontext.dao.exception.XComptaSauvegardeException;

public class BFormulaireClasse extends JInternalFrame {
	private JPanel panel = new JPanel();
	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JPanel panel3 = new JPanel();

	private JTextField fieldNumero = new JTextField(10);
	private JTextField fieldLibelle = new JTextField(250);
	private JButton buttonSauver = new JButton("CrÃ©er");

	private JButton buttonAnnuler = new JButton("Annuler");
	private JButton buttonSupprimer = new JButton("Supprimer");

	private Dimension size = new Dimension(350, 200);

	private XContextInterface xContext;
	private Classe classe;

	public BFormulaireClasse(XContextInterface xContext) {
		this.xContext = xContext;
		this.classe = new Classe();
		
		setTitle("Créer une nouvelle classe");

		init();

		buttonSauver.setText("Créer");
		buttonSupprimer.setEnabled(false);
		fieldNumero.setEditable(true);

		buttonSauver.addActionListener(event -> {
			sauver();
		});

		buttonAnnuler.addActionListener(event -> {
			effacer();
		});
	}

	public BFormulaireClasse(XContextInterface xContext, Classe classe) {
		this.xContext = xContext;
		this.classe = classe;
		
		setTitle("Modifier la classe " + classe);

		charger(classe);

		init();

		buttonSauver.setText("Modifier");
		fieldNumero.setEditable(false);

		buttonSauver.addActionListener(event -> {
			modifier();
		});

		buttonSupprimer.addActionListener(event -> {
			supprimer();
		});

		buttonAnnuler.addActionListener(event -> {
			try {
				charger(xContext.getClasse(Short.parseShort(fieldNumero.getText())));
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

		JLabel label;

		label = new JLabel("Numero");
		panel1.add(label);
		panel1.add(fieldNumero);
		label.setLabelFor(fieldNumero);

		label = new JLabel("Libellé");
		panel2.add(label);
		panel2.add(fieldLibelle);
		label.setLabelFor(fieldLibelle);

		panel3.add(buttonSauver);
		panel3.add(buttonAnnuler);
		panel3.add(buttonSupprimer);

		panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel3.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		setContentPane(panel);
		setVisible(true);

		pack();
	}

	public void sauver() {
		if (fieldNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(getParent(), "Le champs code est vide");
			return;
		}

		try {
			// Todo en attendand de modifier l'objet directement Ã  la modification des
			// champs
			classe.setNumero(Short.parseShort(fieldNumero.getText()));
			classe.setLibelle(fieldLibelle.getText());

			xContext.sauverClasse(classe);

			JOptionPane.showMessageDialog(getParent(), classe + " sauvée");

			dispose();
		} catch (XComptaObjetExisteDejaException e) {
			JOptionPane.showMessageDialog(getParent(), "La classe existe déja !");
		} catch (XComptaSauvegardeException e) {
			JOptionPane.showMessageDialog(getParent(), "Erreur de sauvegarde interne");
		}
	}

	public void modifier() {
		try {
			classe = xContext.getClasse(Short.parseShort(fieldNumero.getText()));
			classe.setLibelle(fieldLibelle.getText());
			xContext.mettreAJourClasse(classe);

			JOptionPane.showMessageDialog(getParent(), classe + " modifiÃ©");
			dispose();
		} catch (XComptaObjetIntrouvableException e) {
			JOptionPane.showMessageDialog(getParent(), "Erreur lors de la sauvegarde de " + classe);
		} catch (XComptaMiseAJourException e) {
			JOptionPane.showMessageDialog(getParent(), "Erreur lors de la sauvegarde de " + classe);
		}
	}

	public void supprimer() {
		try {
			classe = xContext.getClasse(Short.parseShort(fieldNumero.getText()));
			xContext.supprimerClasse(classe);

			JOptionPane.showMessageDialog(getParent(), classe + "supprimÃ©");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(getParent(), "Erreur lores de la suppression de " + classe);
		}
	}

	public void effacer() {
		fieldNumero.setText("");
		fieldLibelle.setText("");
	}

	private void charger(Classe classe) {
		if (classe != null) {
			fieldNumero.setText(Short.toString(classe.getNumero()));
			fieldLibelle.setText(classe.getLibelle());
		}
	}

	@Override
	public String toString() {
		return getTitle();
	}
}
