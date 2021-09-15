import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import fr.xcompta.connector.hibernate.XContextHibernate5;
import fr.xcompta.core.xcontext.exception.XComptaConfigurationException;

public class StartTest {
	public static void main(String [] args) {
	try(XContextHibernate5 xContext = new XContextHibernate5("conf/baba.conf")) {
//		try(XContextInterface xContext = new XContextHibernate5("conf/test.conf")) {
	xContext.open();
	
	
//	try {
	XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("personne.xml")));

	
	System.out.println("****" + xContext.getClasses().size());
	
	
		encoder.writeObject(xContext.getClasses());
	      encoder.flush();
//	} catch(Exception e ) {
//		e.printStackTrace();
//	}
	//teste


	
	
	
	
	//Fin tests
} catch (XComptaConfigurationException ex) {
	ex.printStackTrace();
} catch (FileNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	}

}
