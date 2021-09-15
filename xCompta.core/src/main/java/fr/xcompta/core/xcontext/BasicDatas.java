package fr.xcompta.core.xcontext;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import fr.xcompta.core.base.Classe;
import fr.xcompta.core.base.Journal;

public class BasicDatas {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BasicDatas.class);
	
	
	private BasicDatas() {}

	public static List<Classe> getClasses() {
		logger.info("Création des classes de comptes de bases");

		List<Classe> classes = new ArrayList<Classe>();

		classes.add(new Classe((short) 1, "Comptes de capitaux"));
		classes.add(new Classe((short) 2, "Comptes d'immobilisations"));
		classes.add(new Classe((short) 3, "Comptes de stocks et en-coours"));
		classes.add(new Classe((short) 4, "Comptes de tiers"));
		classes.add(new Classe((short) 5, "Comptes financiers"));
		classes.add(new Classe((short) 6, "Comptes de charges"));
		classes.add(new Classe((short) 7, "Comptes de produits"));
		classes.add(new Classe((short) 8, "Comptes sp�ciaux"));
		classes.add(new Classe((short) 9, "Comptes analytiques d'exploitation"));
		
		return classes;
	}

	public static List<Journal> getJournaux() {
		List<Journal> journaux = new ArrayList<Journal>();

		journaux.add(new Journal("AC", "Journal des achats"));
		journaux.add(new Journal("VE", "Journal des ventes"));

		journaux.add(new Journal("CAI", "Journal de caisse"));
		journaux.add(new Journal("BQ", "Journal de banque"));
		journaux.add(new Journal("OD", "Opérations diverses"));
		journaux.add(new Journal("AN", "Journal des à nouveaux"));

		return journaux;
	}
}	
