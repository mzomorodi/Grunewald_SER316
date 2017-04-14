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
		private static Task testTask3;
		private static Task testTask4;
		
		private static Task testTaskNull;
		private static Task testTaskNFE;
		
		private static String idTestTask1;
		private static String idTestTask2;
		private static String idTestTask3;
		private static String idTestTask4;
		
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
			testTask1 = CurrentProject.getTaskList().createTask(startDate, endDate, "testTitle1", 1, 5, 100, "testDesc1", null);
			testTask2 = CurrentProject.getTaskList().createTask(startDate, endDate, "testTitle2", 2, 10, 250, "testDesc2", null);
			testTask3 = CurrentProject.getTaskList().createTask(startDate, endDate, "testTitle3", 2, 10, 500, "testDesc3", null);
			testTask4 = CurrentProject.getTaskList().createTask(startDate, endDate, "testTitle4", 2, 10, 500, "testDesc4", null);
				
			idTestTask1 = testTask1.getID();
			idTestTask2 = testTask2.getID();
			idTestTask3 = testTask3.getID();
			idTestTask4 = testTask4.getID();			
			
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
		 	System.out.println(taskElementNFE.getAttribute("LOC").getValue());
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
		 @Test
		 public void testRollLOC() {
			 
	        sd = testTask3.getStartDate();
            ed = testTask3.getEndDate();
			 
			Task testTask3a = CurrentProject.getTaskList().createTask(sd, ed, "testTitle3a", 
					1, 10, 300, "testDesc3a",
					testTask3.getID());
			Task testTask3b = CurrentProject.getTaskList().createTask(sd, ed, "testTitle3b", 
					2, 5, 200, "testDesc3b",
					testTask3.getID());
			
			task3loc = CurrentProject.getTaskList().calculateTotalLOCFromSubTasks(testTask3);
			
			// Asserts that testTask3a (300) + testTask3b (200) = 500
			assertEquals(task3loc, 500);
			
	        sd = testTask3.getStartDate();
            ed = testTask3.getEndDate();
			
			Task testTask4a = CurrentProject.getTaskList().createTask(sd, ed, "testTitle4a", 
					1, 10, 200, "testDesc4a",
					testTask4.getID());
			Task testTask4b = CurrentProject.getTaskList().createTask(sd, ed, "testTitle4b", 
					2, 5, 100, "testDesc4b",
					testTask4.getID());
			
			task4loc = CurrentProject.getTaskList().calculateTotalLOCFromSubTasks(testTask4);
			
			// Asserts that testTask4a (200) + testTask4b(100) do not equal 500 (original testTask4 value)
			assertFalse(task4loc == 500);
		        
		
		 }

}
