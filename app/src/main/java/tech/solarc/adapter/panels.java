package tech.solarc.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import tech.solarc.R;


public class panels extends Fragment {

    private ListView panelList;
    private View rootView;

    public panels() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_panels, container, false);

        ArrayList<panel> list = new ArrayList<>();
        list.add(new panel("Panel 1"));
        list.add(new panel("Panel 1"));
        list.add(new panel("Panel 1"));
        list.add(new panel("Panel 1"));
        list.add(new panel("Panel 1"));

        panelAdapter myadapter = new panelAdapter(rootView.getContext(),list);
        panelList = (ListView) rootView.findViewById(R.id.panel_list);
        panelList.setAdapter(myadapter);

        return rootView;
    }

}

