package net.sf.memoranda;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Vector;

import net.sf.memoranda.date.CalendarDate;

public class TimeEntryListTest {	
	TimeEntryList tel1;
	TimeEntryList tel2;
	TimeEntry te1;
	TimeEntry te2;
	TimeEntry te3;
	TimeEntry te4;
	String te1HashID;
	String te2HashID;
    
    CalendarDate date = new CalendarDate();
	
	@Before
	public void setUp() throws Exception {
		tel1 = new TimeEntryList(CurrentProject.get());
		tel2 = new TimeEntryList(CurrentProject.get());
		te1 = tel1.createTimeEntry("id1", date, "1", "2", "900", "1000", "15+5", "PLANNING", "comments 1");
		te2 = tel2.createTimeEntry("id2", date, "2", "3", "1100", "1200", "20+10", "DESIGN", "comments 2");
		te3 = tel1.createTimeEntry("id3", date, "3", "4", "900", "1900", "15+50", "PLANNING", "comments 3");
		te4 = tel2.createTimeEntry("id4", date, "4", "5", "1100", "2000", "20+100", "DESIGN", "comments 4");
		te1HashID = te1.getHashID();
		te2HashID = te2.getHashID();
	}
	
	@Test
	public void testGetAllTimeEntries() {
		Vector<TimeEntry> v1, v2;
		TimeEntry testTimeEntry;
		
		v1 = tel1.getAllTimeEntries();
		v2 = tel2.getAllTimeEntries();
		
		testTimeEntry = (TimeEntry)v1.get(0);
		assertTrue(tel1.getTimeEntry(testTimeEntry.getHashID()) != null);
		
		testTimeEntry = (TimeEntry)v2.get(0);
		assertTrue(tel2.getTimeEntry(testTimeEntry.getHashID()) != null);
		
		testTimeEntry = (TimeEntry)v1.get(1);
		assertTrue(tel1.getTimeEntry(testTimeEntry.getHashID()) != null);
		
		testTimeEntry = (TimeEntry)v2.get(1);
		assertTrue(tel2.getTimeEntry(testTimeEntry.getHashID()) != null);
		
		assertTrue(v1.size() == 2);
		assertTrue(v2.size() == 2);
	}
	
	@Test
	public void testGetTimeEntry() {
		TimeEntry te = tel1.getTimeEntry(te1HashID);
		assertEquals(te.getHashID(), te1.getHashID());
		te = tel2.getTimeEntry(te2HashID);
		assertEquals(te.getHashID(), te2.getHashID());
	}
	
	@Test
	public void testRemoveTimeEntry() {
		TimeEntry te = null;
		
		assertTrue(te == null);
		
		tel1.removeTimeEntry(tel1.getTimeEntry(te1HashID));
		te = tel1.getTimeEntry(te1HashID);
		assertTrue(te == null);
		
		
		te = tel2.getTimeEntry(te2HashID);
		assertEquals(te.getHashID(), te2.getHashID());
	}
}
