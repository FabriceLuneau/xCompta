package bogg;

import javax.swing.JComboBox;

import fr.xcompta.core.base.Journal;
import fr.xcompta.core.xcontext.XContextInterface;
import fr.xcompta.core.xcontext.dao.exception.XComptaObjetIntrouvableException;

/**
 * @author Fabrice LUNEAU
 * 
 *         JComboBoxpréremplit avec les journaux 
 */
public class BComboBoxJournaux extends JComboBox {
	private static final long serialVersionUID = 1L;
	
	private boolean all = false;// Ajoute un item "tout"
	private XContextInterface xContext;
	

	BComboBoxJournaux(XContextInterface xContext) {
		super(xContext.getJournaux().toArray());
		this.xContext = xContext;

		setSelectedIndex(0);
	}

	public void setAllOption(boolean all) {
		Journal journal = new Journal();
		journal.setCode("ALL");
		journal.setLibelle("Tout");

		if (all)
			insertItemAt(journal, 0);
		else
			removeItem(journal);
	}

	public void setSelectedJournal(Journal journal) {
		if (journal != null)
			setSelectedItem(journal);
	}

	public void setSelectedJournal(String codeJournal) {
			try {
				setSelectedItem(xContext.getJournal(codeJournal));
			} catch (XComptaObjetIntrouvableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}

//Réviser l'item tout/all'