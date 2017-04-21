package net.sf.memoranda;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import net.sf.memoranda.date.CalendarDate;

public class DefectTest {
	CalendarDate date = new CalendarDate().tomorrow();
	DefectList dl1 = new DefectList();
	DefectList dl2 = new DefectList();
	Defect d1a = dl1.createDefect();
	Defect d1b = dl1.createDefect(date, "def2", "20: Syntax", "DESIGN", "CODE_REVIEW", "15 min", "", "added missing statement");
	Defect d2a = dl2.createDefect();
	Defect d2b = dl2.createDefect(date, "def2", "30: Build, Package", "PLANNING", "CODE_REVIEW", "20 min", "", "added missing jar to build path");
	
	@Test
	public void testGetContent() {
		Vector v1 = dl1.getAllDefects();
		Defect testD = (Defect)v1.get(0);
		assertTrue(testD.getContent().equals(d1a.getContent()));
	}

	@Test
	public void testGetDate() {
		assertTrue(d1a.getDate().equals(new CalendarDate().today()));
	}

	@Test
	public void testGetDefectType() {
		assertTrue(d2b.getDefectType().equals("30: Build, Package"));
	}

	@Test
	public void testGetID() {
		assertTrue(d2a.getID().equals("def1"));
	}

	@Test
	public void testGetInjectedPhase() {
		assertTrue(d1b.getInjectedPhase().equals("DESIGN"));
	}

	@Test
	public void testGetRemovedPhase() {
		assertTrue(d1b.getRemovedPhase().equals("CODE_REVIEW"));
	}

	@Test
	public void testGetFixedTime() {
		assertTrue(d2b.getFixedTime().equals("20 min"));
	}

	@Test
	public void testGetFixRef() {
		assertTrue(d1a.getFixRef().equals("task 3"));
	}

	@Test
	public void testGetDesc() {
		assertTrue(d2a.getDesc().equals("recoded buttons"));
	}

}
