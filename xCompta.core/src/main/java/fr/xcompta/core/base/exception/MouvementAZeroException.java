package fr.xcompta.core.base.exception;

/**
 * @author Fabrice Luneau
 *
 *Un mouvement ne peut pas avoir un montant égal à 0 au moment de l'enregistrement
 */
public class MouvementAZeroException extends XComptaCoreException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MouvementAZeroException() {
		super();
	}

	public MouvementAZeroException(String message) {
		super(message);
	}

}
