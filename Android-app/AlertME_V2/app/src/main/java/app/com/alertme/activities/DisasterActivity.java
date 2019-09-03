package app.com.alertme.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.com.alertme.R;
import app.com.alertme.db.helpers.DisasterHelper;
import app.com.alertme.db.schemas.DisasterSchema;
import me.srodrigo.androidhintspinner.HintAdapter;
import me.srodrigo.androidhintspinner.HintSpinner;

/**
 * Created by 21701234 on 10/6/2017.
 */

public class DisasterActivity extends AppCompatActivity {


    DisasterHelper myDB;
    
    HintSpinner<String> defaultHintSpinner;
    Spinner spinner;
    EditText disDetailsBox, countryBox, regionBox, disEmergencyBox;
    String disNameBox;
    Button registerButton;
    TextView homeLink;


    String mEmailView;
    String idHidden;
    String mGoogleCountry;
    String mGoogleRegion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster);

        myDB = new DisasterHelper(this);
        populateExtra();
        // Prepare dropdown.
        disasterHintSpinner();
        // Text view.
        disDetailsBox = (EditText) findViewById(R.id.dis_details);
        countryBox = (EditText) findViewById(R.id.country);
        regionBox = (EditText) findViewById(R.id.region);
        disEmergencyBox = (EditText) findViewById(R.id.dis_emergency);

        registerButton = (Button) findViewById(R.id.email_register_button);
        homeLink = (TextView) findViewById(R.id.email_home_button);

        if (isHomePageRedirect()) {
            populateDisaster();
            registerButton.setText("Update");
            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateDisaster();
                }
            });
        } else {
            countryBox.setText(mGoogleCountry);
            regionBox.setText(mGoogleRegion);

            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registerDisaster();
                }
            });

        }
        homeLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerPageIntent = new Intent(DisasterActivity.this, HomeActivity.class);
                registerPageIntent.putExtra("userEmail", mEmailView);
                startActivity(registerPageIntent);
            }
        });
        countryBox.setEnabled(false);
        regionBox.setEnabled(false);
    }

    private void populateExtra() {
        Intent intent = getIntent();
        mEmailView = intent.getStringExtra("userEmail");
        mGoogleCountry = intent.getStringExtra("country");
        mGoogleRegion = intent.getStringExtra("region");
    }

    private Boolean isHomePageRedirect() {
        Intent homePageIntent = getIntent();
        mEmailView = homePageIntent.getStringExtra("userEmail");
        idHidden = homePageIntent.getStringExtra("disasterId");
        String homePageRedirect = homePageIntent.getStringExtra("pageRedir");

        if (null != homePageRedirect && "true".equals(homePageRedirect)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    private void registerDisaster() {
        boolean cancel = validateForm();
        if (!cancel) {
            boolean correctInsert = myDB.insertdata(disNameBox,
                    disDetailsBox.getText().toString(), countryBox.getText().toString(),
                    regionBox.getText().toString(), disEmergencyBox.getText().toString());

            if (correctInsert) {
                Toast.makeText(this, "New record added", Toast.LENGTH_LONG).show();
                disDetailsBox.setText("");
                disEmergencyBox.setText("");
            }
        }
    }

    private void populateDisaster() {

        Cursor result = myDB.getDisastersById(idHidden);

        while (result.moveToNext()) {
            String selectedDisaster = result.getString(result.getColumnIndex(DisasterSchema.Tableinfo.disasterName));
            DisSpinner selectedSpinner =  new DisSpinner();
            selectedSpinner.prepareDisasterDropdown(selectedDisaster);

            disDetailsBox.setText(result.getString(result.getColumnIndex(DisasterSchema.Tableinfo.disasterDescription)));
            countryBox.setText(result.getString(result.getColumnIndex(DisasterSchema.Tableinfo.country)));
            regionBox.setText(result.getString(result.getColumnIndex(DisasterSchema.Tableinfo.region)));
            disEmergencyBox.setText(result.getString(result.getColumnIndex(DisasterSchema.Tableinfo.emergency)));
        }
    }

    private void updateDisaster() {
        boolean cancel = validateForm();
        if (!cancel) {
            boolean correctUpdate = myDB.updatedata(idHidden, disNameBox,
                    disDetailsBox.getText().toString(), countryBox.getText().toString(),
                    regionBox.getText().toString(), disEmergencyBox.getText().toString());

            if (correctUpdate) {
                Toast.makeText(this, "Record updated", Toast.LENGTH_LONG).show();
            }
        }

    }

    private boolean validateForm() {

        boolean cancel = false;
        View focusView = null;

        // Store values at the time of the login attempt.

        String disDetail = disDetailsBox.getText().toString();
        String country = countryBox.getText().toString();
        String region = regionBox.getText().toString();
        String disEmergency = disEmergencyBox.getText().toString();

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(disDetail)) {
            disDetailsBox.setError(getString(R.string.error_field_required));
            focusView = disDetailsBox;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(country)) {
            countryBox.setError(getString(R.string.error_field_required));
            focusView = countryBox;
            cancel = true;
        }
        if (TextUtils.isEmpty(region)) {
            regionBox.setError(getString(R.string.error_field_required));
            focusView = regionBox;
            cancel = true;
        }
        if (TextUtils.isEmpty(disEmergency)) {
            disEmergencyBox.setError(getString(R.string.error_field_required));
            focusView = disEmergencyBox;
            cancel = true;
        }
        if (TextUtils.isEmpty(disNameBox)) {
            /*disNameBox.setError(getString(R.string.error_field_required));
            focusView = new View(disNameBox);*/
            /*Snackbar.make(new View(), "Name of disaster is mandatory.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }
        return cancel;
    }

    private void disasterHintSpinner() {
        List<String> disasterList = Arrays.asList(getResources().getStringArray(R.array.disaster_array));
        spinner = (Spinner) findViewById(R.id.dis_name_spnr);

        defaultHintSpinner = new HintSpinner<>(
                spinner,
                // Default layout - You don't need to pass in any layout id, just your hint text and
                // your list data
                new HintAdapter<>(this, R.string.default_spinner_hint, disasterList),
                new HintSpinner.Callback<String>() {
                    @Override
                    public void onItemSelected(int position, String itemAtPosition) {
                        // Here you handle the on item selected event (this skips the hint selected
                        // event)
                        disNameBox = itemAtPosition;
                    }
                });
        defaultHintSpinner.init();
    }

    private class DisSpinner implements AdapterView.OnItemSelectedListener {

        public void prepareDisasterDropdown(String selectedDisaster) {
        List<String> disasterList =  new ArrayList<>();
            disasterList.add(selectedDisaster);
            // Spinner element
            spinner = (Spinner) findViewById(R.id.dis_name_spnr);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(DisasterActivity.this,
                    android.R.layout.simple_spinner_item, disasterList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setEnabled(false);
            /*// Spinner click listener
            spinner.setOnItemSelectedListener(this);*/
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
          // Nothing has to be done.
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // Nothing has to be done.
        }
    }

}
