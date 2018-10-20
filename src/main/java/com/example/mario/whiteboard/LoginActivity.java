package com.example.mario.whiteboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button sign_in = findViewById(R.id.sign_in);
        final EditText username = findViewById(R.id.name);
        sign_in.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!username.getText().toString().matches("")){
                    Intent memory = new Intent(getApplicationContext(), WhiteboardActivity.class);
                    startActivity(memory);
                    finish();
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}

