package bogg;

import java.awt.Color;
import java.awt.SystemColor;
import java.text.DecimalFormat;

import javax.swing.JColorChooser;
import javax.swing.JFrame;

public class preferences {
	public static Color	oddRowColor				=	Color.WHITE;
	public static Color	evenRowColor			=	Color.getHSBColor(20, 120, 120);

	public static Color backGroundColor			=	Color.black;
	public static Color selectedBackGroundColor	=	SystemColor.textHighlight;
	public static Color selectedForeGroundColor	=	SystemColor.textHighlight;
	
	public static int	nbDigitCompte			=	6;
	
	public static DecimalFormat amountFormat	= new DecimalFormat("### ### ### ##0.00");


	public static void main(String []args) {
		JFrame frame = new JFrame();

        frame.setSize(1000,700);
        frame.setContentPane(new JColorChooser());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}