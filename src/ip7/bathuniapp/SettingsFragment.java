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

public class SettingsFragment extends Fragment {

    private Spinner facultySpinner;
    private Spinner departmentSpinner;
<<<<<<< HEAD
    // private Spinner courseSpinner;
=======
    private Spinner courseSpinner;
>>>>>>> FETCH_HEAD
    private Spinner busRouteSpinner;
    private EditText fullNameText;
    private EditText usernameText;

    @Override
<<<<<<< HEAD
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
=======
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
>>>>>>> FETCH_HEAD

        View view = inflater.inflate(R.layout.settings_view, container, false);

        // Grab the UI elements for use
        facultySpinner = (Spinner) view.findViewById(R.id.spinFaculty);
        departmentSpinner = (Spinner) view.findViewById(R.id.spinDepartment);
<<<<<<< HEAD
        // courseSpinner = (Spinner) view.findViewById(R.id.spinCourse);
=======
        courseSpinner = (Spinner) view.findViewById(R.id.spinCourse);
>>>>>>> FETCH_HEAD
        busRouteSpinner = (Spinner) view.findViewById(R.id.spinBusRoute);
        fullNameText = (EditText) view.findViewById(R.id.etFullName);
        usernameText = (EditText) view.findViewById(R.id.etUsername);

        // Use the above view to populate the spinner
        setSpinnerContent(view, facultySpinner, R.array.faculty_array);
        setSpinnerContent(view, busRouteSpinner, R.array.bus_route_array);

        // Restore preferences
        // TODO Restore faculty and bus route
        SharedPreferences settings = this.getActivity().getPreferences(0);
        fullNameText.setText(settings.getString("name", ""));
        usernameText.setText(settings.getString("username", ""));

        // Update Department spinner when Faculty spinner changes
        // TODO Include all courses
        // TODO Update Course on Department change
<<<<<<< HEAD
        facultySpinner
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                            View view, int pos, long id) {

                        // For testing
                        System.out.println(parent.getItemAtPosition(pos));

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
=======
        facultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int pos, long id) {

                // For testing
                System.out.println(parent.getItemAtPosition(pos));

                // Update department spinner from appropriate array
                if  (parent.getItemAtPosition(pos).equals("Engineering"))
                {
                    setSpinnerContent(view, departmentSpinner,
                                      R.array.course_engineering_array);
                }
                else if (parent.getItemAtPosition(pos).equals("Humanities and Social Sciences"))
                {
                    setSpinnerContent(view, departmentSpinner,
                                      R.array.course_humanities_array);
                }
                else if (parent.getItemAtPosition(pos).equals("Science"))
                {
                    setSpinnerContent(view, departmentSpinner,
                                      R.array.course_science_array);
                }
                else
                {
                    setSpinnerContent(view, departmentSpinner,
                                      R.array.empty_array);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Interface callback

            }

        });
>>>>>>> FETCH_HEAD

        return view;
    }

    // Populates a spinner with the contents of the given array
<<<<<<< HEAD
    private void setSpinnerContent(View view, Spinner spinner, int textArrayId) {

        // Create an ArrayAdapter using the string array and a default spinner
        // layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this.getActivity(), textArrayId,
                android.R.layout.simple_spinner_item);
=======
    private void setSpinnerContent(View view, Spinner spinner, int textArrayId)
    {

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                textArrayId, android.R.layout.simple_spinner_item);
>>>>>>> FETCH_HEAD

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    // TODO This needs fixing: May break from time to time in different tabs
<<<<<<< HEAD
    // @Override
    // public void onDestroyView()
    // {
    // Save preferences
    // TODO Save Bus Route and Course
    // SharedPreferences settings = this.getActivity().getPreferences(0);
    // SharedPreferences.Editor editor = settings.edit();
    // editor.putString("name", fullNameText.getText().toString());
    // editor.putString("username", usernameText.getText().toString());
    //
    // // Commit changes
    // editor.commit();
    // }
=======
  //  @Override
//    public void onDestroyView()
 //   {
        // Save preferences
        //TODO Save Bus Route and Course
//        SharedPreferences settings = this.getActivity().getPreferences(0);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putString("name", fullNameText.getText().toString());
//        editor.putString("username", usernameText.getText().toString());
//
//        // Commit changes
//        editor.commit();
 //   }
>>>>>>> FETCH_HEAD
}
