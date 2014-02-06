package ip7.bathuniapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.support.v4.app.Fragment;
import android.os.Bundle;

public class ToDoListFragment extends Fragment {

	private EditText item1;
	private EditText item1Des;
	private EditText item1date;
	private CheckBox item1check;
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	return  inflater.inflate(R.layout.frag_todolist, container, false);
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
