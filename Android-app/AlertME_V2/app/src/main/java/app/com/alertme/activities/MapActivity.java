package app.com.alertme.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import app.com.alertme.R;

public class MapActivity extends AppCompatActivity {

    private final static Integer PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        placePicker();
    }


    private void placePicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST); // for activty
            //startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST); // for fragment
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*checkPermissionOnActivityResult(requestCode, resultCode, data);*/

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    Place place = PlacePicker.getPlace(this, data);
                    CharSequence address = place.getAddress();
                    String[] addressArray = null;
                    if (null != address) {
                        addressArray = address.toString().split(",");
                    }

                    String country = addressArray[addressArray.length - 1];
                    String region = addressArray[addressArray.length - 2];

                    Intent intent = returnIntent();
                    intent.putExtra("country", country);
                    intent.putExtra("region", region);
                    startActivity(intent);

            }
        }

    }

    @NonNull
    private Intent returnIntent() {
        String mapRedirect = getIntent().getStringExtra("mapRedirect");
        String mEmailView = getIntent().getStringExtra("userEmail");
        Intent intent;
        if ("user".equals(mapRedirect)) {
            intent = new Intent(MapActivity.this, UserActivity.class);
        } else if ("disaster".equals(mapRedirect)) {
            intent = new Intent(MapActivity.this, DisasterActivity.class);
            intent.putExtra("userEmail", mEmailView);
        } else {
            intent = new Intent();
        }
        return intent;
    }
}
