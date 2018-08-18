/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;


public class MainActivity extends AppCompatActivity
{
    public void redirectIfLoggedIn()
    {
        if (ParseUser.getCurrentUser() != null)
        {
            Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
            startActivity(intent);
        }
    }

    /*
    Method for the act of logging into Chattabell. If the user already exists, the username and
    password gathered from the edit texts will be used to check with the Parse server to log the
    user into the app to begin chatting. If the user doesn't exist, a toast is made detailing
    what went wrong with the process of logging in.
     */
    public void login(View view)
    {

        final EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null)
                {
                    Toast.makeText(MainActivity.this, "Welcome "
                                    + usernameEditText.getText().toString(),
                            Toast.LENGTH_SHORT).show();
                    redirectIfLoggedIn();
                }
                else {
                    Toast.makeText(MainActivity.this, e.getMessage()
                            .substring(e.getMessage().indexOf(" ")), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // The signup method takes the user to the SignupActivity to create a new account on the server.
    public void signup(View view)
    {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        redirectIfLoggedIn();

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

}