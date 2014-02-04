package ip7.bathuniapp;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

public class MapsFragment extends Fragment {
	
	private SupportMapFragment fragment;
	private GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.frag_maps, container, false);
    }
    
//    @Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//		FragmentManager fm = getChildFragmentManager();
//		fragment = (SupportMapFragment) fm.findFragmentById(R.id.map_container);
//		if (fragment == null) {
//			fragment = SupportMapFragment.newInstance();
//			fm.beginTransaction().replace(R.id.map_container, fragment).commit();
//		}
//	}
//	
//	@Override
//	public void onResume() {
//		super.onResume();
//		if (map == null) {
//			map = fragment.getMap();
//			map.addMarker(new MarkerOptions().position(new LatLng(0, 0)));
//		}
//	}
}
