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
 * ActualHoursTest verifies the functionality of the actualEffort functions.
 */
public class ActualHoursTest {
	
	Task newTask, anotherTask;
	
	@Before
	public void setUp() throws Exception {
		CalendarDate sd = new CalendarDate();
		CalendarDate ed = new CalendarDate(sd.getDay()+1, sd.getMonth()+1, sd.getYear()+1);
		newTask = CurrentProject.getTaskList().createTask(sd, ed, "Stuff", 2, (long)4.0, (long)1.5, 2, 365, "Things", "PLANNING", null);
		anotherTask = CurrentProject.getTaskList().createTask(sd, ed, "Stuff2", 2, (long)4.0, (long)4.0, 2, 365, "Thingies", "CODE_REVIEW", null);
	}
	
	/**
	 * Test method to verify actEffort amount is stored and invalid entries do not 
	 * affect previously stored values.
	 */
	@Test
	public void testSetActEffort() {
		assertEquals((long)1.5, newTask.getActEffort(), (long)0.0);
		newTask.setActEffort((long)2.75);
		assertEquals((long)2.75, newTask.getActEffort(), (long)0.0);
		newTask.setActEffort((long)-3.5);
		assertEquals((long)2.75, newTask.getActEffort(), (long)0.0);
	}
	
	/**
	 * Test method to verify all areas of getActEffort are covered and work properly.
	 */
	@Test
	public void testGetActEffort(){
		assertEquals((long)4.0, anotherTask.getActEffort(), (long)0.0);
		assertEquals((long)0, anotherTask.getActEffortExcTest(), (long)0.0);
		assertEquals((long)0, anotherTask.getActEffortNullTest(), (long)0.0);
	}

}
