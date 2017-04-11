/**
 * WorkPanelTest.java
 * 
 * @author mzomorod
 */
package net.sf.memoranda.ui;

import static org.junit.Assert.*;
 
import org.junit.Before;
import org.junit.Test;

import javax.swing.JFrame;

/**
 * WorkPanelTest verifies the functionality the the 
 *
 */
public class WorkPanelTest {
	
	JFrame testFrame =  new JFrame();
	WorkPanel testPanel = new WorkPanel();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testFrame.add(testPanel);
	}

	/**
	 * Test method to verify PSP button opens PSP form.
	 */
	@Test
	public void testPspB_actionPerformed() {
		testFrame.pack();
		testFrame.setVisible(true);
		testPanel.pspB_actionPerformed(null);
		assertTrue(testPanel.dailyItemsPanel.getCurrentPanel() == "PSP");
	}

}
