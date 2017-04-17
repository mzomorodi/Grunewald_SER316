package net.sf.memoranda;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Vector;

import net.sf.memoranda.date.CalendarDate;

public class TimeEntryTest {
	CalendarDate date = new CalendarDate();
	TimeEntryList tel1 = new TimeEntryList();
	TimeEntryList tel2 = new TimeEntryList();
	TimeEntry te1 = tel1.createTimeEntry("id1", date, "1", "2", "900", "1000", "15+5", "PLANNING", "comments 1");
	TimeEntry te2 = tel2.createTimeEntry("id2", date, "2", "3", "1100", "1200", "20+10", "DESIGN", "comments 2");
	
	@Test
	public void testGetContent() {
		Vector<TimeEntry> v1 = tel1.getAllTimeEntries();
		TimeEntry testTimeEntry = (TimeEntry)v1.get(0);
		assertTrue(testTimeEntry.getContent().equals(te1.getContent()));
	}

	@Test
	public void testGetDate() {
		assertTrue(te1.getDate().equals(new CalendarDate()));
	}
	
	@Test
	public void testGetID() {
		assertTrue(te2.getID().equals("id2"));
	}

	@Test
	public void testGetLocStart() {
		assertTrue(te2.getLocStart().equals("2"));
	}
	
	@Test
	public void testGetLocEnd() {
		assertTrue(te2.getLocEnd().equals("3"));
	}

	@Test
	public void testGetStartTime() {
		assertTrue(te1.getStartTime().equals("900"));
	}

	@Test
	public void testGetStopTime() {
		assertTrue(te2.getStopTime().equals("1200"));
	}

	@Test
	public void testGetInterruptTime() {
		assertTrue(te1.getInterruptTime().equals("15+5"));
	}

	@Test
	public void testGetPhase() {
		assertTrue(te1.getPhase().equals("PLANNING"));
	}
	
	@Test
	public void testGetComments() {
		assertTrue(te1.getComments().equals("comments 1"));
	}
	
	@Test
	public void testSetDate() {
		CalendarDate d = new CalendarDate();
		te1.setDate(d.toString());
		assertTrue(te1.getDate().equals(d));
	}
	
	@Test
	public void testSetID() {
		te2.setID("id2.1");
		assertTrue(te2.getID().equals("id2.1"));
	}

	@Test
	public void testSetLocStart() {
		te2.setLocStart("200");
		assertTrue(te2.getLocStart().equals("200"));
	}
	
	@Test
	public void testSetLocEnd() {
		te2.setLocEnd("300");
		assertTrue(te2.getLocEnd().equals("300"));
	}

	@Test
	public void testSetStartTime() {
		te1.setStartTime("1400");
		assertTrue(te1.getStartTime().equals("1400"));
	}

	@Test
	public void testSetStopTime() {
		te2.setStopTime("1500");
		assertTrue(te2.getStopTime().equals("1500"));
	}

	@Test
	public void testSetInterruptTime() {
		te1.setInterruptTime("50+20");
		assertTrue(te1.getInterruptTime().equals("50+20"));
	}

	@Test
	public void testSetPhase() {
		te1.setPhase("PHASE1");
		assertTrue(te1.getPhase().equals("PHASE1"));
	}
	
	@Test
	public void testSetComments() {
		te1.setComments("comments 10");
		assertTrue(te1.getComments().equals("comments 10"));
	}
}
