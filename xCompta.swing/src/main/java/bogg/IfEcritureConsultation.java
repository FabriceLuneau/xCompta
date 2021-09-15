package bogg;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import fr.xcompta.core.base.EcritureValidee;
import fr.xcompta.core.xcontext.XContextInterface;
import fr.xcompta.core.xcontext.dao.exception.XComptaObjetIntrouvableException;

/**
 * @author Fabrice LUNEAU
 * 
 *         Affiche une Ecriture, en lecture seule. Valable uniquement, pour les
 *         Ecritures validées.
 */
/**
 * @author Fabrice
 *
 */
public class IfEcritureConsultation extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	protected JPanel panel = new JPanel();
	protected JPanel panel1 = new JPanel();
	protected JPanel panel2 = new JPanel();
	protected JScrollPane panel3 = new JScrollPane();
	protected JPanel panel4 = new JPanel();

	protected Dimension size = new Dimension(650, 350);

	protected FieldDate textFieldDate = new FieldDate();
	protected JTextField textFieldLibelle = new JTextField("", 50);
	protected BComboBoxJournaux comboBoxJournal;

	protected TableEcritureConsultation tableEcriture;

	protected JLabel labelTotaux = new JLabel("");

	protected EcritureValidee ecriture;
	protected XContextInterface xContext;

	public IfEcritureConsultation(XContextInterface xContext, EcritureValidee ecriture) {
		this.xContext = xContext;
		this.ecriture = ecriture;

		init();
	}

	public IfEcritureConsultation(XContextInterface xContext, int numeroEcriture) {
		this.xContext = xContext;
		
		try {
		ecriture = (EcritureValidee) xContext.getEcriture(numeroEcriture);

		init();
		} catch (XComptaObjetIntrouvableException e) {
			// TODO: handle exception
		}
	}

	private void init() {
		setTitle("Ecriture " + ecriture.getId());// ToExplain toRestore

		setSize(size);
		setMinimumSize(size);

		// setClosable(true);
		setIconifiable(true);
		// sizable(false);

		tableEcriture = new TableEcritureConsultation(ecriture);
		panel.add(panel1);
		panel.add(panel2);
		panel.add(panel3);
		panel.add(panel4);

		panel1.add(new JLabel("Journal : "));
		comboBoxJournal = new BComboBoxJournaux(xContext);

		comboBoxJournal.setEnabled(false);
		comboBoxJournal.setSelectedItem(ecriture.getJournal());
		panel1.add(comboBoxJournal);
		comboBoxJournal.setToolTipText("Journal");

		panel1.add(new JLabel("Date : "));
//		textFieldDate.setDate(ecriture.getDate());
		textFieldDate.setEditable(false);
		panel1.add(textFieldDate);
		textFieldDate.setToolTipText("Date");

		panel1.add(new JLabel("Numero de piece : " + (Long.toString(ecriture.getId()))));

		panel2.add(new JLabel("Libelle : "));
		textFieldLibelle.setText(ecriture.getLibelle());
		textFieldLibelle.setEditable(false);
		textFieldLibelle.setToolTipText("Mibelle");
		panel2.add(textFieldLibelle);

		panel3.setViewportView(tableEcriture);
		tableEcriture.changeSelection(0, 0, false, false);

		labelTotaux.setText(
				"<html><table><tr>" + "<td width=110>Total :</td>" + "<td width=110>" + (ecriture.getTotalDebit())
						+ "</td>" + "<td width=110>" + (ecriture.getTotalCredit() * -1) + "</td></tr></table>");
		panel4.add(labelTotaux);

		panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel4.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		setContentPane(panel);
		setVisible(true);

		pack();
	}

	public String toString() {
		return getTitle();
	}
}