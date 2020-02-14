package com.example.Omotecconnect;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private SessionManager session;
    MenuItem course,acc,login;
    TextView nav_name,nav_email,six,nine;
    private SQLiteHandler db;

    List<Course> cl1,cl2;
    String t,f;
    public static String[] names;
    public static String[] ids;

    private VideoView mVideoView;

    //the recyclerview
    RecyclerView recyclerView,recyclerView2;
    CourseAdapter crAdapt;

    Button ten,fifteen, icra,nano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sendRequest();

       /* mVideoView = (VideoView) findViewById(R.id.videoView);

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.sample123);

        mVideoView.setVideoURI(uri);
        mVideoView.start();

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        MediaController mediaController = new MediaController(HomePage.this);
                        mVideoView.setMediaController(mediaController);
                        mediaController.setAnchorView(mVideoView);
                    }
                });
            }
        });

        */

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        session = new SessionManager(getApplicationContext());
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());


        Menu nav_menu = navigationView.getMenu();

        View header = navigationView.getHeaderView(0);
        nav_name = (TextView) header.findViewById(R.id.nav_name);
        nav_email = (TextView) header.findViewById(R.id.nav_email);

        acc = (MenuItem) nav_menu.findItem(R.id.nav_account);
        course = (MenuItem) nav_menu.findItem(R.id.nav_courses);
        login = (MenuItem) nav_menu.findItem(R.id.nav_login);

        ten = (Button) findViewById(R.id.ten);
        fifteen = (Button) findViewById(R.id.fifteen);
        icra = (Button) findViewById(R.id.icra);
        nano = (Button) findViewById(R.id.nano);

        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent in = new Intent(HomePage.this,SpecialCoursesActivity.class);
                in.putExtra("list",t);
                startActivity(in);
                */
                Toast.makeText(HomePage.this,"ten", Toast.LENGTH_LONG).show();

            }
        });

        fifteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomePage.this,"fifteen", Toast.LENGTH_LONG).show();

                /*
                Intent in = new Intent(HomePage.this,SpecialCoursesActivity.class);
                in.putExtra("list",f);
                startActivity(in);

                 */
            }
        });

        icra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        nano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            acc.setVisible(true);
            course.setVisible(true);
            login.setTitle("Logout");
            // Fetching user details from sqlite
            HashMap<String, String> user = db.getUserDetails();

            String name = user.get("name");
            String email = user.get("email");
            nav_name.setText(name);
            nav_email.setText(email);
        }
        else
        {
            acc.setVisible(false);
            course.setVisible(false);
            login.setTitle("Login");
            nav_name.setText("");
            nav_email.setText("");
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        recyclerView2 = (RecyclerView) findViewById(R.id.recycleView2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        cl1 = new ArrayList<>();
        cl2 = new ArrayList<>();

    }

    private void sendRequest(){


        StringRequest stringRequest = new StringRequest(AppConfig.URL_HOME_CR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //success - parse JSON
                            try {
                                JSONObject json = new JSONObject(response);
                                if(json.getString("response").equals("true"))
                                {
                                    JSONArray c1 = json.getJSONArray("json1");
                                    JSONArray c2 = json.getJSONArray("json2");
                                    JSONArray c3 = json.getJSONArray("json3");
                                    JSONArray c4 = json.getJSONArray("json4");

                                    names = new String[c1.length()];
                                    ids = new String[c1.length()];

                                    for(int i=0;i< c1.length();i++){

                                        Course obj =  new Course();

                                        JSONObject jsonObject = c1.getJSONObject(i);

                                        ids[i] = jsonObject.getString("course_id");
                                        names[i] = jsonObject.getString("course_name");

                                        obj.setCourseName(names[i]);
                                        obj.setId(ids[i]);
                                        obj.setSessions(jsonObject.getString("no_session"));
                                        obj.setDuration(jsonObject.getString("session_duration"));
                                        cl1.add(obj);
                                    }

                                    for(int i=0;i< c2.length();i++){

                                        Course obj =  new Course();

                                        JSONObject jsonObject = c2.getJSONObject(i);

                                        ids[i] = jsonObject.getString("course_id");
                                        names[i] = jsonObject.getString("course_name");

                                        obj.setCourseName(names[i]);
                                        obj.setId(ids[i]);
                                        obj.setSessions(jsonObject.getString("no_session"));
                                        obj.setDuration(jsonObject.getString("session_duration"));
                                        cl2.add(obj);
                                    }

                                    crAdapt = new CourseAdapter(cl1,HomePage.this);
                                    recyclerView.setAdapter(crAdapt);

                                    crAdapt = new CourseAdapter(cl2,HomePage.this);
                                    recyclerView2.setAdapter(crAdapt);

                                    t = c3.toString();
                                    f = c4.toString();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HomePage.this,error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        //The following lines add the request to the volley queue
        //These are very important
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onRestart() {
        super.onRestart();
       /* Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.sample123);

        mVideoView.setVideoURI(uri);
        mVideoView.start();

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        MediaController mediaController = new MediaController(HomePage.this);
                        mVideoView.setMediaController(mediaController);
                        mediaController.setAnchorView(mVideoView);
                    }
                });
            }
        });  */
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_special) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_account) {

        } else if (id == R.id.nav_login) {
            if(login.getTitle().equals("Login")) {
                Intent i = new Intent(HomePage.this, MainActivity.class);
                startActivity(i);
                finish();
            }
            else
            {
                logoutUser();
            }
        } else if (id == R.id.nav_six) {

        } else if (id == R.id.nav_nine) {

        } else if (id == R.id.nav_website) {
            Uri uri = Uri.parse("http://www.onmyowntechnology.com");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else if (id == R.id.nav_contact) {
            Intent intent = new Intent(HomePage.this, ContactUsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(HomePage.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
