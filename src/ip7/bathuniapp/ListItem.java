package ip7.bathuniapp;

import java.util.Date;


public class ListItem 

{

	private int priority = 0;
	private String text = "";
	private boolean done = false;
	private Date date; //String in format DD/MM/YY
	private String description;
	
	
	
	public ListItem()
	{
		
	}
	
	public ListItem(String t, int p, boolean x)
	{
		priority = p;
		text = t;
		done = x;
	}
	
	public void setDate(Date d)
	{
		date = d;
	}
	
	public Date getDate()
	{
		return date;
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
	
}
