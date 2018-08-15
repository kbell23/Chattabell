package com.parse.starter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity
{
    // arraylist of users on the server
    ArrayList<String> users = new ArrayList<String>();
    // arraylist of the users current friends
    ArrayList<String> userFriends = new ArrayList<String>();

    ArrayAdapter arrayAdapter;

    ListView userListView;

    Button addButton;
    Button cancelButton;

    EditText nameAddEditText;

    String friendString;

    // Method to return to the user list.
    public void cancel(View view)
    {

        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // dismisses android keyboard
                nameAddEditText.onEditorAction(EditorInfo.IME_ACTION_DONE);

                // clears the nameAddEditText and restores the user list.
                nameAddEditText.getText().clear();
                userListView.setVisibility(View.VISIBLE);
                addButton.setVisibility(View.INVISIBLE);
                cancelButton.setVisibility(View.INVISIBLE);
                nameAddEditText.setVisibility(View.INVISIBLE);
            }
        });
    }

    // Method to add a user to the userFriends array list
    public void addUser(View view)
    {
        userListView = (ListView) findViewById(R.id.userListView);
        addButton = (Button) findViewById(R.id.addButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        nameAddEditText = (EditText) findViewById(R.id.nameAddEditText);

        // copies the nameAddEditText text into a string.
        friendString = nameAddEditText.getText().toString();


        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, users);

        userListView.setAdapter(arrayAdapter);

        // declares the query and searches for usernames to be added to the array list userFriends.
        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseUser>()
        {
            @Override
            public void done(List<ParseUser> objects, ParseException e)
            {
                if (e == null)
                {
                    if (objects.size() > 0)
                    {
                        /*
                        searches through the server and checks if the input given by the
                        userEditText is a username in the server. If it is, and it's not already
                        in the userFriends array list, add the string to the userListView
                        and userFriends.
                         */
                        for (ParseUser user : objects)
                        {
                            if (user.getUsername().equals(friendString)
                                    && !userFriends.contains(friendString))
                            {
                                users.add(user.getUsername());
                                userFriends.add(friendString);
                            }
                        }
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });

        // shows the user list
        nameAddEditText.getText().clear();
        userListView.setVisibility(View.VISIBLE);
        addButton.setVisibility(View.INVISIBLE);
        cancelButton.setVisibility(View.INVISIBLE);
        nameAddEditText.setVisibility(View.INVISIBLE);
    }

    // to activate menu create a menu.xml resource file and then:
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);

        // add_menu switch statement
        switch(item.getItemId())
        {
            // given the scenario where the user wants to add a user to his/her friends
            case R.id.addUser:
                userListView = (ListView) findViewById(R.id.userListView);
                addButton = (Button) findViewById(R.id.addButton);
                cancelButton = (Button) findViewById(R.id.cancelButton);
                nameAddEditText = (EditText) findViewById(R.id.nameAddEditText);
                friendString = nameAddEditText.getText().toString();

                // hides the user list for an edit text and buttons to add a new user
                userListView.setVisibility(View.INVISIBLE);
                addButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
                nameAddEditText.setVisibility(View.VISIBLE);

                return true;
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        setTitle("User List");

        ListView userListView = (ListView)findViewById(R.id.userListView);

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("username", users.get(i));
                startActivity(intent);
            }
        });

        users.clear();

        // goes through the users arrayList and checks if we already have friends upon startup.
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, users);

        userListView.setAdapter(arrayAdapter);

        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseUser>()
        {
            @Override
            public void done(List<ParseUser> objects, ParseException e)
            {
                if (e == null)
                {
                    if (objects.size() > 0)
                    {
                        for (ParseUser user : objects)
                        {
                            if(userFriends.contains(user.getUsername())) {
                                users.add(user.getUsername());
                            }
                        }
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
