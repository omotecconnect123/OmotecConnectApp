package com.example.Omotecconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class testing extends AppCompatActivity {
    public Button btnLogout;
    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);


        session = new SessionManager(getApplicationContext());
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());


    }
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(testing.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void log(View view) {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(testing.this, MainActivity.class);
        startActivity(intent);
        finish();


    }
}
