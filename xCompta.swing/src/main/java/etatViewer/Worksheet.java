package etatViewer;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


public class Worksheet {
    protected HashMap <String, Cell> datas = new HashMap<String, Cell>();
    //protected HashMap	columnss	= new HashMap();
    //protected HashMap	row			= new HashMap();


    public void put(String coord, String value) {
    	datas.put(coord, new Cell(value));
    }

    public void put(int column, int row, String value) {
    	String coord = Integer.toString(column) + "," + Integer.toString(row); 
    	datas.put(coord, new Cell(value));
    }

    public void put(int column, int row, Cell value) {
    	String coord = Integer.toString(column) + "," + Integer.toString(row); 
    	datas.put(coord, value);
    }
    
    public void put(String coord, Cell value) {
    	datas.put(coord, value);
    }

    public Cell get(String coord) {
    	return datas.get(coord);
    }

    public Cell get(int column, int row) {
    	String coord = Integer.toString(column) + "," + Integer.toString(row);
    	return datas.get(coord);
    }

    public void save(File file) {
		Document document = DocumentHelper.createDocument();
		Element root;
		Element element;
		
		document = DocumentHelper.createDocument();
		root = document.addElement("worksheet").addElement("cells");

    	for (Iterator<String> i = datas.keySet().iterator(); i.hasNext();) {
    		String coord = i.next();
			Cell cell = this.datas.get(coord);

			root.addElement("cell").
			addText( cell.getValue() ).
			addAttribute("coord", coord).
			getParent();
		}

		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter writer = new XMLWriter(new FileWriter(file), format);
			writer.write(document);
			writer.close();
		} catch (Exception e) {
			System.out.println(e);		
		}
    }
    
    public void open(File file) {
		try {
			SAXReader reader = new SAXReader();
			org.dom4j.Document document = reader.read(file);
			Element root = document.getRootElement();
	
			for(Iterator i = root.elementIterator(); i.hasNext(); ) {
				Element elementCells = (Element)i.next();
				
				for(Iterator j = elementCells.elementIterator(); j.hasNext(); ) {
					Element elementCell = (Element)j.next();

					if(elementCell.getName().compareTo("cell") == 0) {
						this.put(
							elementCell.attributeValue("coord"),
							new Cell(elementCell.getText()));

						System.out.println(
								elementCell.attributeValue("coord") + "-" +
								elementCell.getText());

					}					
				}				
			}
		} catch(Exception e) {
			System.out.println(e);
		}
    }
    
    public void reset() {
    	datas = new HashMap<String, Cell>();
    }

    public static void main(String [] args) {
        Workbook classeur = new Workbook();

    	JFrame frame = new JFrame();
		JDesktopPane bureau = new JDesktopPane();
        bureau.setBackground(Color.gray);
        frame.setSize(1000,700);
        frame.setContentPane(bureau);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		JInternalFrame fenetreInterne = new Workbook(); 
        fenetreInterne.setVisible(true);
        try {
        fenetreInterne.setMaximum(true);
        } catch (Exception e) {
        	//Todo faire quelque chose avec les exceptions
        	
        }
        bureau.add(fenetreInterne);
        fenetreInterne.toFront();
    }
}