package tech.solarc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import tech.solarc.R;

/**
 * Created by sagar on 30/4/17.
 */

public class ApplianceListAdapter extends ArrayAdapter<Appliance> {

    public ApplianceListAdapter(@NonNull Context context, @NonNull List<Appliance> objects) {
        super(context, R.layout.appliances_list_item, 0 , objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
        {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.appliances_list_item,parent,false);
        }

        Appliance currentAppliance = getItem(position);
        TextView name = (TextView) listItem.findViewById(R.id.appliance_name);
        TextView quantity = (TextView) listItem.findViewById(R.id.appliance_quantity);
        name.setText(currentAppliance.getName());
        quantity.setText((String.valueOf(currentAppliance.getUsableQuantity())));
        return listItem;
    }
}

