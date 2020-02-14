package com.example.Omotecconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Forgot_Password extends AppCompatActivity {

    private EditText Student_id;
    private Button forgot_mail;

    private String str_Student_id_forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);


        Student_id = (EditText) findViewById(R.id.Student_id);

        forgot_mail = (Button) findViewById(R.id.forgot_mail);

        forgot_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Forgot_Pass();
            }
        });

    }


        public void Forgot_Pass() {

            if (Student_id.getText().toString().equals("")) {

                Student_id.setError("This field can not be blank");
            }
            else {


                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Please Wait..");


                progressDialog.show();
                progressDialog.setCancelable(false);

                str_Student_id_forgot = Student_id.getText().toString().trim();


                StringRequest request = new StringRequest(Request.Method.POST, AppConfig.URL_FORGOT_PASS, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        if (response.equals("Sent")) {


                            Student_id.setText("");


                            // user successfully logged in
                            // Create login session



                            startActivity(new Intent(Forgot_Password.this,MainActivity.class));

                            Toast.makeText(Forgot_Password.this, "password is sent on your given email", Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Forgot_Password.this, "Failed to send", Toast.LENGTH_SHORT).show();

                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(Forgot_Password.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("studentidforgot", str_Student_id_forgot);

                        return params;

                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(request);


            }

            //Login function
        }
    }





