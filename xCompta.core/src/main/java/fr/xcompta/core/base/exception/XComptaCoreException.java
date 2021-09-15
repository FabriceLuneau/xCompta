package fr.xcompta.core.base.exception;

/**
 * @author Fabrice Luneau
 * 
 *         Exception de base pour toutes les exception du projet xComptaCore et des sous projets
 *
 */
public class XComptaCoreException extends Exception {
	private static final long serialVersionUID = 1L;
	

	public XComptaCoreException() {
		super();
	}

	public XComptaCoreException(String message) {
		super(message);
	}
}