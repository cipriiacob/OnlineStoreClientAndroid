package com.example.cipri.onlinestoreclient.src.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cipri.onlinestoreclient.R;
import com.example.cipri.onlinestoreclient.src.utils.StoreRestClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginScreen extends AppCompatActivity {
    private static Button loginButton;
    private EditText usernameInputField;
    private EditText passwordInputField;
    private ProgressBar progressBar;
    private static Button signUpButton;
//    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login_screen);

        init();

    }

    private void init() {

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(loginHandler);
        progressBar = (ProgressBar) findViewById(R.id.login_progress_bar);
        progressBar.setVisibility(View.GONE);
        usernameInputField = (EditText) findViewById(R.id.username_input_field);
        passwordInputField = (EditText) findViewById(R.id.password_input_field);

        signUpButton = (Button) findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(sighUpHandler);
    }

    public void loginCall(String username, String password) {

        final Context context = getApplicationContext();

        try {
            StringEntity entity;

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("username", username);
            jsonObject.put("password", password);

            entity = new StringEntity(jsonObject.toString());

            StoreRestClient.post(context, "/users/login", entity, new AsyncHttpResponseHandler() {


                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    Toast toast = Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT);
                    toast.show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast toast;
                    if (statusCode == 403) {
                        progressBar.setVisibility(View.GONE);

                        toast = Toast.makeText(context, "Wrong username or password", Toast.LENGTH_SHORT);
                    } else if (statusCode == 500) {

                        progressBar.setVisibility(View.GONE);
                        toast = Toast.makeText(context, "Server error" + statusCode, Toast.LENGTH_SHORT);
                    } else {

                        progressBar.setVisibility(View.GONE);
                        toast = Toast.makeText(context, "Connection error, try again later", Toast
                                .LENGTH_SHORT);
                    }
                    toast.show();
                }
            });

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    View.OnClickListener loginHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Context context = getApplicationContext();

            String username = usernameInputField.getText().toString().trim();
            String password = passwordInputField.getText().toString().trim();

            String errors = "";
            if (username.length() == 0) {

                errors += "Enter your username";
            } else if (password.length() == 0) {

                errors += "Enter your password";
            }
            if (errors.length() > 0) {

                Toast toast = Toast.makeText(context, errors, Toast.LENGTH_SHORT);
                toast.show();
            } else {
                progressBar.setVisibility(View.VISIBLE);
                loginCall(username, password);
            }


        }
    };
    View.OnClickListener sighUpHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(intent);
        }
    };

}
