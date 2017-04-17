package net.sf.memoranda;

import static org.junit.Assert.*;

import org.junit.Test;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Element;

/**
 * @author Avery Bowen
 * Tester class for history item class
 *
 */
public class HistoryItemTest {
	CalendarDate d = new CalendarDate().tomorrow();
	HistoryItem hi = new HistoryItem(d);
	

	@Test
	public void testHistoryItem() {
		assertTrue(hi.getClass() == HistoryItem.class);
	}

	
	@Test
	public void testGetDate() {
		assertTrue(hi.getDate().equals(CalendarDate.tomorrow()));
	}

}
