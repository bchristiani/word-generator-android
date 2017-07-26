package de.christiani.benjamin.wordgen.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import de.christiani.benjamin.wordgen.R;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) actionBar.hide();
    }
}
