package tech.solarc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import tech.solarc.R;

public class AppliancesAdapter extends RecyclerView.Adapter<AppliancesAdapter.ApplianceHolder>{

    private Context mContext;
    private ArrayList<Appliance> mList;

    public AppliancesAdapter(Context context , ArrayList<Appliance> data){
        mContext = context;
        mList = data;
    }

    @Override
    public ApplianceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ApplianceHolder(LayoutInflater.from(mContext).inflate(R.layout.appliances_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ApplianceHolder holder, int position) {
        Appliance currentPanel = mList.get(position);
        holder.mName.setText(currentPanel.getName());
        holder.mQuantity.setText(String.valueOf(currentPanel.getUsableQuantity()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ApplianceHolder extends RecyclerView.ViewHolder{

        public TextView mName;
        public TextView mQuantity;

        public ApplianceHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.appliance_name);
            mQuantity = (TextView) itemView.findViewById(R.id.appliance_quantity);
        }
    }

}
