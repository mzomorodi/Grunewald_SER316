/**
 * GetStatusTest.java 
 * 
 * @author bhood2
 */
package net.sf.memoranda;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.sf.memoranda.date.CalendarDate;

/**
 * GetStatusTest verifies the functionality of the getStatus method
 */
public class GetStatusTest {
	Task gstest1, gstest2, gstest3, gstest4, gstest5, gstest6, gstest7;
	CalendarDate sd, ed;

	/**
	 * setUp test environment
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//ACTIVE
		sd = new CalendarDate(10, 4, 2017);
		ed = new CalendarDate(10, 4, 2018);
		gstest1 = CurrentProject.getTaskList().createTask(
				sd, ed, "GSD", 2, (long)4.0, 
				(long)1.5, 2, 365, "The Stuff to Get Done", null);
		//SCHEDULED
		sd = new CalendarDate(20, 4, 2017);
		ed = null;
		gstest2 = CurrentProject.getTaskList().createTask(
				sd, ed, "GSD", 2, (long)4.0, 
				(long)1.5, 2, 365, "The Stuff to Get Done", null);
		//FROZEN
		sd = new CalendarDate(10, 4, 2017);
		ed = new CalendarDate(20, 4, 2017);
		gstest3 = CurrentProject.getTaskList().createTask(
				sd, ed, "GSD", 2, (long)4.0, 
				(long)1.5, 2, 365, "The Stuff to Get Done", null);
		gstest3.freeze();
		//DEADLINE
		sd = new CalendarDate(10, 4, 2017);
		ed = new CalendarDate(15, 4, 2017);
		gstest4 = CurrentProject.getTaskList().createTask(
				sd, ed, "GSD", 2, (long)4.0, 
				(long)1.5, 2, 365, "The Stuff to Get Done", null);
		//COMPLETED
		sd = new CalendarDate(10, 4, 2017);
		ed = new CalendarDate(20, 4, 2017);
		gstest5 = CurrentProject.getTaskList().createTask(
				sd, ed, "GSD", 2, (long)4.0, 
				(long)1.5, 2, 365, "The Stuff to Get Done", null);
		gstest5.setProgress(100);
		//FAILED
		sd = new CalendarDate(10, 4, 2017);
		ed = new CalendarDate(14, 4, 2017);
		gstest6 = CurrentProject.getTaskList().createTask(
				sd, ed, "GSD", 2, (long)4.0, 
				(long)1.5, 2, 365, "The Stuff to Get Done", null);
		//ACTIVE
		sd = new CalendarDate(10, 4, 2017);
		ed = new CalendarDate(10, 4, 2016);
		gstest7 = CurrentProject.getTaskList().createTask(
				sd, ed, "GSD", 2, (long)4.0, 
				(long)1.5, 2, 365, "The Stuff to Get Done", null);
				
	}

	/**
	 * Test method for {@link net.sf.memoranda.TaskImpl#getStatus(net.sf.memoranda.date.CalendarDate)}.
	 */
	@Test
	public void testGetStatus() {
		assertEquals(1, gstest1.getStatus(new CalendarDate(15, 4, 2017)));
		assertEquals(0, gstest2.getStatus(new CalendarDate(15, 4, 2017)));
		assertEquals(4, gstest3.getStatus(new CalendarDate(15, 4, 2017)));
		assertEquals(7, gstest4.getStatus(new CalendarDate(15, 4, 2017)));
		assertEquals(2, gstest5.getStatus(new CalendarDate(15, 4, 2017)));
		assertEquals(5, gstest6.getStatus(new CalendarDate(15, 4, 2017)));
		assertEquals(1, gstest7.getStatus(new CalendarDate(15, 4, 2017)));
	}

}
