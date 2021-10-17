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

import fr.xcompta.core.base.Journal;
import fr.xcompta.core.xcontext.XContextInterface;
import fr.xcompta.core.xcontext.dao.exception.XComptaObjetExisteDejaException;
import fr.xcompta.core.xcontext.dao.exception.XComptaObjetIntrouvableException;
import fr.xcompta.core.xcontext.dao.exception.XComptaSauvegardeException;

public class BFormulaireJournal extends JInternalFrame {
	private static final long serialVersionUID = 1L;

	private JPanel panel = new JPanel();
	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JPanel panel3 = new JPanel();

	private JTextField fieldCode = new JTextField(10);
	private JTextField fieldLibelle = new JTextField(25);

	private JButton buttonSauver = new JButton("Créer");
	private JButton buttonAnnuler = new JButton("Annuler");
	private JButton buttonSupprimer = new JButton("Supprimer");

	private static final Dimension size = new Dimension(350, 200);

	private XContextInterface xContext;
	private Journal journal;

	public BFormulaireJournal(XContextInterface xContext) {
		this.xContext = xContext;
		this.journal = new Journal();

		init();

		buttonSauver.setText("Créer");
		buttonSupprimer.setEnabled(false);
		fieldCode.setEditable(true);

		buttonSauver.addActionListener(event -> {
			sauver();
		});

		buttonAnnuler.addActionListener(event -> {
			effacer();
		});
	}

	public BFormulaireJournal(XContextInterface xContext, Journal journal) {
		this.xContext = xContext;
		this.journal = journal;

		chargerJournal(journal);

		init();

		buttonSauver.setText("Modifier");
		fieldCode.setEditable(false);

		buttonSauver.addActionListener(event -> {
			modifier();
		});

		buttonSupprimer.addActionListener(event -> {
			supprimerJournal();
		});

		buttonAnnuler.addActionListener(event -> {
			try {
				chargerJournal(xContext.getJournal(fieldCode.getText()));
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

		label = new JLabel("Code : ");
		panel1.add(label);
		panel1.add(fieldCode);
		label.setLabelFor(fieldCode);

		label =new JLabel("Libellé : ");
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
		panel.setVisible(true);

		pack();
	}

	public void sauver() {
		if (fieldCode.getText().isEmpty()) {
			JOptionPane.showMessageDialog(getParent(), "Le champs code est vide");
			return;
		}

		try {
			journal.setCode(fieldCode.getText());
			journal.setLibelle(fieldLibelle.getText());

			xContext.sauverJournal(journal);

			JOptionPane.showMessageDialog(getParent(), journal + " sauvé");

			dispose();
//				((BBureau)getParent()).afficherJournaux();
		} catch (XComptaObjetExisteDejaException e) {
			JOptionPane.showMessageDialog(getParent(), "Le journal existe déjà !");
		} catch (XComptaSauvegardeException e) {
			JOptionPane.showMessageDialog(getParent(), "Erreur de sauvegarde interne");
		}
	}

	public void modifier() {
		try {
			journal = xContext.getJournal(fieldCode.getText());
			journal.setLibelle(fieldLibelle.getText());
			xContext.mettreAJourJournal(journal);

			JOptionPane.showMessageDialog(getParent(), journal + " modifié");
			dispose();
		} catch (XComptaObjetIntrouvableException e) {
			JOptionPane.showMessageDialog(getParent(), "Erreur lors de la sauvegarde de " + journal);
		}
	}

	public void supprimerJournal() {
		try {
			journal = xContext.getJournal(fieldCode.getText());
			xContext.supprimerJournal(journal);

			JOptionPane.showMessageDialog(getParent(), journal + "supprimé");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(getParent(), "Erreur lores de la suppression de " + journal);
		}
	}

	public void effacer() {
		fieldCode.setText("");
		fieldLibelle.setText("");
	}

	private void chargerJournal(Journal journal) {
		if (journal != null) {
			fieldCode.setText(journal.getCode());
			fieldLibelle.setText(journal.getLibelle());
		}
	}

	@Override
	public String toString() {
		return getTitle();
	}
}
