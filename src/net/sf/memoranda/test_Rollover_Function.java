package net.sf.memoranda;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Attribute;
import nu.xom.Element;

public class test_Rollover_Function {

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
	
	private long task1loc = 0;
	private long task2loc = 0;
	
	private int task1def = 0;
	private int task2def = 0;
	
	private long task1acteff = 0;
	private long task2acteff = 0;
	
	private CalendarDate sd;
	private CalendarDate ed;
		
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		startDate = new CalendarDate();

		/*endDate = new CalendarDate(
				startDate.getMonth()+1, 
				startDate.getDay()+1, 
				startDate.getYear()+1);
		testTask1 = CurrentProject.getTaskList().createTask(
				startDate, endDate, "testTitle1", 
				1, 1, 1, 5, 100, "testDesc1", null);
		testTask2 = CurrentProject.getTaskList().createTask(
				startDate, endDate, "testTitle2", 
				2, 1, 1, 10, 250, "testDesc2", null);*/

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
	
	// createTask(sd, ed, dlg.todoField.getText(),  dlg.priorityCB.getSelectedIndex(), 
	//		effort, actEffort, numDefects, locode, dlg.descriptionField.getText(),null);
	
	// start date, end date, title, priority, effort, acteffort, defects, loc, description, parent
	
	 @Test
	 public void testRollLOC() {
		 
       sd = testTask1.getStartDate();
       ed = testTask1.getEndDate();
		 
		Task testTask1a = CurrentProject.getTaskList().createTask(sd, ed, "testTitle1a", 
				1, 2, 6, 10, 300, "testDesc3a", "PLANNING",
				testTask1.getID());
		Task testTask1b = CurrentProject.getTaskList().createTask(sd, ed, "testTitle1b", 
				2, 5, 7, 5, 200, "testDesc3b", "PLANNING",
				testTask1.getID());
		
		task1loc = CurrentProject.getTaskList().calculateTotalLOCFromSubTasks(testTask1);
		
		// Asserts that testTask1a (300) + testTask1b (200) = 500
		assertEquals(task1loc, 500);
		
		task1def = CurrentProject.getTaskList().calculateTotalDefectsFromSubTasks(testTask1);
		
		// Asserts that testTask1a (10) + testTask1b (5) = 15
		assertEquals(task1def, 15);
		
		task1acteff = CurrentProject.getTaskList().calculateTotalActEffortFromSubTasks(testTask1);
		
		// Asserts that testTask1a (6) + testTask1b (7) = 13
		assertEquals(task1acteff, 13);
		
       sd = testTask2.getStartDate();
       ed = testTask2.getEndDate();
       
    // start date, end date, title, priority, effort, acteffort, defects, loc, description, parent
		
		Task testTask2a = CurrentProject.getTaskList().createTask(sd, ed, "testTitle2a", 
				1, 2, 6, 10, 200, "testDesc2a", "PLANNING",
				testTask2.getID());
		Task testTask2b = CurrentProject.getTaskList().createTask(sd, ed, "testTitle2b", 
				2, 5, 7, 5, 100, "testDesc2b", "PLANNING",
				testTask2.getID());
		
		task2loc = CurrentProject.getTaskList().calculateTotalLOCFromSubTasks(testTask2);
		
		// Asserts that testTask2a (200) + testTask2b(100) do not equal 500 (original testTask2 value)
		assertFalse(task2loc == 500);
		
		task2def = CurrentProject.getTaskList().calculateTotalDefectsFromSubTasks(testTask2);
		
		// Asserts that testTask2a (10) + testTask2b(5) do not equal 1 (original testTask2 value)
		assertFalse(task2def == 1);
		
		task2acteff = CurrentProject.getTaskList().calculateTotalActEffortFromSubTasks(testTask2);
		
		// Asserts that testTask2a (6) + testTask2b(7) do not equal 1 (original testTask2 value)
		assertFalse(task2acteff == 1);
	 }
}
