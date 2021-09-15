package fr.xcompta.core.xcontext.dao.exception;

/**
 * @author Fabrice Luneau
 * 
 *  Doit être levée si un objet est référencé par un autre objet 
 */
	public class XComptaObjetUtiliseException extends XComptaDaoException {
	private static final long serialVersionUID = 1L;

	public XComptaObjetUtiliseException() {
		super();
	}

	public XComptaObjetUtiliseException(String message) {
		super(message);
	}

}
