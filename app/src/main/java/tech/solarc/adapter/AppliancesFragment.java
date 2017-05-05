package tech.solarc.adapter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shawnlin.numberpicker.NumberPicker;

import tech.solarc.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppliancesFragment extends Fragment {


    public AppliancesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_appliances, container, false);

        NumberPicker picker = (NumberPicker) rootView.findViewById(R.id.appliance_number_picker);
        int value = picker.getValue();

        return rootView;
    }

}
