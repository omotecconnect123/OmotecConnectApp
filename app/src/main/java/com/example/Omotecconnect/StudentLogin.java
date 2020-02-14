package com.example.Omotecconnect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentLogin extends Fragment {


    //private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnLogin;
    private Button btnLinkToRegister;
    private Button skip;
    private EditText Student_id;
    private EditText Stud_password;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    private TextView forgot_pass;

    String str_Student_id,str_Student_password;

    public StudentLogin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_login, container, false);
        // Inflate the layout for this fragment
        btnLogin = (Button) view.findViewById(R.id.btnLogin);

        Student_id = (EditText) view.findViewById(R.id.Student_id);
        Stud_password = (EditText) view.findViewById(R.id.Stud_password);

        skip = (Button) view.findViewById(R.id.skip);
        //btnLinkToRegister = (Button) view.findViewById(R.id.btnLinkToRegisterScreen);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getActivity());


        forgot_pass = (TextView) view.findViewById(R.id.forgot_pass);

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Forgot_Password.class);
                startActivity(intent);
                getActivity().finish();
            }
        });



       /* btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Logged in", Toast.LENGTH_SHORT).show();
            }
        });*/


        session = new SessionManager(getActivity());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(getActivity(), HomePage.class);
            startActivity(intent);
            getActivity().finish();
        }

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomePage.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
               /* String Studentid = Student_id.getText().toString().trim();
                String Studpassword = Stud_password.getText().toString().trim();

                // Check for empty data in the form
                if (!Studentid.isEmpty() && !Studpassword.isEmpty()) {
                    // login user
                    Login(Studentid, Studpassword);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getActivity(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }*/
                SLogin();

            }

        });
        return view;
    }

    public void SLogin() {

        if (Student_id.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Please Enter Your Employee code", Toast.LENGTH_SHORT).show();
            Student_id.setError("This field can not be blank");
        } else if (Stud_password.getText().toString().equals("")) {

            Stud_password.setError("Enter Password");
            Toast.makeText(getActivity(), "Enter Password", Toast.LENGTH_SHORT).show();
        } else {


            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please Wait..");

            progressDialog.show();
            progressDialog.setCancelable(false);

            str_Student_id = Student_id.getText().toString().trim();
            str_Student_password = Stud_password.getText().toString().trim();


            StringRequest request = new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    if (response.equalsIgnoreCase("Trainer logged in")) {


                        Student_id.setText("");
                        Stud_password.setText("");

                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);


                        startActivity(new Intent(getActivity(), HomePage.class));
                        Toast.makeText(getActivity(), "Logged in", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("studentid", str_Student_id);
                    params.put("password", str_Student_password);
                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(request);


        }

        //Login function
    }


        private void showDialog() {
            if (!pDialog.isShowing())
                pDialog.show();
        }

        private void hideDialog() {
            if (pDialog.isShowing())
                pDialog.dismiss();
        }




    }

