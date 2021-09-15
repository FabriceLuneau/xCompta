package fr.xcompta.core.base.exception;
/**
 * @author Fabrice Luneau
 *
 * Une écriture est valide si :
 *  - Elle est équilibrée
 *  les totaux du débit et du crédit sont  égaux.
* Les totaux du débit ou du crédit son différents de 0
* - Si elle comporte au moins 2 mouvements
* - si tous les mouvements utilisent des comptes différents
* - si tous les mouvements ont un montand différent de 0
*- Si la date est valide, hors date de cloture, dans la péeriode autorisée, dans l'exercisse ouvert,...
*
*Certaines erreurs peuvent faire l'objets d'autres Exceptions, qui peuvent remonter jusqua celle-ci
 */
	public class EcritureInvalideException extends XComptaCoreException {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EcritureInvalideException() {
		super();
	}

	public EcritureInvalideException(String message) {
		super(message);
	}

}