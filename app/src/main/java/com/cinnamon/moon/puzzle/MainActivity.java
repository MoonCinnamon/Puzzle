package com.cinnamon.moon.puzzle;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cinnamon.moon.puzzle.Data.PreferencesUtils;
import com.cinnamon.moon.puzzle.Data.SqlUtils;
import com.cinnamon.moon.puzzle.DirectMessage.MakeDirectService;
import com.cinnamon.moon.puzzle.Login.LoginData;
import com.cinnamon.moon.puzzle.Login.OauthThread;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.cinnamon.moon.puzzle.TimeLine.TimeLine_Adapter;
import com.cinnamon.moon.puzzle.Util.Tweet;
import com.cinnamon.moon.puzzle.ViewPagerAdapter.ViewpagerAdapter;
import twitter4j.auth.AccessToken;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ExecutorService executorService;
    private SqlUtils sqlU;
    private PreferencesUtils prefUtils;
    private ArrayList<String> userflow;
    private Tweet tweet;
    private Handler handler;
    private ViewPager pager;
    private ViewpagerAdapter adapter;
    private ArrayList<TimeLine_Adapter> adapterlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prefUtils = new PreferencesUtils(getApplicationContext());

        userflow = new ArrayList<>();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
//                content.setText("");
            }
        };

        adapterlist = new ArrayList<>();

        pager = (ViewPager) findViewById(R.id.MainView);
        adapter = new ViewpagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        tweet = new Tweet(handler);


        executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        sqlU = new SqlUtils(getApplicationContext(), "puzzle.db", null, 1);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (prefUtils.getLoginInfo()) {
            ArrayList<String> list = prefUtils.getData("userflow");
            makePage();
            for (String id : list) {
                OauthThread oauth = new OauthThread(sqlU.getData(id));
                Future<AccessToken> futAccess = executorService.submit(oauth);
                try {
                    //로그인이 끝난 시점 여기서 행동을 한다.
                    if (futAccess.get() != null) {

                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivityForResult(intent, 831);
        }
    }

    public void makePage() {
        //viewpager page 추가 메소드를 구현한다.
        for (int view = 0; view < LoginData.getPage().size(); view++) {
//            String value = LoginData.getPage().get(view);

            TimeLine_Adapter adapter = new TimeLine_Adapter(getApplicationContext(), R.layout.view_timeline_item, getSupportFragmentManager());
            adapterlist.add(adapter);
//            AdapterSetter.setAdapter(value, new List_adapter(adapter_data));
        }
    }

    public View.OnClickListener sendTweet = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            tweet.setMessage(content.getText().toString());
//            new Thread(tweet).start();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String pinnumber = data.getStringExtra("pinCode");
            ArrayList<String> page = new ArrayList<>();
            if (requestCode == 831) {
                login(pinnumber, page);
                //수정요망
                LoginData.setPage(page);
                prefUtils.setLoginInfo(true);
                makePage();
            } else if (requestCode == 74) {
                login(pinnumber, page);
            }
        }
    }

    public void login(String pinnumber, ArrayList<String> page) {
        OauthThread oauth = new OauthThread(pinnumber);
        Future<AccessToken> futAccess = executorService.submit(oauth);
        try {
            if (futAccess.get() != null) {
                executorService.shutdown();

                AccessToken token = futAccess.get();
                //DB에 저장
                ContentValues conVales = new ContentValues();
                conVales.put("id", token.getUserId());
                conVales.put("token", token.getToken());
                conVales.put("tokenSecret", token.getTokenSecret());
                conVales.put("conKey", LoginData.getConsumerKey());
                conVales.put("conSecret", LoginData.getConsumerSecret());
                sqlU.insert(conVales);

                userflow.add(String.valueOf(token.getUserId()));
                prefUtils.setUserflow(userflow);

                for (int num = 0; num < 4; num++)
                    page.add(num + "_" + token.getUserId());

                prefUtils.setPage(String.valueOf(token.getUserId()), page);

                Intent intent = new Intent(this, MakeDirectService.class);
                startService(intent);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
