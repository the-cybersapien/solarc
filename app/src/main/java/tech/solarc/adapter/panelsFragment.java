package tech.solarc.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.paolorotolo.appintro.ISlidePolicy;

import java.util.ArrayList;

import tech.solarc.MainActivity;
import tech.solarc.R;


public class panelsFragment extends Fragment implements ISlidePolicy{

    private int flag = 0;

    private ListView panelList;
    private View rootView;

    public panelsFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
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

        panelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                flag = 1;
                ((MainActivity)getActivity()).b();
            }
        });

        return rootView;
    }

    @Override
    public boolean isPolicyRespected() {
        if(flag == 0)
            return false;
        else
            return true;
    }

    @Override
    public void onUserIllegallyRequestedNextPage() {

    }
}

