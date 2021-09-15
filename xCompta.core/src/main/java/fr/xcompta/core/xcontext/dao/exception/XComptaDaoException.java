package fr.xcompta.core.xcontext.dao.exception;

/**
 * @author Fabrice Luneau
 * 
 *Exception m�re pour lex exception de XComptaDao
 */
	public class XComptaDaoException extends Exception {	
	private static final long serialVersionUID = 1L;

	public XComptaDaoException() {
		super();
	}

	public XComptaDaoException(String message) {
		super(message);
	}

}
