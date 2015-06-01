package com.erlantzoniga.androidnanodegree;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
