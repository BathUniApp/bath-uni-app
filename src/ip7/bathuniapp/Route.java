package ip7.bathuniapp;

import java.util.List;

public class Route {
	
	private String name;
	private List<Day> days;
	
	public Route(String routeName, List<Day> theDays)
	{
		name = routeName;
		days = theDays;
	}

}
