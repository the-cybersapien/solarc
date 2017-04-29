package tech.solarc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import tech.solarc.R;


public class panelAdapter extends ArrayAdapter<panel> {


    public panelAdapter(@NonNull Context context, @NonNull ArrayList<panel> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_panel,parent,false);
        }

        panel currentPanel = getItem(position);

        TextView view = (TextView) listItemView.findViewById(R.id.item_panel);
        view.setText(currentPanel.getName());

        return listItemView;
    }
}
