package app.com.alertme.activities;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import app.com.alertme.R;

public class HelpActivity extends AppCompatActivity {

    Button emergencyLink;
    TextView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        emergencyLink = (Button) findViewById(R.id.earthquakeHelp);
        scrollView = (TextView)  findViewById(R.id.helpScrollView);
        emergencyLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AssetManager assetManager = getAssets();
                 // To load text file
                InputStream input;
                try {
                    input = assetManager.open("earthquakeHelp.txt");

                    int size = input.available();
                    byte[] buffer = new byte[size];
                    input.read(buffer);
                    input.close();

                    // byte buffer into a string
                    String text = new String(buffer);

                    scrollView.setText(text);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


        });

       emergencyLink = (Button) findViewById(R.id.tsunamiHelp);

        emergencyLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AssetManager assetManager = getAssets();
                // To load text file
                InputStream input;
                try {
                    input = assetManager.open("tsunamiHelp.txt");

                    int size = input.available();
                    byte[] buffer = new byte[size];
                    input.read(buffer);
                    input.close();

                    // byte buffer into a string
                    String text = new String(buffer);

                    scrollView.setText(text);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


        });

        emergencyLink = (Button) findViewById(R.id.fireHelp);

        emergencyLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AssetManager assetManager = getAssets();
                // To load text file
                InputStream input;
                try {
                    input = assetManager.open("fireHelp.txt");

                    int size = input.available();
                    byte[] buffer = new byte[size];
                    input.read(buffer);
                    input.close();

                    // byte buffer into a string
                    String text = new String(buffer);

                    scrollView.setText(text);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


        });

        emergencyLink = (Button) findViewById(R.id.stromHelp);

        emergencyLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AssetManager assetManager = getAssets();
                // To load text file
                InputStream input;
                try {
                    input = assetManager.open("stromHelp.txt");

                    int size = input.available();
                    byte[] buffer = new byte[size];
                    input.read(buffer);
                    input.close();

                    // byte buffer into a string
                    String text = new String(buffer);

                    scrollView.setText(text);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        });

        emergencyLink = (Button) findViewById(R.id.volcanicHelp);

        emergencyLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AssetManager assetManager = getAssets();
                // To load text file
                InputStream input;
                try {
                    input = assetManager.open("volcanicHelp.txt");

                    int size = input.available();
                    byte[] buffer = new byte[size];
                    input.read(buffer);
                    input.close();

                    // byte buffer into a string
                    String text = new String(buffer);

                    scrollView.setText(text);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        });

}}
