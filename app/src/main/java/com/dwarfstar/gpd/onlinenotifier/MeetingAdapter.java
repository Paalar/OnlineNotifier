package com.dwarfstar.gpd.onlinenotifier;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline.Meeting;

import java.util.List;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MeetingAdapterHolder> {

    List<Meeting> mMeetingList;
    Context mContext;

    public static class MeetingAdapterHolder extends RecyclerView.ViewHolder {

        TextView mSummary;
        TextView mTime;

        public MeetingAdapterHolder(View itemView) {
            super(itemView);
            mSummary = itemView.findViewById(R.id.meeting_summary);
            mTime = itemView.findViewById(R.id.meeting_time);
        }
    }

    public MeetingAdapter(List<Meeting> meetingList) {
        mMeetingList = meetingList;
    }

    @Override
    public MeetingAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_row, parent, false);
        mContext = parent.getContext();
        MeetingAdapterHolder meetingAdapterHolder = new MeetingAdapterHolder(view);
        return meetingAdapterHolder;
    }

    @Override
    public void onBindViewHolder(MeetingAdapterHolder holder, int position) {
        holder.mSummary.setText(mMeetingList.get(position).getSummary());
        String start = mMeetingList.get(position).getStartTime();
        String end = mMeetingList.get(position).getEndTime();
        holder.mTime.setText(mContext.getResources().getString(R.string.from_to, start, end));
    }

    @Override
    public int getItemCount() {
        return mMeetingList.size();
    }
}
