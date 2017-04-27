/*
  File:	TaskDefectsTest.java
  Author: Matthew Seiler
  Date:	4/13/2017
  
  Description: JUnit tests for defects count in tasks
*/
package net.sf.memoranda;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Attribute;
import nu.xom.Element;

/**
Class:	TaskDefectsTest

Description: tests functionality for adding/editing number of defects for tasks
*/
public class TaskDefectsTest {
	
	private static Task testTask1;
	private static Task testTask2;
	private static Task testTaskNull;
	private static Task testTaskNFE;
	private static String idTestTask1;
	private static String idTestTask2;
	private static Element taskElementNull;
	private static Element taskElementNFE;
	
	private static CalendarDate startDate;
	private static CalendarDate endDate;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		startDate = new CalendarDate();
		endDate = new CalendarDate(
				startDate.getMonth()+1, 
				startDate.getDay()+1, 
				startDate.getYear()+1);
		testTask1 = CurrentProject.getTaskList().createTask(
				startDate, endDate, "testTitle1", 
				1, 5, 2, 1, 362, "testDesc1", null);
		testTask2 = CurrentProject.getTaskList().createTask(
				startDate, endDate, "testTitle2", 
				2, 10, 5, 2, 1048, "testDesc2", null);
		idTestTask1 = testTask1.getID();
		idTestTask2 = testTask2.getID();
		
		taskElementNull = new Element("task");
		testTaskNull = new TaskImpl(taskElementNull, CurrentProject.getTaskList());
		
		taskElementNFE = new Element("task");
		taskElementNFE.addAttribute(new Attribute("numDefects", "notNumber"));
		testTaskNFE = new TaskImpl(taskElementNFE, CurrentProject.getTaskList());
		
	}
	
	/**
	 * testGetNumDefects checks if numDefects is read from storage
	 */
	@Test
	public void testGetDefects() {
		assertEquals(1, CurrentProject.getTaskList().getTask(idTestTask1).getNumDefects());
		
		// test null attribute
		assertEquals(null, taskElementNull.getAttribute("numDefects"));
		assertEquals(0, testTaskNull.getNumDefects());
		
		// test NumberFormatException
		System.out.println(taskElementNFE.getAttribute("numDefects").getValue());
		assertEquals("notNumber", taskElementNFE.getAttribute("numDefects").getValue());
		assertEquals(0, testTaskNFE.getNumDefects());
	}
	
	/**
	 * testSetNumDefects checks if numDefects amount is stored
	 */
	@Test
	public void testSetNumDefects() {
		assertEquals(2, CurrentProject.getTaskList().getTask(idTestTask2).getNumDefects());
		
		testTask2.setNumDefects(25);
		assertEquals(25, CurrentProject.getTaskList().getTask(idTestTask2).getNumDefects());
	}
}
