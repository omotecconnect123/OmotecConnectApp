package com.example.Omotecconnect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseDetailsActivity extends AppCompatActivity {

    String course_name,course_id,sessions,duration;

    TextView no_sess,sess_dura,course_n;

    CourseDetailAdapter crAdapt;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent in = getIntent();
        course_id = in.getStringExtra("course_id");
        course_name = in.getStringExtra("course_name");
        sessions = in.getStringExtra("no_sess");
        duration = in.getStringExtra("sess_dura");

        sendRequest();
        no_sess = (TextView) findViewById(R.id.no_sess);
        sess_dura = (TextView) findViewById(R.id.sess_dura);
        course_n = (TextView) findViewById(R.id.course_n);

        course_n.setText(course_name);
        sess_dura.setText(duration);
        no_sess.setText(sessions);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void sendRequest(){


        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_HOME_CR_DET, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        //success - parse JSON
                        try {
                            JSONObject json = new JSONObject(response);
                            if(json.getString("response").equals("true"))
                            {
                                String c1 = json.getString("json1");
                                JSONObject c2 = json.getJSONObject("json2");

                                List<String> myList = new ArrayList<String>(Arrays.asList(c1.substring(1,c1.length()-1).replace("\"","").split(",")));
                                Log.e("result", String.valueOf(myList));

                                crAdapt = new CourseDetailAdapter(myList,c2,CourseDetailsActivity.this);
                                recyclerView.setAdapter(crAdapt);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CourseDetailsActivity.this,error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                Log.e("id",course_id);
                params.put("course_id", course_id);

                return params;
            }

        };
        //The following lines add the request to the volley queue
        //These are very important
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
