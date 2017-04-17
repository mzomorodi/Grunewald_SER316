/**
 * PSPPanelTest.java
 * 
 * @author mzomorod
 */

package net.sf.memoranda.ui;

import static org.junit.Assert.*;

import javax.swing.JFrame;

import org.junit.Before;
import org.junit.Test;

/**
 * PSPPanelTest verifies the functionality of the Defect and Time views.
 *
 */
public class PSPPanelTest {
	
	JFrame testFrame =  new JFrame();
	PSPPanel testPanel = new PSPPanel();

	@Before
	public void setUp() throws Exception {
		testFrame.add(testPanel);
		testFrame.pack();
		testFrame.setVisible(true);
	}

	@Test
	public void testDefectB_actionPerformed() {
		testPanel.defectB_actionPerformed(null);
		assertTrue(testPanel.defectsPanel.isVisible() == true);
		assertTrue(!(testPanel.timesPanel.isVisible() == true));
	}

	@Test
	public void testTimeB_actionPerformed() {
		testPanel.timeB_actionPerformed(null);
		assertTrue(testPanel.timesPanel.isVisible() == true);
		assertTrue(!(testPanel.defectsPanel.isVisible() == true));
	}

}
