package app.com.alertme.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import app.com.alertme.R;
import app.com.alertme.db.helpers.DisasterHelper;
import app.com.alertme.db.helpers.UserHelper;
import app.com.alertme.db.schemas.UserSchema;

import static app.com.alertme.db.schemas.DisasterSchema.Tableinfo.emergency;

public class EmergencyActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    UserHelper myUserDB;
    DisasterHelper myDisasterDB;

    EditText localEmergencyContactBox, emergencyContact1Box, emergencyContact2Box;
    Button localEmergencyCallButton, emergencyCallButton1, emergencyCallButton2;
    TextView homeLink;

    String latestDisasterIdView;
    String mEmailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        mEmailView = getIntent().getStringExtra("userEmail");
        latestDisasterIdView = getIntent().getStringExtra("latestDisasterId");

        localEmergencyContactBox = (EditText) findViewById(R.id.localemergencycontact);
        emergencyContact1Box = (EditText) findViewById(R.id.emergencycontact1);
        emergencyContact2Box = (EditText) findViewById(R.id.emergencycontact2);

        // Populate emergency numbers.
        setUserDetails();
        setDisasterDetails();

        homeLink = (TextView) findViewById(R.id.email_home_button);
        homeLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emergencyPageIntent = new Intent(EmergencyActivity.this, HomeActivity.class);
                emergencyPageIntent.putExtra("userEmail", mEmailView);
                startActivity(emergencyPageIntent);
            }
        });

        localEmergencyCallButton = (Button) findViewById(R.id.localemergencybutton);
        localEmergencyCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall(view, Uri.parse("tel:" + localEmergencyContactBox.getText().toString()));
            }
        });

        emergencyCallButton1 = (Button) findViewById(R.id.emergencybutton1);
        emergencyCallButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall(view, Uri.parse("tel:" + emergencyContact1Box.getText().toString()));
            }

        });

        emergencyCallButton2 = (Button) findViewById(R.id.emergencybutton2);
        emergencyCallButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall(view, Uri.parse("tel:" + emergencyContact2Box.getText().toString()));
            }
        });
        // Disable Fieilds.
        localEmergencyContactBox.setEnabled(false);
        if(TextUtils.isEmpty(localEmergencyContactBox.getText().toString())){
            localEmergencyCallButton.setEnabled(false);
        }

        emergencyContact1Box.setEnabled(false);
        if(TextUtils.isEmpty(emergencyContact1Box.getText().toString())){
            emergencyCallButton1.setEnabled(false);
        }

        emergencyContact2Box.setEnabled(false);
        if(TextUtils.isEmpty(emergencyContact2Box.getText().toString())){
            emergencyCallButton2.setEnabled(false);
        }
    }

    private void callPhone(Uri parse) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(parse);
        try {
            startActivity(callIntent);
        } catch (SecurityException se) {

        }
    }

    private void setUserDetails() {
        myUserDB = new UserHelper(this);
        Cursor result = myUserDB.getUserByEmail(mEmailView);

        while (result.moveToNext()) {
            emergencyContact1Box.setText(result.getString(result.getColumnIndex(UserSchema.Tableinfo.emergencyContact1)));
            emergencyContact2Box.setText(result.getString(result.getColumnIndex(UserSchema.Tableinfo.emergencyContact2)));
        }
    }
    private void setDisasterDetails() {
        myDisasterDB = new DisasterHelper(this);
        Cursor result = myDisasterDB.getDisastersById(latestDisasterIdView);

        while (result.moveToNext()) {
            localEmergencyContactBox.setText(result.getString(result.getColumnIndex(emergency)));
            break;
        }
    }


    public void makePhoneCall(View view, Uri telNumber) {

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            callPhone(telNumber);
        }
    }
}
