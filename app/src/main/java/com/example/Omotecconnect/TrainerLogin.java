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
public class TrainerLogin extends Fragment {

    //private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnEmpLogin;

    private EditText Employee_Code;
    private EditText Employee_password;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    String str_Emp_Code, str_Emp_password;

    public TrainerLogin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trainer_login, container, false);

        btnEmpLogin = (Button) view.findViewById(R.id.btnEmpLogin);

        Employee_Code = (EditText) view.findViewById(R.id.Employee_Code);
        Employee_password = (EditText) view.findViewById(R.id.Employee_password);

        btnEmpLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TLogin();
            }
        });

        return view;
    }

    public void TLogin() {

        if (Employee_Code.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Please Enter Your Employee code", Toast.LENGTH_SHORT).show();
            Employee_Code.setError("This field can not be blank");
        } else if (Employee_password.getText().toString().equals("")) {

            Employee_password.setError("Enter Password");
            Toast.makeText(getActivity(), "Enter Password", Toast.LENGTH_SHORT).show();
        } else {


            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please Wait..");

            progressDialog.show();
            progressDialog.setCancelable(false);

            str_Emp_Code = Employee_Code.getText().toString().trim();
            str_Emp_password = Employee_password.getText().toString().trim();


            StringRequest request = new StringRequest(Request.Method.POST, AppConfig.URL_Trainer_LOGIN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    if (response.equalsIgnoreCase("Trainer logged in")) {

                        Employee_Code.setText("");
                        Employee_password.setText("");
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();


                        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
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
                    params.put("empcode", str_Emp_Code);
                    params.put("password", str_Emp_password);
                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(request);


        }

        //Login function
    }
}