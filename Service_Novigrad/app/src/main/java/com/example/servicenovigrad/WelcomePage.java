package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class WelcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        displayWelcomeMessage();
    }

    public void displayWelcomeMessage(){
        //Placeholders
        String first_name = "my dude";
        String role = "one hell of a guy.";

        TextView welcome_message = findViewById(R.id.welcome_message);
        welcome_message.setText("Welcome " + first_name + "!\nYou are logged in as " + role);
    }
}