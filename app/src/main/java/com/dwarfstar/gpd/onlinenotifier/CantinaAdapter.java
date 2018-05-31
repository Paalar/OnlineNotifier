package com.dwarfstar.gpd.onlinenotifier;


import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dwarfstar.gpd.onlinenotifier.JSON.Cantina.Cantina;

import java.util.List;

public class CantinaAdapter extends RecyclerView.Adapter<CantinaAdapter.CantinaAdapterHolder> {

    private List<Cantina> mCantinaList;

    public static class CantinaAdapterHolder extends RecyclerView.ViewHolder {

        TextView cantinaName;
        TextView cantinaMessage;

        public CantinaAdapterHolder(View itemView) {
            super(itemView);
            cantinaName = itemView.findViewById(R.id.cantina_location);
            cantinaMessage = itemView.findViewById(R.id.cantina_message);
        }
    }

    @Override
    public CantinaAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cantina_row, parent, false);
        CantinaAdapterHolder cantinaAdapterHolder = new CantinaAdapterHolder(view);
        return cantinaAdapterHolder;
    }

    @Override
    public void onBindViewHolder(CantinaAdapterHolder holder, int position) {
        Cantina cantina = mCantinaList.get(position);
        holder.cantinaMessage.setText(cantina.getHours().getMessage());
        holder.cantinaName.setText(cantina.getCantinaName());
    }

    @Override
    public int getItemCount() {
        return mCantinaList.size();
    }

    public CantinaAdapter(List<Cantina> cantinas) {
        mCantinaList = cantinas;
    }
}
