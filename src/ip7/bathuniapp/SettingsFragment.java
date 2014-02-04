package ip7.bathuniapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingsFragment extends Fragment {
	
	private Spinner facultySpinner;
	
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
    	
    	
        View view = inflater.inflate(R.layout.settings_view, container, false);
        // Use the above view to populate the spinner
        setSpinnerContent(view);
        
        return view;
    }
    
    // Uses the given view to populate the Faculty Spinner
    private void setSpinnerContent(View view)
    {
    	facultySpinner = (Spinner) view.findViewById(R.id.spinFaculty);
    	// Create an ArrayAdapter using the string array and a default spinner layout
    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
    	        R.array.faculty_array, android.R.layout.simple_spinner_item);
    	// Specify the layout to use when the list of choices appears
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	// Apply the adapter to the spinner
    	facultySpinner.setAdapter(adapter);
    }
}
