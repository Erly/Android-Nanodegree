package com.erlantzoniga.androidnanodegree;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void selectMediaStreamer(View v) {
        Toast.makeText(getApplicationContext(), R.string.launch_media_streamer, Toast.LENGTH_SHORT).show();
    }

    public void selectScores(View v) {
        Toast.makeText(getApplicationContext(), R.string.launch_scores, Toast.LENGTH_SHORT).show();
    }

    public void selectLibrary(View v) {
        Toast.makeText(getApplicationContext(), R.string.launch_library, Toast.LENGTH_SHORT).show();
    }

    public void selectBuildItBigger(View v) {
        Toast.makeText(getApplicationContext(), R.string.launch_build_bigger, Toast.LENGTH_SHORT).show();
    }

    public void selectReader(View v) {
        Toast.makeText(getApplicationContext(), R.string.launch_reader, Toast.LENGTH_SHORT).show();
    }

    public void selectCapstone(View v) {
        Toast.makeText(getApplicationContext(), R.string.launch_capstone, Toast.LENGTH_SHORT).show();
    }
}
