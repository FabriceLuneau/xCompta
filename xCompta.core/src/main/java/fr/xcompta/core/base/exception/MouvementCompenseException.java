package fr.xcompta.core.base.exception;

/**
 * @author Fabrice Luneau
 * 
 *  Une écriture ne peut pas faire références plusieurs fois au même compte
 *  une écriture ne peut pas utiliser le même compte au débit et au crédit en même temps
 */
public class MouvementCompenseException extends XComptaCoreException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MouvementCompenseException() {
		super();
	}

	public MouvementCompenseException(String message) {
		super(message);
	}

}
