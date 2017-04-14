package net.sf.memoranda;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import net.sf.memoranda.date.CalendarDate;


public class DefectListTest {
	CalendarDate date = new CalendarDate().tomorrow();
	DefectList dl1 = new DefectList();
	DefectList dl2 = new DefectList();
	Defect d1a = dl1.createDefect();
	Defect d1b = dl1.createDefect(date, "def2", "20: Syntax", "DESIGN", "CODE_REVIEW", "15 min", "", "added missing statement");
	Defect d2a = dl2.createDefect();
	Defect d2b = dl2.createDefect(date, "def2", "30: Build, Package", "PLANNING", "CODE_REVIEW", "20 min", "", "added missing jar to build path");

	@Test
	public void testGetAllDefects() {
		Vector v1 = dl1.getAllDefects();
		Defect testD = (Defect)v1.get(0);
		assertTrue(testD.getContent().equals(d1a.getContent()));
		testD = (Defect)v1.get(1);
		assertTrue(testD.getContent().equals(d1b.getContent()));
		v1 = dl2.getAllDefects();
		testD = (Defect)v1.get(0);
		assertTrue(testD.getContent().equals(d2a.getContent()));
		testD = (Defect)v1.get(1);
		assertTrue(testD.getContent().equals(d2b.getContent()));
		
	}

}
