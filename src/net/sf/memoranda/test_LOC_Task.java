package net.sf.memoranda;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Attribute;
import nu.xom.Element;

public class test_LOC_Task {
	
	// I used mdseiler's implementation of the J Unit for the defects #44 to streamline my unit testing
	
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
		
		private long task3loc = 0;
		private long task4loc = 0;
		
		private CalendarDate sd;
		private CalendarDate ed;
			
		/**
		 * @throws java.lang.Exception
		 */
		@BeforeClass
		public static void setUpBeforeClass() throws Exception {
			startDate = new CalendarDate();
			endDate = new CalendarDate(startDate.getMonth()+1, startDate.getDay()+1, startDate.getYear()+1);
			testTask1 = CurrentProject.getTaskList().createTask(startDate, endDate, "testTitle1", 1, 1, 1, 5, 100, "testDesc1", "PLANNING", null);
			testTask2 = CurrentProject.getTaskList().createTask(startDate, endDate, "testTitle2", 2, 1, 1, 10, 250, "testDesc2", "PLANNING", null);
				
			idTestTask1 = testTask1.getID();
			idTestTask2 = testTask2.getID();
			
			taskElementNull = new Element("task");
			testTaskNull = new TaskImpl(taskElementNull, CurrentProject.getTaskList());
			
			taskElementNFE = new Element("task");
			taskElementNFE.addAttribute(new Attribute("LOC", "notNumber"));
			testTaskNFE = new TaskImpl(taskElementNFE, CurrentProject.getTaskList());
		}
		
		  /**
		  * testGetLOC checks if Lines of Code is read from storage
		  */
		 @Test
		 public void testGetLOC() {
		 	assertEquals(100, CurrentProject.getTaskList().getTask(idTestTask1).getLOC());
		 	
		 	// test null attribute
		 	assertEquals(null, taskElementNull.getAttribute("LOC"));
		 	assertEquals(0, testTaskNull.getLOC());
		 	
		 	// test NumberFormatException
		 	assertEquals("notNumber", taskElementNFE.getAttribute("LOC").getValue());
		 	assertEquals(0, testTaskNFE.getLOC());
		 }
		 
		 /**
		  * testSetLOC checks if LOC is stored
		  */
		 @Test
		 public void testSetNumDefects() {
		 	assertEquals(250, CurrentProject.getTaskList().getTask(idTestTask2).getLOC());
		 	
		 	testTask2.setLOC(500);
		 	assertEquals(500, CurrentProject.getTaskList().getTask(idTestTask2).getLOC());
		 }
		 
		 /**
		  * testRollLOC checks if subtasks roll the LOC to the main task correctly
		  * also tests to make sure that the value is overwritten and changed appropriately
		  */

		        
		
}
