package fr.xcompta.core.xcontext.dao.exception;

/**
 * @author Fabrice Luneau
 * 
Les objets compta suivants sont consid�r� comme v�rouill�s  si un autre objet y fait r�f�rences :
- Les classes v�rouill�es par les comptes
-les journaux v�rouill�s par les �critures
-, les comptes,... v�rouill�s par les mouvements
*
Une �criture valid�e ne peut pas �tre supprim�e
 */
	public class XComptaObjetVerouilleException extends XComptaDaoException 	{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public XComptaObjetVerouilleException() {
		super();
	}

	public XComptaObjetVerouilleException(String message) {
		super(message);
	}

}
