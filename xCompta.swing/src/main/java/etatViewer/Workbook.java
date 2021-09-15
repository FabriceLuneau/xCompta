package etatViewer;

import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;


public class Workbook extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	
	protected JPanel			panel			= new JPanel();
    protected JScrollPane   	worksheetPane	= new JScrollPane();

    protected JMenuBar			menuBar			= new JMenuBar();

    protected TableModel        tableModel;
    protected TableColumnModel  columnModel;
    protected JTable            table			= new JTable();
    
    protected Worksheet			datas 			= new Worksheet();
    
    protected File				file			= null;
    
    protected JMenu menuFichier = new JMenu("Fichier");
    protected JMenuItem itemOuvir = new JMenuItem("Ouvrir");
    protected JMenuItem itemSauver = new JMenuItem("Sauver");
    protected JMenuItem itemSauverSous = new JMenuItem("Sauver sous");


    public Workbook() {
    	setResizable(true);
    	setIconifiable(true);
    	setMaximizable(true);
    	setClosable(true);
    	setSize(500,500);

        tableModel = new MonTableModel();
        table.getTableHeader().setReorderingAllowed(false);

        table.setModel(tableModel);
        table.setCellSelectionEnabled(true);
        
        setContentPane(panel);

        panel.add(worksheetPane);
        
        //panel.add(menu);
        /*JMenu menuFichier = new JMenu("Fichier");
        JMenuItem itemOuvir = new JMenuItem("Ouvrir");
        JMenuItem itemSauver = new JMenuItem("Sauver");
        JMenuItem itemSauverSous = new JMenuItem("Sauver sous");*/
        menuFichier.add(itemOuvir);
        menuFichier.add(itemSauver);
        menuFichier.add(itemSauverSous);
        menuBar.add(menuFichier);
        setJMenuBar(menuBar);
        
        itemSauverSous.setEnabled(false);

        worksheetPane.setViewportView(table);

        //menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		setVisible(true);

		itemOuvir.addActionListener(
				event -> {
        		datas.reset();
	    	    JFileChooser fileChooser = new JFileChooser("Tableur");
	    	    fileChooser.showOpenDialog(null);
	    	    //datas.open(fileChooser.getSelectedFile());
	    	    file = fileChooser.getSelectedFile();
	    	    datas.open(file);
	    	    itemSauverSous.setEnabled(true);
    	});

		itemSauverSous.addActionListener(
				event -> {
	    	    JFileChooser fileChooser = new JFileChooser("Tableur");
	    	    fileChooser.showOpenDialog(null);
	    	    datas.save(fileChooser.getSelectedFile());
    	});
    
		itemSauver.addActionListener(
				event -> {
    			if(file != null) {
    				itemSauverSous.setEnabled(true);
    				datas.save(file);
    			} else {
    				itemSauverSous.setEnabled(false);
    			}
    				

    	    	
		});
	}

    //class MonColumnModel extends DefaultTableColumnModel {}
    //class MonRowModel extends DefaultTableCellRenderer {}
    
    public Worksheet getWorksheet() {
    	return datas;
    }
    
    class MonTableModel extends AbstractTableModel {
    	private static final long serialVersionUID = 1L;
    	
    	public MonTableModel() {
    		super();
		}

        public Object getValueAt(int column, int row) {
        	Cell cell = datas.get(row, column);

        	if(cell == null)
        		return null;
        	else
        		return cell.getValue();
        }

        public int getColumnCount() {
            return 20;
        }

        public int getRowCount() {
            return 100;
        }

        @Override
        public String getColumnName(int column) {
        	return Integer.toString(column);
        }
        
        @Override
        public boolean isCellEditable(int row, int column) {
            return true;
        }
@Override
        public void setValueAt(Object valeur, int row,int column) {
        	datas.put(column, row, valeur.toString());
        }
    }
}