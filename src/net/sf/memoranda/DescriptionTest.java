/**
 * DescriptionTest.java
 * 
 * @author bhood2
 */
package net.sf.memoranda;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.sf.memoranda.date.CalendarDate;

/**
 * DescriptionTest verifies the functionality of the getDescription 
 * and the setDescription methods.
 */
public class DescriptionTest {
	Task desTest1, desTest2;
	CalendarDate sd, ed;
	String str, tst;
	
	/**
	 * setUp test environment
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		sd = ed = new CalendarDate();
		desTest1 = CurrentProject.getTaskList().createTask(sd, ed, "GSD", 2, (long)4.0, (long)1.5, 2, 365, "The Stuff to Get Done", null);
		desTest2 = CurrentProject.getTaskList().createTask(sd, ed, "GSD", 2, (long)4.0, (long)1.5, 2, 365, null, null);
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskImpl#getDescription()}.
	 */
	@Test
	public void testGetDescription() {
		str = "The Stuff to Get Done";
		assertEquals(str, desTest1.getDescription());
		desTest1.remChildElement("description");
		assertEquals("", desTest2.getDescription());
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskImpl#setDescription(java.lang.String)}.
	 */
	@Test
	public void testSetDescription() {
		tst = "Test string";
		desTest2.setDescription(tst);
		assertEquals(tst, desTest2.getDescription());
		desTest2.remChildElement("description");
		assertNull(desTest2.getDescription());
		desTest2.setDescription(tst);
		assertEquals(tst, desTest2.getDescription());
	}

}
