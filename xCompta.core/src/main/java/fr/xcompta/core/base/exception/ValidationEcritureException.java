package fr.xcompta.core.base.exception;

import fr.xcompta.core.xcontext.dao.exception.XComptaDaoException;

/**
 * @author Fabrice Luneau
 * 
 *         Doit �tre lev�e si une �criture ne peut pas �tre valid�e par le DAO
 *         En cas d'erreur avec la solution de persistance
 */
public class ValidationEcritureException extends XComptaDaoException{
	private static final long serialVersionUID = 1L;

	public ValidationEcritureException() {
		super();
	}

	public ValidationEcritureException(String message) {
		super(message);
	}

}
