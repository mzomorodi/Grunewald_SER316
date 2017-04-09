package net.sf.memoranda.ui;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.JFrame;

public class WorkPanelTest {
	
	JFrame testFrame =  new JFrame();
	WorkPanel testPanel = new WorkPanel();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		testFrame.add(testPanel);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPspB_actionPerformed() {
		testFrame.pack();
		testFrame.setVisible(true);
		testPanel.pspB_actionPerformed(null);
		assertTrue(testPanel.dailyItemsPanel.getCurrentPanel() == "PSP");
	}

}
