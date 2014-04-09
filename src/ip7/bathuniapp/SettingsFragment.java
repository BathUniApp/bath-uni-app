package ip7.bathuniapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


/*
 *  Code behind the settings tab
 *  Simply set up the spinboxes for use, and 
 *  store whatever information is input in the
 *  android device's settings.
 */
public class SettingsFragment extends Fragment {

    private Spinner facultySpinner;
    private Spinner departmentSpinner;
    private Spinner yearSpinner;
    private Spinner busRouteSpinner;
    private EditText fullNameText;
    private EditText usernameText;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.settings_view, container, false);

        // Grab the UI elements for use
        fullNameText = (EditText) view.findViewById(R.id.etFullName);
        usernameText = (EditText) view.findViewById(R.id.etUsername);
        facultySpinner = (Spinner) view.findViewById(R.id.spinFaculty);
        departmentSpinner = (Spinner) view.findViewById(R.id.spinDepartment);
        yearSpinner = (Spinner) view.findViewById(R.id.spinYear);
        busRouteSpinner = (Spinner) view.findViewById(R.id.spinBusRoute);

        // Use the above view to populate the spinner
        setSpinnerContent(view, facultySpinner, R.array.faculty_array);
        setSpinnerContent(view, yearSpinner, R.array.year_array);
        setSpinnerContent(view, busRouteSpinner, R.array.bus_route_array);

        // Restore preferences
        SharedPreferences settings = this.getActivity().getPreferences(0);
        fullNameText.setText(settings.getString("name", ""));
        usernameText.setText(settings.getString("username", ""));
        facultySpinner.setSelection(settings.getInt("faculty", 0));
        departmentSpinner.setSelection(settings.getInt("department", 0));
        yearSpinner.setSelection(settings.getInt("year", 0));
        busRouteSpinner.setSelection(settings.getInt("bus", 0));

        // Update Department spinner when Faculty spinner changes
        facultySpinner
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                            View view, int pos, long id) {

                        // Update department spinner from appropriate array
                        if (parent.getItemAtPosition(pos).equals("Engineering")) {
                            setSpinnerContent(view, departmentSpinner,
                                    R.array.course_engineering_array);
                        } else if (parent.getItemAtPosition(pos).equals(
                                "Humanities and Social Sciences")) {
                            setSpinnerContent(view, departmentSpinner,
                                    R.array.course_humanities_array);
                        } else if (parent.getItemAtPosition(pos).equals(
                                "Science")) {
                            setSpinnerContent(view, departmentSpinner,
                                    R.array.course_science_array);
                        } else {
                            setSpinnerContent(view, departmentSpinner,
                                    R.array.empty_array);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Interface callback
                    }
                });

        return view;
    }
    
    // Store the information when the tab is stopped
    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences settings = this.getActivity().getPreferences(0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("name", fullNameText.getText().toString());
        editor.putString("username", usernameText.getText().toString());
        editor.putInt("faculty", facultySpinner.getSelectedItemPosition());
        editor.putInt("department", departmentSpinner.getSelectedItemPosition());
        editor.putInt("year", yearSpinner.getSelectedItemPosition());
        editor.putInt("bus", busRouteSpinner.getSelectedItemPosition());
        editor.putString("departmentName", departmentSpinner.getSelectedItem().toString());
        editor.putString("yearName", yearSpinner.getSelectedItem().toString());
        
        //Commit edits
        editor.commit();  
    }

    // Populates a spinner with the contents of the given array
    private void setSpinnerContent(View view, Spinner spinner, int textArrayId) {

        // Create an ArrayAdapter using the string array and a default spinner
        // layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this.getActivity(), textArrayId,
                android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }
}
