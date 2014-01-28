package ip7.bathuniapp;

import android.support.v4.app.*;

public class TabsPagerAdapter extends FragmentPagerAdapter {
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
        case 0:
            return new SettingsFragment();
        case 1:
            return new BusesFragment();
        case 2:
            return new ClassesFragment();
        case 3:
            return new ToDoListFragment();
        case 4:
            return new MapsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        // item count, equal to number of tabs
        return 5;
    }
}
