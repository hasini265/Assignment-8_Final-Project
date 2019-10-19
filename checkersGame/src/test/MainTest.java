package test;

import javax.swing.UIManager;
import org.junit.jupiter.api.Test;
import ui.CheckerWindow;

public class MainTest {

	@Test
	public void testMain() {
		
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
