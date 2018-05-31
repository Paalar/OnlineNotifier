package com.dwarfstar.gpd.onlinenotifier;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dwarfstar.gpd.onlinenotifier.JSON.Cantina.Cantina;
import com.dwarfstar.gpd.onlinenotifier.JSON.Cantina.CantinaJSONDecode;
import com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline.Coffee;
import com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline.Meetings;
import com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline.NotifierJSONDecode;
import com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline.Office;
import com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline.Servant;
import com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline.ServantPerson;
import com.dwarfstar.gpd.onlinenotifier.JSON.OfficeOnline.Status;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class KontoretActivity extends AppCompatActivity {

    private TextView mCoffeeAmount, mCoffeeTime;
    private Office mOffice;
    private Coffee mCoffee;
    private Servant mServant;
    private Status mStatus;
    private Meetings mMeetings;
    private RecyclerView mRecyclerView;
    private BottomNavigationView mNavigationView;
    public static List<Cantina> mCantinaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontoret);

        mCoffeeTime = findViewById(R.id.coffe_last_made);
        mCoffeeAmount = findViewById(R.id.coffe_pots);
        mNavigationView = findViewById(R.id.bottom_nav_bar);

        loadJSONOffice();
        //checkResponsibility();
        //loadJSONCantina();

        try {
            mCoffeeTime.setText(getResources().getString(R.string.coffee_last_made, mCoffee.getTime()));
            mCoffeeAmount.setText(getResources().getString(R.string.coffee_pots_text, mCoffee.getPots()));
        } catch (Exception e) {
            mCoffeeAmount.setText("The API Might be down, sorry for the inconvenience");
        }
        //FragmentManager fragmentManager = getSupportFragmentManager();
        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        /*mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                final String MEETING = getString(R.string.meetings_menu), CANTINA = getString(R.string.cantina_menu);
                Intent intent = null;

                if (item.getTitle().equals(MEETING)) {
                    intent = new Intent(KontoretActivity.this, MeetingsActivity.class);
                    intent.putExtra("position", item.getItemId());
                } else if (item.getTitle().equals(CANTINA)) {
                    intent = new Intent(KontoretActivity.this, CantinaActivity.class);
                    intent.putExtra("position", item.getItemId());
                }
                if (intent != null) {
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });*/

    }

    private void loadJSONCantina() {
        CantinaJSONDecode fetchData = new CantinaJSONDecode();
        mCantinaList = fetchData.getCantina();
    }

    private void loadJSONOffice() {
        final Handler handler = new Handler();
        final NotifierJSONDecode fetchData = new NotifierJSONDecode();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Void result = fetchData.execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                mOffice = fetchData.getOffice();
                mCoffee = mOffice.getCoffee();
                mServant = mOffice.getServant();
                mStatus = mOffice.getStatus();
                mMeetings = mOffice.getMeetings();
            }
        }, 5000);
    }

    private void checkResponsibility() {
        if (mServant.isIfResponsible()) {
            loadRecyclerView(mServant.getServantsList());
        } else {
            TextView noResponsible = findViewById(R.id.no_servants);
            noResponsible.setVisibility(View.VISIBLE);
            noResponsible.setText(mServant.getPersonResponsible());
        }
    }

    private void loadRecyclerView(List<ServantPerson> servantPersonList) {
        mRecyclerView = findViewById(R.id.servantsFragment);
        LinearLayoutManager layoutManager = new LinearLayoutManager(KontoretActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);

        KontoretAdapter kontoretAdapter = new KontoretAdapter(servantPersonList);
        mRecyclerView.setAdapter(kontoretAdapter);
    }
}
