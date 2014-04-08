package ip7.bathuniapp;

import ip7.bathuniapp.R;

import ip7.bathuniapp.TouchImageView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.os.Bundle;

/*
 *  Maps Fragment simply displays a university map
 *  using TouchImageView, allowing the user to scroll
 *  and zoom around the map
 */
public class MapsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_maps, container, false);

        TouchImageView img = (TouchImageView) v.findViewById(R.id.img);
        img.setMaxZoom(4);

        return v;
    }
}
