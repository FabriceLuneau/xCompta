package fr.xcompta.core.xcontext.dao.exception;

/**
 * @author Fabrice Luneau
 * 
 *  Doit �tre lev�e si un oobjet n'est pas trouv�e par un DAO 
 */
	public class XComptaObjetIntrouvableException extends XComptaDaoException {
	private static final long serialVersionUID = 1L;

	public XComptaObjetIntrouvableException() {
		super();
	}

	public XComptaObjetIntrouvableException(String message) {
		super(message);
	}

}
