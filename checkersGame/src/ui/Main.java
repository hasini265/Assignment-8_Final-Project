/* This class contains the main method to create the GUI and
 * start the checkers game./
/*============================================================================*/
 /* JAVA CHECKERS
 * -------------
 * Author: M Chandrahasini
 * Created: 2019/10/18
 * Description: This program is a simple implementation of the standard
 * checkers game, with standard rules, in Java.
 */
/*============================================================================*/

package ui;

import javax.swing.UIManager;
import ui.CheckerWindow;

public class Main {
	
public static void main(String[] args) {
	
	//Set the look and feel to OS	
		try {
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Create a window to display the checkers game
		CheckerWindow window = new CheckerWindow();
		window.setDefaultCloseOperation(CheckerWindow.EXIT_ON_CLOSE);
		window.setVisible(true);
	}


}
