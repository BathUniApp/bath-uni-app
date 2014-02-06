package ip7.bathuniapp;


public class ListItem 

{

	private int priority = 0;
	private String text = "";
	private boolean done = false;
	
	
	public ListItem()
	{
		
	}
	
	public ListItem(String t, int p, boolean x)
	{
		priority = p;
		text = t;
		done = x;
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
