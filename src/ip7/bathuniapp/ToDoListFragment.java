package ip7.bathuniapp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ToDoListFragment extends Fragment {

	// The name of the file to store the to-dos XML in
	String FILENAME = "todos";
	ArrayList<ListItem> todolist = new ArrayList<ListItem>();


	public void sortByDate() //should sort the todolist by date, is currently not called
	/* Currently this uses bubblesort but upon reflection using insertion sort should be hugely more effiecent
	 * because you should only be inserting one object in at a time normally and thus sorting the new
	 * item straight in would be far more productive, but for now bubble sort is simple
	 * and quicksort etc is annoying because of the way java works, it isn't really a recurisve language
	 */
	{
		for(int j=0; j<todolist.size()-1; j++)
		{
			for(int i=0; i<todolist.size()-1; i++)
			{
				GregorianCalendar date1 = getCalendarVariable(todolist.get(i).getDate());
				GregorianCalendar date2= getCalendarVariable(todolist.get(i+1).getDate());
				if(date1.after(date2))
				{
					ListItem temp = todolist.get(i);
					todolist.set(i, todolist.get(i+1));
					todolist.set(i+1, temp); //swap the two variables
				}
			}
		}
	}

	public void addNewListItem(String t, int p, boolean x, String d, String e)
	{
		todolist.add(new ListItem(t, p, x, d, e, findFreeID(), 0)); //use other function for adding a child
	}

	public void addNewChild(String t, int p, boolean x, String d, String e, int pid) //Add child with given parent
	{
		todolist.add(new ListItem(t, p, x, d, e, findFreeID(), pid)); //ignore parentID for the time being
	}


	public int findFreeID() //Finds the first free ID for your new list item
	{
		int d = 1;
		while(true)
		{
			for(int i=0; i<todolist.size(); i++)
			{
				if(todolist.get(i).getID()==d)
				{
					d++;
					break;
				}
			}
			return d;
		}
	}

	public void removeListItem(int i)  //removes the item with the given ID
	{
		if(!hasChildren(i))
		{
			int x = todolist.size();
			for(int j=0; j<x; j++)
			{
				if(todolist.get(j).getID()==i)
				{
					todolist.remove(j);
					break;
				}
			}
			if(x==todolist.size())
			{
				System.out.println("Failed to remove item"); //shouldn't happen but just in case
			}
		}
		else
		{
			System.out.println("Cannot remove thread with children"); //For now at least, later will implement removal of children too
			//Also inform the user
		}

	}

	public boolean hasChildren(int pp)
	{
		for(int i=0; i<todolist.size(); i++)
		{
			if(todolist.get(i).getParentID()==pp)
			{
				return true;
			}
		}
		return false;
	}

	public void removeChildren(int pp) //Contary to current above comments, this will remove all the children with a given parentID pp
	{
		for(int i=0; i<todolist.size(); i++)
		{
			if(todolist.get(i).getParentID()==pp)
			{
				todolist.remove(i);
			}
		}
	}

	public GregorianCalendar getCalendarVariable(String date)
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

	public ArrayList<ListItem> getDateGroup(String mode, String date) //mode being day or month, date input being DD/MM/YYYY
	{
		ArrayList<ListItem> filtered = new ArrayList<ListItem>();
		String[] datesplit = date.split("/");
		int day = Integer.valueOf(datesplit[0]);
		int month = Integer.valueOf(datesplit[0]);
		int year = Integer.valueOf(datesplit[0]);
		GregorianCalendar cal = getCalendarVariable(date);

		for(int i=0; i<todolist.size(); i++)
		{
			ListItem temp = todolist.get(i);
			if(temp.getParentID()!=0) //You will obtain anything with a parent via another method
			{
				continue;
			}

			GregorianCalendar tempdate = temp.getDateClass();

			if(mode.equals("Day"))
			{
				if(day==tempdate.get(Calendar.DAY_OF_MONTH)&&
						year==tempdate.get(Calendar.YEAR)&&
						month==tempdate.get(Calendar.MONTH))
				{
					filtered.add(temp);
				}
			}
			if(mode.equals("Month"))
			{
				if(year==tempdate.get(Calendar.YEAR)&&
						month==tempdate.get(Calendar.MONTH))
				{
					filtered.add(temp);
				}
			}

			if(mode.equals("Week"))
			{
				if(cal.get(Calendar.WEEK_OF_YEAR)==tempdate.get(Calendar.WEEK_OF_YEAR)
						&&year==tempdate.get(Calendar.YEAR))
				{
					filtered.add(temp);
				}
			}

		}



		return filtered;
	}

	public ArrayList<ListItem> getSubTasks(int pid)
	{
		ArrayList<ListItem> filtered = new ArrayList<ListItem>();
		for(int i=0; i<todolist.size(); i++)
		{
			if(todolist.get(i).getParentID()==pid)
			{
				filtered.add(todolist.get(i));
			}
		}






		return filtered;
	}




	// Code to run on startup

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Test setting and getting the XML
        setXML();
        getXML();

        return  inflater.inflate(R.layout.frag_todolist, container, false);
    }

    // Sets some XML
    // This will need to be changed, it just demonstrates the ways of
    // Adding elements and attributes.
    private void setXML() //Acknowledged this needs to be changed but don't really understand it much, will ask
    //Tristan who wrote it Monday/Tuesday. Gathering this requires a for loop through my arraylist
    {
        try {
            // Create a document builder
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // Root elements. Note XML requires a single unique root element
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("to-dos");
            doc.appendChild(rootElement);

            // First To-do element
            Element todo = doc.createElement("todo");
            rootElement.appendChild(todo);

            // To make sure to-dos are unique, add an ID
            todo.setAttribute("id", "1");

            // Title element
            Element title = doc.createElement("title");
            title.appendChild(doc.createTextNode("Complete Homework"));
            todo.appendChild(title);

            // Description element
            Element description = doc.createElement("description");
            description.appendChild(doc.createTextNode("Finish that silly android application"));
            todo.appendChild(description);

            // Parent element
            Element parent = doc.createElement("parentid");
            parent.appendChild(doc.createTextNode("12"));
            todo.appendChild(parent);

            // Complete element
            Element complete = doc.createElement("complete");
            complete.appendChild(doc.createTextNode("false"));
            todo.appendChild(complete);

            // Write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(getActivity().openFileOutput(FILENAME , Context.MODE_PRIVATE));
            transformer.transform(source, result);

            // Note: This will print to LogCat, not the eclipse console
            System.out.println("To-Do XML saved");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (TransformerConfigurationException tce) {
            tce.printStackTrace();
        } catch (TransformerException te) {
            te.printStackTrace();
        }
    }

    // Restores some saved XML
    // Again, this needs to be changed, it's just to demonstrate.
    private void getXML()
    {
        try
        {
            // Get the saved XML file
            // Obviously this will call an error if there is no such file
            FileInputStream fileis = getActivity().openFileInput(FILENAME);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fileis);

            // Make sure the file is interpreted correctly.
            doc.getDocumentElement().normalize();

            // To demonstrate accessing elements
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            // Get all the to-dos into a list
            NodeList nList = doc.getElementsByTagName("todo");

            System.out.println("----------------------------");

            // Print all the information about the saved todos
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);
                System.out.println("\nCurrent To-do : " + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    System.out.println("To-Do ID : " + eElement.getAttribute("id"));
                    System.out.println("Title : " + eElement.getElementsByTagName("title").item(0).getTextContent());
                    System.out.println("Priority : " + eElement.getElementsByTagName("priority").item(0).getTextContent());
                    System.out.println("Description : " + eElement.getElementsByTagName("description").item(0).getTextContent());
                    System.out.println("Parent ID : " + eElement.getElementsByTagName("parentid").item(0).getTextContent());
                    System.out.println("Complete : " + eElement.getElementsByTagName("complete").item(0).getTextContent());
                    System.out.println("Date : " + eElement.getElementsByTagName("date").item(0).getTextContent());

                    //Make a new list item and store it in the arraylist
                    ListItem l = new ListItem(
                    		eElement.getElementsByTagName("title").item(0).getTextContent(),
                    		Integer.parseInt(eElement.getElementsByTagName("priority").item(0).getTextContent()),
                    		Boolean.parseBoolean(eElement.getElementsByTagName("complete").item(0).getTextContent()),
                    		eElement.getElementsByTagName("date").item(0).getTextContent(),
                    		eElement.getElementsByTagName("description").item(0).getTextContent(),
                    		Integer.parseInt(eElement.getAttribute("id")),
                    		Integer.parseInt(eElement.getElementsByTagName("parentid").item(0).getTextContent())
                    		);
                    todolist.add(l); //I assume this XML code, though I'm not completely sure, will be called on startup,
                    				//So this line and ones above will fill the list

                }
            }

        } catch (java.io.FileNotFoundException fnfe) {
            System.err.println("File not found exception");
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            System.err.println("IOException");
            ioe.printStackTrace();
        } catch (ParserConfigurationException pce) {
            System.err.println("ParserConfiguratioException");
            pce.printStackTrace();
        } catch (SAXException saxe) {
            System.err.println("SAXException");
            saxe.printStackTrace();
        }
    }



    public void changeView(View view)
    {
        // Do something in response to button
    }

    public void textHasChanged()
    {
        //implement something maybe onTextChanged()
    }

    public void openAddTask() {
    }

}
