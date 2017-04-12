package net.sf.memoranda;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import net.sf.memoranda.date.CalendarDate;

/**
 * @author Avery Bowen
 * 
 * Description: The back end for Defect management
 *
 */
public class Defect {
	
	private static Map<String, String> _defTypes;
	public static enum Phase{
		PLANNING, DESIGN, CODE, CODE_REVIEW, COMPILE, TEST, POSTMORTEM
	};
	private static Task _t = null;
	private static int _idNum;
	private static int _fixTime;
	private static int _fixRefID;
	private static String _description;
	private static CalendarDate _date;
	private static int _defType;
	private static Phase _injPhase;
	private static Phase _remPhase;
	
	public Defect(int id, Task t, CalendarDate d, int type, Phase iPh, Phase rPh, int fTime, int fRef, String desc){
		this._idNum = id;
		this._t = t;
		this._date = d;
		this._defType = type;
		this._injPhase = iPh;
		this._remPhase = rPh;
		this._fixTime = fTime;
		this._fixRefID = fRef;
		this._description = desc;
	}
	
	private static Map<String, String> createMap(){
		_defTypes.put("10", "Documentation");
		_defTypes.put("20", "Syntax");
		_defTypes.put("30", "Build, Package");
		_defTypes.put("40", "Assignment");
		_defTypes.put("50", "Interface");
		_defTypes.put("60", "Checking");
		_defTypes.put("70", "Data");
		_defTypes.put("80", "Function");
		_defTypes.put("90", "System");
		_defTypes.put("100", "Environment");
		return _defTypes;
	}
	
	public static List getValues(){
		createMap();
		List c = (List) _defTypes.values();
		return c;
	}
	
	public static Vector getPhases(){
		Vector v = new Vector();
		v.add(Phase.CODE);
		v.add(Phase.CODE_REVIEW);
		v.add(Phase.COMPILE);
		v.add(Phase.DESIGN);
		v.add(Phase.PLANNING);
		v.add(Phase.POSTMORTEM);
		v.add(Phase.TEST);
		
		return v;
	}
	
	/**
	 * @return the _t
	 */
	public Task get_t() {
		return _t;
	}

	/**
	 * @param _t the _t to set
	 */
	public void set_t(Task _t) {
		this._t = _t;
	}

	/**
	 * @return the _idNum
	 */
	public int get_idNum() {
		return _idNum;
	}

	/**
	 * @param _idNum the _idNum to set
	 */
	public void set_idNum(int _idNum) {
		this._idNum = _idNum;
	}

	/**
	 * @return the _fixTime
	 */
	public int get_fixTime() {
		return _fixTime;
	}

	/**
	 * @param _fixTime the _fixTime to set
	 */
	public void set_fixTime(int _fixTime) {
		this._fixTime = _fixTime;
	}

	/**
	 * @return the _fixRefID
	 */
	public int get_fixRefID() {
		return _fixRefID;
	}

	/**
	 * @param _fixRefID the _fixRefID to set
	 */
	public void set_fixRefID(int _fixRefID) {
		this._fixRefID = _fixRefID;
	}

	/**
	 * @return the _description
	 */
	public String get_description() {
		return _description;
	}

	/**
	 * @param _description the _description to set
	 */
	public void set_description(String _description) {
		this._description = _description;
	}

	/**
	 * @return the _date
	 */
	public CalendarDate get_date() {
		return _date;
	}

	/**
	 * @param _date the _date to set
	 */
	public void set_date(CalendarDate _date) {
		this._date = _date;
	}

	/**
	 * @return the _defType
	 */
	public int get_defType() {
		return _defType;
	}

	/**
	 * @param _defType the _defType to set
	 */
	public void set_defType(int _defType) {
		this._defType = _defType;
	}

	/**
	 * @return the _injPhase
	 */
	public Phase get_injPhase() {
		return _injPhase;
	}

	/**
	 * @param _injPhase the _injPhase to set
	 */
	public void set_injPhase(Phase _injPhase) {
		this._injPhase = _injPhase;
	}

	/**
	 * @return the _remPhase
	 */
	public Phase get_remPhase() {
		return _remPhase;
	}

	/**
	 * @param _remPhase the _remPhase to set
	 */
	public void set_remPhase(Phase _remPhase) {
		this._remPhase = _remPhase;
	}
	
	

}
