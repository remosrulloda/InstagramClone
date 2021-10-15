package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity
{

    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignup;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (ParseUser.getCurrentUser() != null)
        {
            goMainActivity();
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        // Dialog screen when user enters the wrong password
        dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.error_dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
        }

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        Button retry = dialog.findViewById(R.id.btnRetry);

        retry.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Toast.makeText(LoginActivity.this, "Retry", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });



        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick login button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Create the ParseUser
                ParseUser user = new ParseUser();
                // Set core properties
                user.setUsername(etUsername.getText().toString());
                user.setPassword(etPassword.getText().toString());
                // Invoke signUpInBackground
                user.signUpInBackground(new SignUpCallback()
                {
                    public void done(ParseException e)
                    {
                        if (e == null)
                        {
                            // Hooray! Let them use the app now.
                            Toast.makeText(LoginActivity.this, "Signup Successful!", Toast.LENGTH_SHORT).show();
                            goMainActivity();
                        }
                        else
                        {
                            // Sign up didn't succeed. Look at the ParseException
                            // to figure out what went wrong
                            Toast.makeText(LoginActivity.this, "Signup Unsuccessful!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });




    }

    private void loginUser(String username, String password)
    {
        Log.i(TAG, "Attempting to login user " + username);
        ParseUser.logInInBackground(username, password, new LogInCallback()
        {
            @Override
            public void done(ParseUser user, ParseException e)
            {
                if (e != null){
                    // TODO: better error handling;
                    Log.e(TAG, "Issue with login", e);
                    Toast.makeText(LoginActivity.this, "Issue with login!", Toast.LENGTH_SHORT).show();
                    dialog.show();

                    return;
                }
                // TODO: navigate to the main activity if the user has signed in properly
                goMainActivity();
                Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goMainActivity()
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }




}