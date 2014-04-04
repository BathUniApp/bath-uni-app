package ip7.bathuniapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.widget.*;

public class LecturesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.classes_day, container, false);

        // table with the lecture times column (rows, technically)
        TableLayout lectureTable = (TableLayout) v.findViewById(R.id.lecture_table);

        // table with the lectures for the given time and the week days
        TableLayout lectures     = (TableLayout) v.findViewById(R.id.lectures);

        fillTimeTable(lectureTable, lectures);

        return v;
    }

    public void fillTimeTable(TableLayout lecTimeTable, TableLayout lecs) {

        // this is just a placeholder to test the table. Should obviously be replaced
        // by whatever actually holds the lectre information,
        String[] placeholder = {"-mon-", "-tue-", "-wed-", "-thu-", "-fri-", "SAT", "SUN"};

        String[] daysArray = {"Monday", "Tuesday", "Wednesday", "Thursday",
                              "Friday", "Saturday", "Sunday"};
        String[] timesArray = {"08:15", "09:15", "10:15", "11:15", "12:15",
                               "13:15", "14:15", "15:15", "16:15", "17:15"};
        // if even, colour row blue
        int colourCounter = 0;

        // fill the first row with the day names
        fillRow(lecTimeTable, lecs, " ", daysArray, 1);

        // fill the row with placeholders for lectures
        // this will clearly depend on what the actual lecture info comes in.
        // Ideally, that would be a string or two with the lecture and room codes.
        for (String time : timesArray) {
            fillRow(lecTimeTable, lecs, time, placeholder, colourCounter);
            colourCounter++;
        }
    }

    public void fillRow(TableLayout lecTimeTable, TableLayout lecs,
                        String lectureTime, String[] lectures, int rowCounter) {
        // lecTimeTable is the outmost table, holding the lectures and the times column
        // lecs is the table inside it, holding the lectures themselves

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

        TextView time = new TextView(this.getActivity());
        time.setText(lectureTime);
        time.setPadding(10, 5, 10, 5);
        timeRow.addView(time);

        if (rowCounter % 2 == 0) {
            timeRow.setBackgroundResource(R.color.list_background);
            lectureRow.setBackgroundResource(R.color.list_background);
        }

        lecTimeTable.addView(timeRow);


        // add the lectures to the row
        for (String d : lectures) {
            TextView day = new TextView(getActivity());
            day.setText(d);
            day.setPadding(10, 5, 10, 5);
            lectureRow.addView(day);
        }
        lecs.addView(lectureRow);

    }
}
