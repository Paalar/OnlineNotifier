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

import com.dwarfstar.gpd.onlinenotifier.JSON.Cantina.Cantina;
import com.dwarfstar.gpd.onlinenotifier.JSON.Cantina.CantinaJSONDecode;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CantinaActivity extends AppCompatActivity {

    private List<Cantina> mCantinaList;
    private RecyclerView mRecyclerView;
    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cantina);

        Bundle bundle = getIntent().getExtras();
        int navBarPos = bundle.getInt("position");

        loadJSONCantina();
        loadRecyclerView();

        mNavigationView = findViewById(R.id.bottom_nav_bar);
        mNavigationView.setSelectedItemId(navBarPos);

        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                final String MEETING = getString(R.string.meetings_menu), OFFICE = getString(R.string.office_menu);
                Intent intent = null;

                if (item.getTitle().equals(MEETING)) {
                    intent = new Intent(CantinaActivity.this, MeetingsActivity.class);
                    intent.putExtra("position", item.getItemId());
                } else if (item.getTitle().equals(OFFICE)) {
                    intent = new Intent(CantinaActivity.this, KontoretActivity.class);
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


    private void loadJSONCantina() {
        CantinaJSONDecode fetchData = new CantinaJSONDecode();
        try {
            fetchData.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        mCantinaList = fetchData.getCantina();
    }

    private void loadRecyclerView() {
        mRecyclerView = findViewById(R.id.cantina_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CantinaActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        CantinaAdapter cantinaAdapter = new CantinaAdapter(mCantinaList);
        mRecyclerView.setAdapter(cantinaAdapter);
    }
}
