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

	
	public ArrayList<ListItem> getDateGroup(String mode, String date) //mode being day or month, date input being DD/MM/YYYY
	{
		ArrayList<ListItem> filtered = new ArrayList<ListItem>();
		String[] datesplit = date.split("/");
		int day = Integer.valueOf(datesplit[0]);
		int month = Integer.valueOf(datesplit[0]);
		int year = Integer.valueOf(datesplit[0]);
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.MONTH, month-1); //Calendar uses months with Jan being 0 and Dec being 11
		cal.set(Calendar.YEAR, year);
		
		for(int i=0; i<todolist.size(); i++)
		{
			ListItem temp = todolist.get(i);
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
    private void setXML()
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
                    System.out.println("Description : " + eElement.getElementsByTagName("description").item(0).getTextContent());
                    System.out.println("Parent ID : " + eElement.getElementsByTagName("parentid").item(0).getTextContent());
                    System.out.println("Complete : " + eElement.getElementsByTagName("complete").item(0).getTextContent());
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
    
    
}
