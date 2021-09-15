package fr.xcompta.core.xcontext.dao.exception;

/**
 * @author Fabrice Luneau
 *
 * Cette exception Doit être levée si un objet ne peut pas être sauvé en base
 */
	public class XComptaMiseAJourException extends XComptaDaoException {
	private static final long serialVersionUID = 1L;

	public XComptaMiseAJourException() {
		super();
	}

	public XComptaMiseAJourException(String message) {
		super(message);
	}

}
