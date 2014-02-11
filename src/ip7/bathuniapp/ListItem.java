package ip7.bathuniapp;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class ListItem 

{

	private int ID;
	private int priority = 0;
	private String text = "";
	private boolean done = false;
	private String date; //String in format DD/MM/YY
	private String description;
	private int parent;
	
	
	
	public ListItem()
	{
		
	}
	
	public ListItem(String t, int p, boolean x, String d, String e, int id, int pid)
	{
		priority = p;
		text = t;
		done = x;
		date = d;
		description = e;
		ID = id;
		parent = pid;
	}
	
	public void setDate(String d)
	{
		date = d;
	}
	
	public String getDate()
	{
		return date;
	}
	
	public GregorianCalendar getDateClass()
	{
		String[] datesplit = date.split("/");
		int day = Integer.valueOf(datesplit[0]);
		int month = Integer.valueOf(datesplit[0]);
		int year = Integer.valueOf(datesplit[0]);
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.MONTH, month-1); //Calendar uses months with Jan being 0 and Dec being 11
		cal.set(Calendar.YEAR, year);
		return cal;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String d)
	{
		description = d;
	}
	
	public int getPriority()
	{
		return priority;
	}
	
	public String getText()
	{
		return text;
	}
	
	public boolean getDone()
	{
		return done;
	}
	
	public void setPriority(int p)
	{
		priority = p;
	}
	
	public void setText(String s)
	{
		text = s;
	}
	
	public void setDone(boolean x)
	{
		done = x;
	}
	
	public void setID(int i)
	{
		ID = i;
	}
	
	public int getID()
	{
		return ID;
	}
	
	public int getParentID()
	{
		return parent;
	}
	
	public void setParentID(int p)
	{
		parent = p;
	}
}
