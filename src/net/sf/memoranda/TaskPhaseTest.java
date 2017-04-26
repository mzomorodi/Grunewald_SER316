/*
  File:	TaskPhaseTest.java
  Author: Matthew Seiler
  Date:	4/24/2017
  
  Description: JUnit tests for phase selection in tasks
*/
package net.sf.memoranda;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Element;

/**
Class:	TaskPhaseTest

Description: tests functionality for setting phase on tasks
*/
public class TaskPhaseTest {
	
	private static Task testTask1;
	private static Task testTask2;
	private static Task testTaskNull;
	private static String idTestTask1;
	private static String idTestTask2;
	private static Element taskElementNull;
	
	private static CalendarDate startDate;
	private static CalendarDate endDate;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		startDate = new CalendarDate();
		endDate = new CalendarDate(startDate.getMonth()+1, startDate.getDay()+1, startDate.getYear()+1);
		testTask1 = CurrentProject.getTaskList().createTask(startDate, endDate, "testTitle1", 1, 7, 3, 8, 480, "testDesc1", "PLANNING", null);
		testTask2 = CurrentProject.getTaskList().createTask(startDate, endDate, "testTitle2", 2, 4, 10, 20, 2050, "testDesc2", "DESIGN", null);
		idTestTask1 = testTask1.getID();
		idTestTask2 = testTask2.getID();
		
		taskElementNull = new Element("task");
		testTaskNull = new TaskImpl(taskElementNull, CurrentProject.getTaskList());	
	}
	
	/**
	 * testGetPhase checks if phase is read from storage
	 */
	@Test
	public void testGetPhase() {
		assertEquals("PLANNING", CurrentProject.getTaskList().getTask(idTestTask1).getPhase());
		
		// test null attribute
		assertEquals(null, taskElementNull.getAttribute("phase"));
		assertEquals(null, testTaskNull.getPhase());
	}
	
	/**
	 * testSetPhase checks if phase amount is stored
	 */
	@Test
	public void testSetPhase() {
		assertEquals("DESIGN", CurrentProject.getTaskList().getTask(idTestTask2).getPhase());
		
		testTask2.setPhase("CODE_REVIEW");
		assertEquals("CODE_REVIEW", CurrentProject.getTaskList().getTask(idTestTask2).getPhase());
	}
}