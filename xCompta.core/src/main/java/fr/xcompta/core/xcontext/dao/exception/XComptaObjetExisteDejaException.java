package fr.xcompta.core.xcontext.dao.exception;

/**
 * @author Fabrice Luneau
 *
 * 
 * Cette exception Doit être levée si un objet est déjà présent en base
 * La vomparaisaon se fait par clef métier
 */
	public class XComptaObjetExisteDejaException extends XComptaDaoException {
	private static final long serialVersionUID = 1L;

	public XComptaObjetExisteDejaException() {
		super();
	}

	public XComptaObjetExisteDejaException(String message) {
		super(message);
	}

}
