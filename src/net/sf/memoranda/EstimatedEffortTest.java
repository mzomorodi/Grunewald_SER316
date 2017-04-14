/**
 * EstimatedEffortTest.java
 * 
 * @author bhood2
 */
package net.sf.memoranda;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.sf.memoranda.date.CalendarDate;

/**
 * EstimatedEffortTest verifies the functionality of the effort functions.
 */
public class EstimatedEffortTest {
	Task test1, test2;

	@Before
	public void setUp() throws Exception {
		CalendarDate sd = new CalendarDate();
		CalendarDate ed = new CalendarDate(sd.getMonth()+1, sd.getDay()+1, sd.getYear()+1);
		test1 = CurrentProject.getTaskList().createTask(sd, ed, "GSD", 2, (long)4.0, (long)1.5, "The Stuff to Get Done", null);
		test2 = CurrentProject.getTaskList().createTask(sd, ed, "GSDN", 2, (long)4.0, (long)4.0, "The Stuff to Get Done Now", null);
	}

	/**
	 * Test method to verify effort amount is stored and invalid entries do not 
	 * affect previously stored values.
	 */
	@Test
	public void testSetEffort() {
		assertEquals((long)4.0, test1.getEffort(), (long)0.0);
		test1.setEffort((long)2.75);
		assertEquals((long)2.75, test1.getEffort(), (long)0.0);
		test1.setEffort((long)-3.5);
		assertEquals((long)2.75, test1.getEffort(), (long)0.0);
	}

	/**
	 * Test method to verify all areas of getEffort are covered and work properly.
	 */
	@Test
	public void testGetEffort() {
		assertEquals((long)4.0, test2.getEffort(), (long)0.0);
		assertEquals((long)0, test2.getEffortExcTest(), (long)0.0);
		assertEquals((long)0, test2.getEffortNullTest(), (long)0.0);
	}

}
