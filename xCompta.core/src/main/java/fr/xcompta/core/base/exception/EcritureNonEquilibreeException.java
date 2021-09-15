package fr.xcompta.core.base.exception;

/**
 * @author Fabrice Luneau
 *
 * Une écriture doit être équilibrée
 *  C'est à dire que la somme des débits et des crédits doivent être égale et différente de 0
 * - Elle doit donc compporter 
 *- au moins deux mouvements d'un montant différent de 0
 */
public class EcritureNonEquilibreeException extends XComptaCoreException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EcritureNonEquilibreeException() {
		super();
	}

	public EcritureNonEquilibreeException(String message) {
		super(message);
	}

}
