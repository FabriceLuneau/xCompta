package bogg;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.time.LocalDate;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.slf4j.LoggerFactory;

import fr.xcompta.core.base.Ecriture;
import fr.xcompta.core.base.EcritureDeBrouillard;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.xcontext.XContextInterface;
import fr.xcompta.core.xcontext.dao.exception.XComptaObjetIntrouvableException;
import fr.xcompta.core.xcontext.dao.exception.XComptaObjetVerouilleException;

public class BFormulaireEcriture extends JInternalFrame {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BFormulaireEcriture .class);
	private static final long serialVersionUID = 1L;
	
	private JPanel panel = new JPanel();
	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JScrollPane panel3 = new JScrollPane();
	private JPanel panel4 = new JPanel();
	private JPanel panel5 = new JPanel();
	private JPanel panel6 = new JPanel();

	private JMenuBar menuBar = new JMenuBar();

	private Dimension size = new Dimension(650, 350);

	private FieldDate fieldDate = new FieldDate();
	private JTextField fieldLibelle = new JTextField("", 50);

	private BComboBoxJournaux comboBoxJournal;
	private JTextField fieldDebit = new JTextField(15);
	private JTextField fieldCredit = new JTextField(15);
	private JTextField fieldSolde = new JTextField(15);

	private JButton buttonEnregistrer = new JButton("Enregistrer");
	private JButton buttonAnnuler = new JButton("Annuler");

	private TableEcritureSaisie tableEcriture;

	private EcritureDeBrouillard ecriture;
	private XContextInterface xContext;

	//false = mode creation true = mode mise à jour  
	private boolean update = false;

	private JMenu menuFichier = new JMenu("Fichier");
	private JMenuItem itemOuvrir = new JMenuItem("Ouvrir");
	private JMenuItem itemSauver = new JMenuItem("Sauver");
	private JMenuItem itemSauverSous = new JMenuItem("Sauver sous");

	private File file = null;

	public BFormulaireEcriture(XContextInterface xContext) {
		this.xContext = xContext;
		this.ecriture = new EcritureDeBrouillard();
		
		ecriture.setDate(LocalDate.now());
//		fieldDate.setDate()

		tableEcriture = new TableEcritureSaisie(xContext);
		comboBoxJournal = new BComboBoxJournaux(xContext);

		init();
		
		update = false;
		
		this.setTitle("Nouvelle écriture");
	}

	public BFormulaireEcriture(XContextInterface xContext, EcritureDeBrouillard ecriture) {
		this.xContext = xContext;
		this.ecriture = ecriture;

		tableEcriture = new TableEcritureSaisie(xContext, ecriture);
		
		comboBoxJournal = new BComboBoxJournaux(xContext);
		comboBoxJournal.setSelectedJournal(ecriture.getJournal());

//		fieldDate.setDate(ecriture.getDate().toString());
		
		init();
		update = true;
		
		setTitle("Modification ecriture nÂ°" + ecriture.getId());
	}

	private void init() {
		setSize(size);
		setClosable(true);
		setIconifiable(true);
		setResizable(false);

		menuFichier.add(itemOuvrir);
		menuFichier.add(itemSauver);
		menuFichier.add(itemSauverSous);
		menuBar.add(menuFichier);
		setJMenuBar(menuBar);
		
		JLabel label;

		panel.add(panel1);
		panel.add(panel2);
		panel.add(panel3);
		panel.add(panel4);
		panel.add(panel5);
		panel.add(panel6);
		
		//Construction des panneaux
		//Panel 1
		label = new JLabel("Date");
//		fieldDate.setNullValue(false);
//		fieldDate.setMaximum(new Date());
label.setLabelFor(fieldDate);
		panel1.add(new JLabel("Journal : "));
		panel1.add(fieldDate);
		
		label = new JLabel("Journaux");
		label.setLabelFor(comboBoxJournal);
		
		panel1.add(label);
		panel1.add(comboBoxJournal);
		
		//Panel 2
		
		label = new JLabel("Libellé;");
				label.setLabelFor(fieldLibelle);

		panel2.add(label);
		fieldLibelle.setText(ecriture.getLibelle());

		panel2.add(fieldLibelle);
		
		//Panel 3
		panel3.setViewportView(tableEcriture);
		
		//Panel 4
		label = new JLabel("Débit");
		label.setLabelFor(fieldDebit);	
		panel4.add(label);
		label.setLabelFor(fieldDebit);
		panel4.add(fieldDebit);
		fieldDebit.setEditable(false);
		
		label = new JLabel("Crédit");
		label.setLabelFor(fieldCredit);
		fieldCredit.setEditable(false);
		panel4.add(fieldCredit);
		
		label = new JLabel("Solde");
		label.setLabelFor(fieldSolde);
		panel5.add(label);
		panel5.add(fieldSolde);
		fieldSolde.setEditable(false);

		panel6.add(buttonEnregistrer);
		panel6.add(buttonAnnuler);

		panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel4.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panel5.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panel6.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		setContentPane(panel);
		panel.setVisible(true);
		pack();

		buttonEnregistrer.addActionListener(event -> {
			ecriture = tableEcriture.getEcriture();
			ecriture.setJournal((Journal) comboBoxJournal.getSelectedItem());
			ecriture.setLibelle(fieldLibelle.getText());
			ecriture.setDate(fieldDate.getValeur());
			
			sauver();
						});

		buttonAnnuler.addActionListener(event -> {
			effacer();
						});

		tableEcriture.getTableModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent tme) {
				// labelTotaux.setText(miseAJourTotaux());
//				miseAJourTotaux();
			}
		});

//		itemOuvir.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent ae) {
//				JFileChooser fileChooser = new JFileChooser("input");
//				fileChooser.showOpenDialog(null);
//				file = fileChooser.getSelectedFile();

//				if (file != null) {
//					setEcriture(XmlAccess.getEcriture(xContext, file, true));
//			}
//		});

//		itemSauver.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent ae) {
//				ecriture = tableEcriture.getEcriture();
//				ecriture.setJournal((Journal) comboBoxJournal.getSelectedItem());
//				ecriture.setLibelle(textFieldLibelle.getText());
//				ecriture.setDate(fieldDate.getDate());

//				if (file != null) {
//					itemSauverSous.setEnabled(true);
//					XmlAccess.writeEcriture(xContext, file, ecriture);
//				} else {
//					JFileChooser fileChooser = new JFileChooser("input");
//					fileChooser.showOpenDialog(null);
//					file = fileChooser.getSelectedFile();

//					XmlAccess.writeEcriture(xContext, file, ecriture);
//				}
//			}
//		});

//		itemSauverSous.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent ae) {
//				JFileChooser fileChooser = new JFileChooser("input");
//				fileChooser.showOpenDialog(null);
//				file = fileChooser.getSelectedFile();

//				ecriture = tableEcriture.getEcriture();
//				ecriture.setJournal((Journal) comboBoxJournal.getSelectedItem());
//				ecriture.setLibelle(textFieldLibelle.getText());
//				ecriture.setDate(fieldDate.getDate());
//				XmlAccess.writeEcriture(xContext, file, ecriture);
//			}
		}
//;
//	}

	private void setEcriture(EcritureDeBrouillard ecriture) {
		this.ecriture = ecriture;
		tableEcriture.setEcriture(ecriture);
		fieldLibelle.setText(ecriture.getLibelle());
		comboBoxJournal.setSelectedItem(ecriture.getJournal());
//		fieldDate.setDate(ecriture.getDate());
	}

//	private void miseAJourTotaux() {
//		
//		double debit = tableEcriture.getTotalDebit();
//		double credit = tableEcriture.getTotalCredit();
//		double solde = debit + credit;

//		String chaineTotaux;

//		if (credit != 0)
//			credit *= -1;

//		textFieldDebit.setText(Double.toString(debit));
//		textFieldCredit.setText(Double.toString(credit));

//		 if (solde == 0) {
//		 chaineTotaux += "EquilibrÃ©e";
//		 } else {
//		 if (solde > 0) {
//		 chaineTotaux += preferences.amountFormat.format(solde)+ " DÃ©biteur"; {
//		 } else {
//		 chaineTotaux += preferences.amountFormat.format(solde)
//		 + " CrÃ©diteur";
//	}
//		 }
//	}*./
//	}

	public Ecriture getEcriture() {
		return ecriture;
	}
	
		private void effacer() {
			tableEcriture.effacer();
			fieldLibelle.setText("");
			setTitle("Créer une nouvelle écriture");
		file = null;
		}

		public void sauver() {
			if(update) {
				try {
					xContext.mettreAJourEcriture(ecriture);
				} catch (XComptaObjetVerouilleException | XComptaObjetIntrouvableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} else {
			Integer id = xContext.sauverEcriture(ecriture);
			
			JOptionPane.showMessageDialog(getParent(), "Ecriture sauvée : " + id);
		}
		}
		
		private boolean ecritureEstValide() {
//			if (!fieldDate.inputIsValid()) {
//				JOptionPane.showMessageDialog(getParent(), "La date n'est pas valide !");
//				return false;
//		}

		if(!ecriture.estEquilibree()) {
			JOptionPane.showMessageDialog(getParent(), "L'écriture n'est pas équilibrée");
			return false;
		}
		
		
		return true;
}
		
@Override
			public String toString() {
			return getTitle();
		}
	}
