package bogg;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class IfListFrames extends JInternalFrame {
	protected JScrollPane pane = new JScrollPane();
	private static final long serialVersionUID = 1L;
	
	protected JList<JInternalFrame> list;

	protected Dimension size = new Dimension(450, 250);

	public IfListFrames(JInternalFrame[] frames) {
		list = new JList(frames);

		setClosable(true);
		setSize(size);
		setContentPane(pane);
		pane.setViewportView(list);
		setVisible(true);
		pack();

		list.requestFocusInWindow();
		list.setSelectedIndex(1);

		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				JInternalFrame internalFrame = list.getSelectedValue();
				
				putToFront(internalFrame);
			}
		});
		
		list.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
			JInternalFrame internalFrame = list.getSelectedValue();
			
			putToFront(internalFrame);
				}
				}
		});
	}
	
	private void putToFront(JInternalFrame internalFrame) {

		if (!internalFrame.isMaximum()) {
			try {
				internalFrame.setIcon(false);
			} catch (Exception e) {
				//todo faire quelque chose
			}

			internalFrame.toFront();
			internalFrame.requestFocus();
			dispose();
		}
	}

	@Override
	public String toString() {
		return getTitle();
	}
}