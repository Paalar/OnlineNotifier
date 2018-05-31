package com.dwarfstar.gpd.onlinenotifier;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline.Meetings;
import com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline.MeetingsJSONDecode;

import java.util.concurrent.ExecutionException;


public class MeetingsActivity extends AppCompatActivity {

    BottomNavigationView mNavigationView;
    RecyclerView mRecyclerView;
    TextView mOfficeFreeTextview;
    int navBarPos;

    Meetings mMeetings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);

        Bundle bundle = getIntent().getExtras();
        navBarPos = bundle.getInt("position");

        mOfficeFreeTextview = findViewById(R.id.office_free);
        mNavigationView = findViewById(R.id.bottom_nav_bar);
        mNavigationView.setSelectedItemId(navBarPos);

        loadJSONMeeting();
        checkMeetings();

        mOfficeFreeTextview.setText(mMeetings.getMessage());
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                final String OFFICE = getString(R.string.office_menu), CANTINA = getString(R.string.cantina_menu);
                Intent intent = null;

                if (item.getTitle().equals(OFFICE)) {
                    intent = new Intent(MeetingsActivity.this, KontoretActivity.class);
                } else if (item.getTitle().equals(CANTINA)) {
                    intent = new Intent(MeetingsActivity.this, CantinaActivity.class);
                    intent.putExtra("position", item.getItemId());
                }
                if (intent != null) {
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });

    }


    private void checkMeetings() {
        if (!mMeetings.getMeetings().isEmpty()) {
            loadRecyclerView();
        }
    }

    private void loadRecyclerView() {
        mRecyclerView = findViewById(R.id.meeting_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MeetingsActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);

        MeetingAdapter meetingAdapter = new MeetingAdapter(mMeetings.getMeetings());
        mRecyclerView.setAdapter(meetingAdapter);
    }

    private void loadJSONMeeting() {
        MeetingsJSONDecode fetchdata = new MeetingsJSONDecode();

        try {
            Void result = fetchdata.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        mMeetings = fetchdata.getMeetings();
    }
}
