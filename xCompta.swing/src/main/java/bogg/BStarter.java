package bogg;

import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.slf4j.LoggerFactory;

import fr.xcompta.connector.hibernate.XContextHibernate5;
import fr.xcompta.core.xcontext.XContextInterface;
import fr.xcompta.core.xcontext.exception.XComptaConfigurationException;

/**
 * @author Fabrice LUNEAU
 *
 *         Fenêtre de lancement de l'application Permet de choisir un fichier de
 *         configuration et d'initialiser un xContexte, seulement hibrernate
 *         pour le moment
 * 
 */
public class BStarter extends JFrame {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BStarter.class);

	private static final long serialVersionUID = 1L;

	private JPanel panel = new JPanel();

	private JTextField fieldUser = new JTextField(15);
	private JPasswordField fieldPassword = new JPasswordField(15);
	private JTextField fieldHost = new JTextField(15);

	private JButton buttonConnect = new JButton("Connection");

	private JComboBox<File[]> comboConfiguration;

	private ArrayList<File> fichiersConfigurations = new ArrayList<File>();

	public BStarter() {
		init();
		
	}
	
	private void init() {
		comboConfiguration = new JComboBox(chargerFichiersConfiguration().toArray());

		panel.setLayout(new GridLayout(8, 2));

		JLabel label;

		label = new JLabel("Utilisateur");
		label.setLabelFor(fieldUser);
		panel.add(label);
		panel.add(fieldUser);

		label = new JLabel("Mot de passe");
		label.setLabelFor(fieldPassword);
		panel.add(label);
		panel.add(fieldPassword);

		label = new JLabel("host");
		label.setLabelFor(fieldHost);
		panel.add(label);
		panel.add(fieldHost);

		label = new JLabel("Configuration");
		label.setLabelFor(comboConfiguration);
		panel.add(label);
		panel.add(comboConfiguration);

		panel.add(buttonConnect);

		setContentPane(panel);

		buttonConnect.addActionListener(
				event -> {
//				try (XContextInterface xContext = new XContextHibernate(getSelectedConfiguration().getAbsolutePath())) {
				try {
				XContextInterface xContext = new XContextHibernate5(getSelectedConfiguration().getAbsolutePath());
					xContext.open();
					new BBureau(xContext);
					dispose();
				} catch (XComptaConfigurationException ex) {
//								 TODO Auto-generated catch block
					ex.printStackTrace();
				}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(45, 300);
		setLocationRelativeTo(this.getParent());
	}

	private ArrayList<File> chargerFichiersConfiguration() {
		File dossierConfiguration = new File("conf");
		java.io.File[] fichiers = dossierConfiguration.listFiles();

		for (File fichier : fichiers) {
			if (fichier.getAbsoluteFile().toString().matches(".*.conf")) {
				fichiersConfigurations.add(fichier);
			}
		}
		return fichiersConfigurations;
	}

	public File getSelectedConfiguration() {
		return (File) comboConfiguration.getSelectedItem();
	}

	private String getConfigurationName(String filename) {
		String name = "Pas de nom";

		try (InputStream inputStream = new FileInputStream(filename)) {
			Properties properties = new Properties();
			properties.load(inputStream);

			logger.info("Configuration " + name + "trouvé");

			name = properties.getProperty("name");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}
}

//Netoyer le code
//faire la javadoc

//Logger plus si possible et mieux gérer les exception
//gérrer les erreurs
//Si il n'y a pas de fichiers dans le dossier
//proposer de créer une nouvelle configuration

//rendre le dossier "conf' parametrable

//Afficher le nom de la configuration dans la combo box
//s'appuyer sur getConfigurationName( )

//permettre de recharger la liste
// faire un bouton recharger ou rafraichir

