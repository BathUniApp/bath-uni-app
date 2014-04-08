package ip7.bathuniapp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Gravity;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.*;

public class LecturesFragment extends Fragment {
    
    String[] timesArray = {"08:15", "09:15", "10:15", "11:15", "12:15",
            "13:15", "14:15", "15:15", "16:15", "17:15", "18:15"};
         
    String[] datesArray = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    
    String[][] events = new String[11][5];

    @SuppressLint("SimpleDateFormat")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lectures_week, container, false);

        // table with the lecture times column (rows, technically)
        TableLayout lectureTable = (TableLayout) v.findViewById(R.id.lecture_times);
        // table with the lectures for the given time and the week days
        TableLayout lectures     = (TableLayout) v.findViewById(R.id.lectures);
        
        
        try {
            InputStream inputStream = getResources().openRawResource(
                    getResources().getIdentifier("raw/test",
                    "raw", getActivity().getPackageName()));
            
            
             
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(inputStreamReader);
                
                
                String line;
                Boolean inEvent = false;
                for (int i = 0; i < timesArray.length; i++) {
                    String[] day = {"", "", "", "", ""};
                    events[i] = day;
                }
                
                String location = "";
                String course = "";
                String desc = "";
                int startDay = 0;
                int startTime = 0;
                    
                
                
                while ((line = br.readLine()) != null) {
                    String splitString[] = line.split(":");
                    if ((splitString.length > 1) && (splitString[0].equals("LOCATION"))) {
                        location = splitString[1];
                    }
                    else if ((inEvent) && (splitString.length > 1) && (splitString[0].equals("DTSTART"))) {
                        String times[] = splitString[1].split("T");
                        Calendar c = Calendar.getInstance();
                        c.setTime(new SimpleDateFormat("yyyyMMdd").parse(times[0]));
                        startDay = (c.get(Calendar.DAY_OF_WEEK) - 2);
                        startTime = (Integer.parseInt(times[1]) / 10000) - 8;
                    }
                    else if ((inEvent) && (splitString.length > 1) && (splitString[0].equals("SUMMARY"))) {
                        String summary[] = splitString[1].split(" ");
                        course = summary[0];
                        StringBuilder builder = new StringBuilder();
                        for(int i = 3; i < summary.length; i++) {
                            builder.append(summary[i]);
                            builder.append(" ");
                        }
                        desc = builder.toString();
                        if(desc.length() > 18) {
                            desc = desc.substring(0, 17);
                        }
                    }
                    else if (line.equals("BEGIN:VEVENT")) {
                        inEvent = true;
                        location = "";
                        course = "";
                        desc = "";
                        startDay = 0;
                        startTime = 0;
                    } 
                    else if (line.equals("END:VEVENT")) {
                        inEvent = false;
                        events[startTime][startDay] = location + "\n" + course + "\n" + desc;
                    }
                }
                
                
                
                
                br.close();
                
                for(int i = 0; i < events.length; i++) {
                    System.out.println(timesArray[i]);
                    for (int j = 0; j < events[i].length; j++) {
                        System.out.println(datesArray[j]);
                        System.out.println(events[i][j]);
                    }
                }

                
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        fillTimeTable(lectureTable, lectures);

        return v;
    }

    public void fillTimeTable(TableLayout lecTimeTable, TableLayout lecs) {

        // this is just a placeholder to test the table. Should
        // obviously be replaced by whatever actually holds the lecture
        // information.
//        String[] placeholder = {"lecturelecture \n room \n weeks", "-tue-",
//                                "-wed-", "-thu-", "-fri-", "SAT", "SUN"};
//        String[] daysArray = {"Monday", "Tuesday", "Wednesday", "Thursday",
//                              "Friday", "Saturday", "Sunday"};
//        String[] timesArray = {"08:15", "09:15", "10:15", "11:15", "12:15",
//                               "13:15", "14:15", "15:15", "16:15", "17:15", "18:15"};

        int colourCounter = 0; // if even, colour row blue

        fillRow(lecTimeTable, lecs, " ", datesArray, 1); // first row contains day names
        
        
        for(int i = 0; i < events.length; i++) {
            fillRow(lecTimeTable, lecs, timesArray[i], events[i], colourCounter);
            colourCounter++;
        }

        // fill the row with placeholders for lectures. The table can
        // comfortably hold three lines of information (lecture code,
        // room code, weeks running)
//        for (String time : timesArray) {
//            fillRow(lecTimeTable, lecs, time, placeholder, colourCounter);
//            colourCounter++;
//        }
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
