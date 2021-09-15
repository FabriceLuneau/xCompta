package fr.xcompta.core.date.exception;

/**
 * @author Fabrice Luneau
 * 
 * Il n'est pas possible de modifier ou d'ajouter une opération(écriture) sur un exercisse comptablbe cloturé
 *  Elle peut être enregistrée dans le futur, dans une limite raisonable de temps à définir
 *  Donc elle doit être enregistrée sur un exercisse ouvert
 *  c'est à dire Sur l'exercisse comptable en cours   
 */
	public class ExercisseClotureException extends DateException {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExercisseClotureException() {
		super();
	}

	public ExercisseClotureException(String message) {
		super(message);
	}

}
