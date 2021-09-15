package fr.xcompta.core.date.exception;

import fr.xcompta.core.base.exception.XComptaCoreException;

/**
 * @author Fabrice Luneau
 *
 * Exception de base pour toutes les exceptions relative aux dates 
 */
	public class DateException extends XComptaCoreException {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DateException() {
		super();
	}

	public DateException(String message) {
		super(message);
	}
}
