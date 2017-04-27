package net.sf.memoranda;

import static org.junit.Assert.*;

import org.junit.Test;

import net.sf.memoranda.date.CalendarDate;

public class EventTest {
	
	EventsManager em = new EventsManager();
	CalendarDate date = new CalendarDate().today();
	Event e1 = em.createEvent(date, 4, 30, "Let puppy out");
	Event e2 = em.createRepeatableEvent(1, date, date.tomorrow(), 1, 6, 45, "Feed puppy", false);

	@Test
	public void testGetHour() {
		assertTrue(e1.getHour() == 4);
	}

	@Test
	public void testGetMinute() {
		assertTrue(e1.getMinute() == 30);
	}

	@Test
	public void testGetTimeString() {
		assertTrue(e1.getTimeString().equals("4:30 AM"));
	}

	@Test
	public void testGetText() {
		assertTrue(e1.getText().equals("Let puppy out"));
	}

	@Test
	public void testGetContent() {
		assertTrue(!e1.getContent().equals(e2.getContent()));
	}

	@Test
	public void testIsRepeatable() {
		assertTrue(e2.isRepeatable());
		assertTrue(!e1.isRepeatable());
	}

	@Test
	public void testGetStartDate() {
		assertTrue(e2.getStartDate().equals(date));
		assertTrue(e1.getStartDate() == null);
	}

	@Test
	public void testGetEndDate() {
		assertTrue(e2.getEndDate().equals(date.tomorrow()));
		assertTrue(e1.getEndDate() == null);
	}

	@Test
	public void testGetPeriod() {
		assertTrue(e2.getPeriod() == 1);
		assertTrue(e1.getPeriod() == 0);
	}

	@Test
	public void testGetId() {
		assertTrue(!e2.getId().equals(e1.getId()));
	}

	@Test
	public void testGetRepeat() {
		assertTrue(e2.getRepeat() == 1);
		assertTrue(e1.getRepeat() == 0);
	}

	@Test
	public void testGetTime() {
		//System.out.println(e2.getTime().toString());
		assertTrue(!e2.getTime().equals(new CalendarDate().today().getDate()));
	}

	@Test
	public void testGetWorkingDays() {
		assertTrue(!e2.getWorkingDays());
	}

}
