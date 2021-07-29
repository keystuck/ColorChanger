package com.example.android.colorchanger;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Display mDisplay;
    private float timeDown;
    private float timeUp;
    private float timePress;
    private int alph = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = (ImageView) findViewById(R.id.background);

        Spinner alphaSpinner = (Spinner) findViewById(R.id.alpha_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> alphaAdapter = ArrayAdapter.createFromResource(this,
                R.array.alpha_vals, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        alphaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        alphaSpinner.setAdapter(alphaAdapter);

        Spinner greenSpinner = (Spinner) findViewById(R.id.alpha_spinner);
        ArrayAdapter<CharSequence> greenAdapter = ArrayAdapter.createFromResource(this,
                R.array.alpha_vals, android.R.layout.simple_spinner_item);
        greenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        greenSpinner.setAdapter(greenAdapter);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == event.ACTION_DOWN) {
                    timeDown = System.currentTimeMillis() % 100000;
                } else if (event.getActionMasked() == event.ACTION_UP) {
                    timeUp = System.currentTimeMillis() % 100000;
                    timePress = timeUp - timeDown;
                    float x = (event.getX() - v.getLeft()) / (v.getRight() - v.getLeft());
                    float y = (event.getY() - v.getTop()) / (v.getBottom() - v.getTop());
                    changeColor((int) (x * 256), (int) (y * 256), (int) (timePress));
                }
                return true;
            }

        });
        mSensorManager= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null){

            mDisplay = getWindowManager().getDefaultDisplay();


        }
        else {
            addToText("not present");
        }
    }

    public void changeColor(int red, int blue, int green){
        int col0 = alph;
                //(int)(256*Math.random());
        int col1 = Math.abs(red % 256);
        int col2;
        if (green < 300){
            col2 = 0;
        } else if (green < 600){
            col2 = 96;
        } else if (green < 900){
            col2 = 192;
        }
        else {
            col2 = 255;
        }
        //(int) (256*Math.random());
        int col3 = Math.abs(blue % 256);
        int colorToUse = Color.argb(col0, col1, col2, col3);
        ImageView backIview = (ImageView)findViewById(R.id.background);
        backIview.setBackgroundColor(colorToUse);

//        int rot = mDisplay.getRotation();

        addToText("Red: " + Integer.toHexString(col1)
                + ", Green: " + Integer.toHexString(col2)
                + ", Blue: " + Integer.toHexString(col3)
                + ", Alpha: " + Integer.toHexString(col0) + "\n");

        alph += 64;
        if (alph == 256){
            alph -= 1;
        }
        else if (alph > 256){
            alph = 0;
        }

    }



    public void addToText(String txt){
        TextView colorText = (TextView) findViewById(R.id.colorCode);
        colorText.setText(txt);
    }


}
