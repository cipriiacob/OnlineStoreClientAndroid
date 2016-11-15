package com.example.cipri.onlinestoreclient.src.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by Cipri on 12-Nov-16.
 */

public class SignInActivity extends Activity {

    private Button signUpButton;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText repeatPasswordEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sign_up_layout);

        init();

    }

    private void init() {

        signUpButton = (Button) findViewById(R.id.register_button);
        signUpButton.setOnClickListener(registerHandler);

        firstNameEditText = (EditText) findViewById(R.id.first_name_edit_field);
        lastNameEditText = (EditText) findViewById(R.id.last_name_edit_field);
        emailEditText = (EditText) findViewById(R.id.email_edit_text);
        usernameEditText = (EditText) findViewById(R.id.username_edit_text);
        passwordEditText = (EditText) findViewById(R.id.first_password_edit_text);
        repeatPasswordEditText = (EditText) findViewById(R.id.second_password_edit_text);


    }

    View.OnClickListener registerHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String errors = validateFields();

            if (errors.length() > 0) {
                Toast toast = Toast.makeText(getApplicationContext(), errors, Toast.LENGTH_SHORT);
                toast.show();
            } else {

                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                registerCall(firstName, lastName, email, username, password);

            }
        }
    };

    private void registerCall(String firstName, String lastName, String email, String username, String password) {

        try {


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("firstName", firstName);
            jsonObject.put("lastName", lastName);
            jsonObject.put("email", email);
            jsonObject.put("username", username);
            jsonObject.put("password", password);

            StringEntity entity = new StringEntity(jsonObject.toString());

            StoreRestClient.post(getApplicationContext(), "/users/register", entity, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Registered with succes", Toast.LENGTH_SHORT);
                    toast.show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (statusCode == 409) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Username already registered " +
                                statusCode, Toast
                                .LENGTH_SHORT);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Internal server error, please try " +
                                "again later" + statusCode, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private String validateFields() {

        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String repeatPassword = repeatPasswordEditText.getText().toString();

        String error = "";
        if (firstName.length() == 0) {
            error = "Enter your first name";
        } else if (firstName.length() > 100) {
            error = "First name can not exceed 100 characters";
        } else if (lastName.length() == 0) {
            error = "Enter your last name";
        } else if (lastName.length() > 100) {
            error = "Last name can not exceed 100 characters";
        } else if (email.length() == 0) {
            error = "Enter your email";
        } else if (email.length() > 100) {
            error = "Your email can not exceed 100 characters";
        } else if (username.length() == 0) {
            error = "Enter a username";
        } else if (lastName.length() > 100) {
            error = "The username can not exceed 100 characters";
        } else if (password.length() == 0) {
            error = "Enter a password";
        } else if (password.length() < 4) {
            error = "Your password must be longer than 4 characters";
        } else if (lastName.length() > 100) {
            error = "Your password can not exceed 100 characters";
        } else if (repeatPassword.length() == 0) {
            error = "Repeat the password";
        } else if (password.compareTo(repeatPassword) != 0) {
            error = "Passwords don't match";
        }
        String rule = "\\A[a-zA-Z0-9!#$%&'*+=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+=?^_`{|}~-]+)*@(?:[a-z0-9]" +
                "(?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\z";
        if (error.length() == 0) {
            if (!email.matches(rule)) {
                error = "Incorrect email format";
            }
        }
        return error;
    }
}
