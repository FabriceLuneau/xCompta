package fr.xcompta.core.xcontext.exception;

/**
 * @author Fabrice Luneau
 *
 *
 *Doit être levé siune erreurse produit à l'initialisation de la configuration du context 
 */
public class XComptaConfigurationException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public XComptaConfigurationException() {
		super();
	}

	public XComptaConfigurationException(String message) {
		super(message);
	}

}
