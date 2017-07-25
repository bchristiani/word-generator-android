package de.christiani.benjamin.wordgen.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import de.christiani.benjamin.wordgen.R;

public class InputActivity extends AppCompatActivity {

    private static final int TIME_INTERVAL = 2000;
    private boolean doubleBackToQuitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_search);
        fab.setOnClickListener((view) -> startActivity(new Intent(getApplicationContext(), ResultActivity.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_input, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (this.doubleBackToQuitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToQuitPressedOnce = true;
        Toast.makeText(this, R.string.toast_quit_app, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(
                () -> doubleBackToQuitPressedOnce = false
                , TIME_INTERVAL);
    }
}
