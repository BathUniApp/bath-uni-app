package ip7.bathuniapp;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.URI;
import java.util.GregorianCalendar;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Encoding;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.Attach;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Gravity;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.*;

public class LecturesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lectures_week, container, false);
        
        AssetManager assManager = getActivity().getApplicationContext().getAssets();
        InputStream in = null;
        try {
            in = assManager.open("test.ics");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = null;
        try {
            calendar = builder.build(in);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }
        if (calendar != null) {
            System.out.println("value :" + calendar);
            
            for (Object event : calendar.getComponents(Component.VEVENT)) {
                if (((VEvent) event).getSummary() != null) {
                    System.out.println(((VEvent) event).getSummary().getValue());
                    System.out.println(((VEvent) event).getStartDate().getDate());
                
                }
            }
        }
        
        
        
        
        
        
        
        
        

        // table with the lecture times column (rows, technically)
        TableLayout lectureTable = (TableLayout) v.findViewById(R.id.lecture_times);
        // table with the lectures for the given time and the week days
        TableLayout lectures     = (TableLayout) v.findViewById(R.id.lectures);

        fillTimeTable(lectureTable, lectures);

        return v;
    }

    public void fillTimeTable(TableLayout lecTimeTable, TableLayout lecs) {

        // this is just a placeholder to test the table. Should
        // obviously be replaced by whatever actually holds the lectre
        // inf++ormation.
        String[] placeholder = {"lecturelecture \n room \n weeks", "-tue-",
                                "-wed-", "-thu-", "-fri-", "SAT", "SUN"};
        String[] daysArray = {"Monday", "Tuesday", "Wednesday", "Thursday",
                              "Friday", "Saturday", "Sunday"};
        String[] timesArray = {"08:15", "09:15", "10:15", "11:15", "12:15",
                               "13:15", "14:15", "15:15", "16:15", "17:15"};

        int colourCounter = 0;// if even, colour row blue

        fillRow(lecTimeTable, lecs, " ", daysArray, 1); // first row contains day names

        // fill the row with placeholders for lectures. The table can
        // comfortably hold three lines of information (lecure code,
        // room code, weeks running)
        for (String time : timesArray) {
            fillRow(lecTimeTable, lecs, time, placeholder, colourCounter);
            colourCounter++;
        }
    }

    public void fillRow(TableLayout lecTimeTable, TableLayout lecs,
                        String lectureTime, String[] lectures, int rowCounter) {
        // lecTimeTable is the outmost table, holding the lectures and
        // the times column lecs is the table inside it, holding the
        // lectures themselves

        // row in the outside table, containing the time (first column)
        TableRow timeRow = new TableRow(this.getActivity());
        TableRow.LayoutParams timeParams =
            new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                      TableRow.LayoutParams.WRAP_CONTENT,
                                      1.0f);
        timeRow.setLayoutParams(timeParams);

        // row of lectures for the specified time in the inside table
        TableRow lectureRow = new TableRow(this.getActivity());
        TableRow.LayoutParams lectureParams =
            new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                      TableRow.LayoutParams.WRAP_CONTENT,
                                      1.0f);
        lectureRow.setLayoutParams(lectureParams);

        // if even, colour dark blue
        if (rowCounter % 2 == 0) {
            timeRow.setBackgroundResource(R.color.list_background);
            lectureRow.setBackgroundResource(R.color.list_background);
        }

        // add the lecture times
        TextView time = new TextView(this.getActivity());
        setTextViewOptions(time, 3, Gravity.CENTER, 10, 5, 10, 5, lectureTime);
        timeRow.addView(time); // add the textview to the row
        lecTimeTable.addView(timeRow); // add the row to the table

        // add the lectures to the row
        for (String l : lectures) {
            TextView lecture = new TextView(getActivity());
            setTextViewOptions(lecture, 3, Gravity.CENTER_HORIZONTAL,
                               10, 5, 10, 5, l);
             lectureRow.addView(lecture);
        }
        lecs.addView(lectureRow);

    }

    public void setTextViewOptions(TextView textView, int minLines, int gravity,
                                   int paddingLeft, int paddingTop,
                                   int paddingRight, int paddingBottom,
                                   String text) {
        textView.setMinLines(minLines);
        textView.setGravity(gravity);
        textView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        textView.setText(text);
    }
}
