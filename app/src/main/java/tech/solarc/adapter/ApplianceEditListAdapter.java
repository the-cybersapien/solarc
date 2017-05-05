package tech.solarc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.shawnlin.numberpicker.NumberPicker;

import java.util.List;

import tech.solarc.R;

/**
 * Created by sagar on 30/4/17.
 * Appliance EditList Adapter to Add or Edit an Appliance
 */

public class ApplianceEditListAdapter extends ArrayAdapter<Appliance> {

    public ApplianceEditListAdapter(@NonNull Context context, @NonNull List<Appliance> objects) {
        super(context, R.layout.appliances_list_item, 0 , objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
        {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.edit_appliance_list_item,parent,false);
        }

        final Appliance currentAppliance = getItem(position);
        final TextView name = (TextView) listItem.findViewById(R.id.appliance_name);
        name.setText(currentAppliance.getName());
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setCursorVisible(true);
                name.setFocusableInTouchMode(true);
                name.setInputType(InputType.TYPE_CLASS_TEXT);
                name.requestFocus();
            }
        });

        int quantity = currentAppliance.getUsableQuantity();
        NumberPicker np = (NumberPicker) listItem.findViewById(R.id.appliance_number_picker);
        np.setValue(quantity);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                currentAppliance.setTotalQuantity(newVal);
            }
        });
        return listItem;
    }
}

