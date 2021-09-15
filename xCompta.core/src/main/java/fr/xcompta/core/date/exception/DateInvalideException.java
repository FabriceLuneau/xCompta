package fr.xcompta.core.date.exception;

import fr.xcompta.core.base.exception.XComptaCoreException;

/**
 * @author Fabrice Luneau
 * 
 *  Une opération(écriture) ne peut pas être enregistrée :
 *  à ou avant la date de cloture.
 *  Elle peut être enregistrée dans le futur, dans une limite raisonable de temps à définir
 *  Elle doit être enregistrée sur une période ouverte
 *  Sur l'exervisse comptable en cours   
 */
	public class DateInvalideException extends XComptaCoreException {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DateInvalideException() {
		super();
	}

	public DateInvalideException(String message) {
		super(message);
	}
}
