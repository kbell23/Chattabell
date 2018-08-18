package com.parse.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    /*
     Method for the act of signing up for Chattabell and making a new account to be added to
     the server.
     */
    public void signup(View view)
    {
        final EditText signupUsernameEditText = (EditText) findViewById(R.id.usernameEditText);
        EditText signupPasswordEditText = (EditText) findViewById(R.id.signupPasswordEditText);
        EditText signupEmailEditText = (EditText) findViewById(R.id.signupEmailEditText);

        ParseUser user = new ParseUser();

        user.setUsername(signupUsernameEditText.getText().toString());
        user.setPassword(signupPasswordEditText.getText().toString());
        user.setEmail(signupEmailEditText.getText().toString());

        user.signUpInBackground(new SignUpCallback()
        {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(SignupActivity.this, "Welcome "
                            + signupUsernameEditText.getText().toString(),
                            Toast.LENGTH_SHORT).show();
                    } else {
                        String message = e.getMessage();

                        if (message.toLowerCase().contains("java")){
                            message = e.getMessage().substring(e.getMessage().indexOf(" "));
                        }

                        Toast.makeText(SignupActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }
}
