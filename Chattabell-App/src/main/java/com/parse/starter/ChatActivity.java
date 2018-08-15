package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/*
The ChatActivity class allows users to communicate with each other given the appropriate
intent and stores the data for the two users to look back on.
 */
public class ChatActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();

        String activeUser = intent.getStringExtra("username");

        setTitle(activeUser);

        Log.i("Info", activeUser);
    }
}
