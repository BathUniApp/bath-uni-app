package ip7.bathuniapp;

import ip7.bathuniapp.R;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.support.v4.app.*;
import java.util.*;
import android.view.*;
import android.widget.*;


public class MainActivity extends FragmentActivity
    implements ActionBar.TabListener {

    private NonSwipeableViewPager nsViewPager;
    private TabsPagerAdapter tabsAdapter;
    private ActionBar actionBar;
    private String[] tabs = {"Settings", "Buses", "Classes",
                             "To-Do list", "Maps" };

    // private ListView lv;
    private ToDoAdapter adapter;
    private ArrayList<ListItem> fetch = new ArrayList<ListItem>();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        nsViewPager = (NonSwipeableViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        tabsAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        nsViewPager.setAdapter(tabsAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // list items
        ListItem itOne = new ListItem("Item1", "Here goes item one!");
        ListItem itTwo = new ListItem("Item2", "And there is two!");
        fetch.add(itOne);
        fetch.add(itTwo);

        ListView lv = (ListView) findViewById(android.R.id.list);
        adapter = new ToDoAdapter(this,
                                  android.R.id.list,
                                  fetch);
        lv.setAdapter(adapter);

        //add the tabs
        for (String name : tabs) {
            actionBar.addTab(actionBar.newTab()
                       .setText(name)
                       .setTabListener(this));
        }
    }

    @Override
    public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        nsViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {

    }
}
