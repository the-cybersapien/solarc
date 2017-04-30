package tech.solarc.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import tech.solarc.R;

/**
 * Created by sagar on 30/4/17.
 */

public class EditListAdapter extends ArrayAdapter<EditApplication> {

    public EditListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<EditApplication> objects) {
        super(context, resource, 0 , objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
        {
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.edit_list_item,parent,false);
        }

        EditApplication currentApplication = getItem(position);
        EditText edit = (EditText) listItem.findViewById(R.id.edit_list_quantity);
        Button plus = (Button) listItem.findViewById(R.id.edit_list_plus_button);
        Button minus = (Button) listItem.findViewById(R.id.edit_list_minus_button);
        TextView name = (TextView) listItem.findViewById(R.id.edit_list_name);


        return listItem;
    }
}

