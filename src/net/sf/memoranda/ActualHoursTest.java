/**
 * ActualHoursTest.java
 * 
 * @author bhood2
 */
package net.sf.memoranda;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.sf.memoranda.date.CalendarDate;

/**
 * ActualHoursTest verifies the functionality of the 
 * actualEffort functions.
 */
public class ActualHoursTest {
	
	Task newTask;
	
	@Before
	public void setUp() throws Exception {
		CalendarDate sd = new CalendarDate();
		CalendarDate ed = new CalendarDate(sd.getMonth()+1, sd.getDay()+1, sd.getYear()+1);
		newTask = CurrentProject.getTaskList().createTask(sd, ed, "Stuff", 2, (long)4.0, (long)1.5, "Things", null);
	}
	
	/**
	 * Test method to verify actEffort amount is stored.
	 */
	@Test
	public void testActHours() {
		assertEquals((long)1.5, newTask.getActEffort(), (long)0.0);
		newTask.setActEffort((long)2.75);
		assertEquals((long)2.75, newTask.getActEffort(), (long)0.0);
	}

}
