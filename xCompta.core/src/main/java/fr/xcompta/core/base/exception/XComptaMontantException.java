package fr.xcompta.core.base.exception;

/**
	 * @author Fabrice Luneau
	 * 
	 * Exception de base pour toutes les exceptions relatives aux montants.
	 *
	 */
		public class XComptaMontantException extends XComptaCoreException {	
		private static final long serialVersionUID = 1L;

		
		public XComptaMontantException() {
			super();
		}

		public XComptaMontantException(String message) {
			super(message);
		}
	}
