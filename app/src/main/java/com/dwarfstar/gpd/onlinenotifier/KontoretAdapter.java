package com.dwarfstar.gpd.onlinenotifier;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline.ServantPerson;

import java.util.List;

public class KontoretAdapter extends RecyclerView.Adapter<KontoretAdapter.ServantViewHolder> {

    List<ServantPerson> mServantPersonList;
    Context mContext;

    public static class ServantViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView servantName;

        ServantViewHolder(View view) {
            super(view);
            time = view.findViewById(R.id.time_textview);
            servantName = view.findViewById(R.id.servant_name_textview);
        }

    }

    @Override
    public ServantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.servant_row, parent, false);
        mContext = parent.getContext();
        ServantViewHolder servantViewHolder = new ServantViewHolder(view);
        return servantViewHolder;
    }

    @Override
    public void onBindViewHolder(ServantViewHolder holder, int position) {
        holder.servantName.setText(mServantPersonList.get(position).getName());
        String start = mServantPersonList.get(position).getStart().getTime();
        String end = mServantPersonList.get(position).getEnd().getTime();
        holder.time.setText(mContext.getResources().getString(R.string.from_to, start, end));
    }

    @Override
    public int getItemCount() {
        return mServantPersonList.size();
    }

    KontoretAdapter(List<ServantPerson> servantPersonList) {
        mServantPersonList = servantPersonList;
    }
}
