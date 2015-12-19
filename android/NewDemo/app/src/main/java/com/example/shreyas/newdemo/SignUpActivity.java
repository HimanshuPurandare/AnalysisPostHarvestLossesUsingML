package com.example.shreyas.newdemo;

import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

public class SignUpActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>
{
    private AutoCompleteTextView signup_email;
    private EditText mPasswordView;
    private EditText mConfirmPassword;
    private EditText name;
    private RadioButton rd_farmer,rd_gdm,rd_both;

    private static final int REQUEST_READ_CONTACTS = 0;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setupToolbar();

        signup_email = (AutoCompleteTextView) findViewById(R.id.signup_email);
        populateAutoComplete();


        mPasswordView = (EditText) findViewById(R.id.signup_password);
        mConfirmPassword = (EditText) findViewById(R.id.signup_confirm_password);
        name = (EditText) findViewById(R.id.signup_name);
        rd_farmer=(RadioButton)findViewById(R.id.role_farmer);
        rd_gdm=(RadioButton)findViewById(R.id.role_gdmanager);
        rd_both=(RadioButton)findViewById(R.id.role_both);

        Button mEmailSignInButton = (Button) findViewById(R.id.register_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignup();
            }
        });



    }
    void setupToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SignUp");
        //getSupportActionBar().setSubtitle("Pune");
        toolbar.setTitleTextColor(getResources().getColor(R.color.Text_Icon));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.LightPrimaryColor));
    }


    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(signup_email, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(SignUpActivity.this,SignIn.class);
        startActivity(i);
        finish();
    }




    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSignup() {
        //if (mAuthTask != null) {
        //    return;
        //}

        // Reset errors.
        signup_email.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the signup attempt.
        final String string_name = name.getText().toString();
        final String email = signup_email.getText().toString();
        final String password = mPasswordView.getText().toString();
        final String confirm_passowrd = mConfirmPassword.getText().toString();
        final String user_role;


        if(rd_farmer.isChecked())
        {

            user_role="Farmer";
        }
        else if(rd_gdm.isChecked())
        {
            user_role="Go-Down Manager";
        }
        else
        {
            user_role="Both";
        }

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if(!confirm_passowrd.equals(password))
        {
            mPasswordView.setError(getString(R.string.error_password_mismatch));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            signup_email.setError(getString(R.string.error_field_required));
            focusView = signup_email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            signup_email.setError(getString(R.string.error_invalid_email));
            focusView = signup_email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            SharedPreferences sharedPreferences=getSharedPreferences("AppRegistration", Context.MODE_PRIVATE);

            String token=sharedPreferences.getString("RegistrationToken", "-");
            if(MainActivity.ConnectedToNetwork)
            {

                JSONObject j = new JSONObject();
                try {
                    j.put("SignUpUid", email);
                    j.put("SignUpPwd", password);
                    j.put("SignUpName", string_name);
                    j.put("SignUpRole", user_role);

                    if (token.equals("-"))
                    {
                        Log.d("RegistrationToken : ","NULL");
                    }
                    else
                    {
                        j.put("registrationtoken",token);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url = MainActivity.ServerIP + "/signup/";
                JsonObjectRequest jsonRequest = new JsonObjectRequest
                        (Request.Method.POST, url, j, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // the response is already constructed as a JSONObject!
                                try {
                                    response = response.getJSONObject("Android");
                                    String signinresult = response.getString("Result");
                                    if (signinresult.equals("Valid")) {
                                        Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                                        MainActivity.signedin = 1;
                                        SharedPreferences sharedPreferences = getSharedPreferences("SignInData", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("SignedInStatus", "Yes");
                                        editor.putString("SignedInEmailId", email);
                                        editor.putString("SignedInName", string_name);
                                        editor.putString("SignedInRole",user_role);
                                        editor.commit();

                                        startActivity(i);
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        });
                MainActivity.getInstance().addToRequestQueue(jsonRequest);

            }
            else
            {
                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(SignUpActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        signup_email.setAdapter(adapter);
    }


}
