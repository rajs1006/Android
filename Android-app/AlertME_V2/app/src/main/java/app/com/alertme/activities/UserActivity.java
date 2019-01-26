package app.com.alertme.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.com.alertme.R;
import app.com.alertme.db.helpers.UserHelper;
import app.com.alertme.db.schemas.UserSchema;

import static app.com.alertme.R.id.email;

public class UserActivity extends AppCompatActivity {

    UserHelper myDB;
    EditText nameBox, emailBox, passwordBox, phoneBox, emergencyContact1Box,emergencyContact2Box, countryBox, regionBox;
    Button registerButton;
    TextView loginLink;
    String idHidden;
    String mEmailView;
    String mGoogleCountry;
    String mGoogleRegion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        myDB = new UserHelper(this);

        nameBox = (EditText) findViewById(R.id.name);
        emailBox = (EditText) findViewById(email);
        passwordBox = (EditText) findViewById(R.id.password);
        phoneBox = (EditText) findViewById(R.id.phone);
        emergencyContact1Box = (EditText) findViewById(R.id.emergencycontact1);
        emergencyContact2Box = (EditText) findViewById(R.id.emergencycontact2);
        countryBox = (EditText) findViewById(R.id.country);
        regionBox = (EditText) findViewById(R.id.region);

        registerButton = (Button) findViewById(R.id.email_register_button);
        loginLink = (TextView) findViewById(R.id.email_sign_in_button);

        if (isHomePageRedirect()) {
            populateUser();
            emailBox.setEnabled(false);
            registerButton.setText("Update");
            loginLink.setText("Go back to home");
            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateUser();
                }
            });
            loginLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent homePageIntent = new Intent(UserActivity.this, HomeActivity.class);
                    homePageIntent.putExtra("userEmail", mEmailView);
                    startActivity(homePageIntent);
                }
            });
        } else {
            countryBox.setText(mGoogleCountry);
            regionBox.setText(mGoogleRegion);

            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registerUser();
                }
            });

            loginLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(UserActivity.this, LoginActivity.class));
                }
            });
        }
        countryBox.setEnabled(false);
        regionBox.setEnabled(false);
    }

    private Boolean isHomePageRedirect() {
        Intent homePageIntent = getIntent();
        mEmailView = homePageIntent.getStringExtra("userEmail");
        mGoogleCountry = homePageIntent.getStringExtra("country");
        mGoogleRegion = homePageIntent.getStringExtra("region");
        String homePageRedirect = homePageIntent.getStringExtra("pageRedir");

        if (null != homePageRedirect && "true".equals(homePageRedirect)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private void registerUser() {
        boolean cancel = validateForm();
        if (!cancel) {
            boolean correctInsert = myDB.insertdata(nameBox.getText().toString(),
                    emailBox.getText().toString(), passwordBox.getText().toString(),
                    phoneBox.getText().toString(),emergencyContact1Box.getText().toString(),
                    emergencyContact2Box.getText().toString(), countryBox.getText().toString(),
                    regionBox.getText().toString());

            if (correctInsert) {
                Toast.makeText(this, "New record added", Toast.LENGTH_LONG).show();
                nameBox.setText("");
                emailBox.setText("");
                passwordBox.setText("");
                phoneBox.setText("");
            }
        }
    }

    private void populateUser() {

        Cursor result = myDB.getUserByEmail(mEmailView);

        while (result.moveToNext()) {
            idHidden = result.getString(result.getColumnIndex(UserSchema.Tableinfo.userId));
            nameBox.setText(result.getString(result.getColumnIndex(UserSchema.Tableinfo.userName)));
            emailBox.setText(result.getString(result.getColumnIndex(UserSchema.Tableinfo.emailId)));
            passwordBox.setText(result.getString(result.getColumnIndex(UserSchema.Tableinfo.password)));
            phoneBox.setText(result.getString(result.getColumnIndex(UserSchema.Tableinfo.phone)));
            emergencyContact1Box.setText(result.getString(result.getColumnIndex(UserSchema.Tableinfo.emergencyContact1)));
            emergencyContact2Box.setText(result.getString(result.getColumnIndex(UserSchema.Tableinfo.emergencyContact2)));
            countryBox.setText(result.getString(result.getColumnIndex(UserSchema.Tableinfo.country)));
            regionBox.setText(result.getString(result.getColumnIndex(UserSchema.Tableinfo.region)));
        }
    }

    private Boolean isEmailAlreadyExist(String email) {
        Boolean isEmailAlreadyExist = Boolean.FALSE;
        Cursor result = myDB.getRowCountByEmail(email);

        if (result.moveToNext()) {
            isEmailAlreadyExist = Boolean.TRUE;
        }
        return isEmailAlreadyExist;
    }

    private void updateUser() {
        boolean cancel = validateForm();
        if (!cancel) {
            boolean correctUpdate = myDB.updatedata(idHidden, nameBox.getText().toString(),
                    emailBox.getText().toString(), passwordBox.getText().toString(),
                    phoneBox.getText().toString(), emergencyContact1Box.getText().toString(),
                    emergencyContact2Box.getText().toString(),countryBox.getText().toString(),
                    regionBox.getText().toString());

            if (correctUpdate) {
                Toast.makeText(this, "Record updated", Toast.LENGTH_LONG).show();
            }
        }

    }

    private boolean validateForm() {

        boolean cancel = false;
        View focusView = null;
        // Store values at the time of the login attempt.
        String email = emailBox.getText().toString();
        String password = passwordBox.getText().toString();
        String phone = phoneBox.getText().toString();
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            passwordBox.setError(getString(R.string.error_field_required));
            focusView = passwordBox;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            passwordBox.setError(getString(R.string.error_invalid_password));
            focusView = passwordBox;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailBox.setError(getString(R.string.error_field_required));
            focusView = emailBox;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailBox.setError(getString(R.string.error_invalid_email));
            focusView = emailBox;
            cancel = true;
        }else if(isEmailAlreadyExist(email)){
            if(!isHomePageRedirect()) {
                emailBox.setError(getString(R.string.error_email_exist));
                focusView = emailBox;
                cancel = true;
            }
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(phone)) {
            phoneBox.setError(getString(R.string.error_field_required));
            focusView = phoneBox;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        return cancel;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}