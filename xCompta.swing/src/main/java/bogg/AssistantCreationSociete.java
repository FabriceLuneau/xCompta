package bogg;
import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * @author Fabrice LUNEAU
 * 
 *         Mini Assistant de création de société Crée le fichier de
 *         configuration *.con.xml ajoute les journaux et un plan comptable
 *         selon des mod�les
 * 
 */
public class AssistantCreationSociete extends JFrame {
	private static final long serialVersionUID = 1L;

	protected Dimension size = new Dimension(500, 500);

	public AssistantCreationSociete() {
		super("Assistant de création");

		setSize(size);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

//	public static void main(String[] args) {
//	}

}

//Todo faire un assitant de création qui crée:
// le fichier de configuration .conf
//ajoute les infos de bases
// les classes les journaux les comptes d'après des plan comptables...
