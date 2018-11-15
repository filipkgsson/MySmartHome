package com.example.mysmarthome;

import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class    PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final int WIFI_SECTION_NUMBER = 1;

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView;
        if(getArguments().getInt(ARG_SECTION_NUMBER) == WIFI_SECTION_NUMBER){
            rootView = inflater.inflate(R.layout.fragment_wifi, container, false);
            Switch wifiSwitch = (Switch) rootView.findViewById(R.id.wifiSwitch);
            final WifiScanner wifiScanner = new WifiScanner(getActivity());
            wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ListView listView = (ListView)rootView.findViewById(R.id.wifiList);
                    wifiScanner.scan(listView);
                    //ArrayAdapter<ScanResult> adapter = new ArrayAdapter<ScanResult>(getActivity(), android.R.layout.simple_list_item_1, wifiScanner.scan());
                    //listView.setAdapter(adapter);
                    //((MainActivity)getActivity()).scanAvailableWifi(listView, isChecked);
                }
            });
        }
        else {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        }
        return rootView;
    }
}
