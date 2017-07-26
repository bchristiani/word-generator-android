package de.christiani.benjamin.wordgen.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.christiani.benjamin.wordgen.R;
import de.christiani.benjamin.wordgen.common.form.TextInputElement;
import de.christiani.benjamin.wordgen.common.form.TextInputElementWatcher;

public class InputActivity extends AppCompatActivity {

    public static final String KEY_PREF_ALPHABET = "pref_alphabet";
    public static final String KEY_PREF_WORD_SIZE = "pref_word_size";
    private static final int TIME_INTERVAL = 2000;
    private boolean doubleBackToQuitPressedOnce = false;
    private ProgressDialog progressDialog;
    private Handler handler;
    private TextInputElement tieAlphabet, tieWordSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.initInputFields();
        this.loadSavedPreferences();
        this.progressDialog = this.createProgressDialog();
        this.handler = new Handler();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_search);
        fab.setOnClickListener((view) -> {

            if(this.isInputValid()) {
                this.progressDialog.show();
                this.handler.postDelayed(() -> {
                    this.progressDialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), ResultActivity.class));
                }, TIME_INTERVAL);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_input, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return true;
            case R.id.action_app_info:
                startActivity(new Intent(getApplicationContext(), InfoActivity.class));
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

    @Override
    protected void onPause() {
        super.onPause();
        final SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putString(KEY_PREF_ALPHABET, this.tieAlphabet.getInput());
        editor.putString(KEY_PREF_WORD_SIZE, this.tieWordSize.getInput());
        editor.apply();
    }

    private void initInputFields() {
        final EditText etAlphabet = (EditText) findViewById(R.id.et_alphabet);
        final TextInputLayout tilAlphabet = (TextInputLayout) findViewById(R.id.til_alphabet);
        this.tieAlphabet = new TextInputElement(getString(R.string.et_error_alphabet), etAlphabet, tilAlphabet);
        etAlphabet.addTextChangedListener(new TextInputElementWatcher(tieAlphabet));

        final EditText etWordSize = (EditText) findViewById(R.id.et_word_size);
        final TextInputLayout tilWordSize = (TextInputLayout) findViewById(R.id.til_word_size);
        this.tieWordSize = new TextInputElement(getString(R.string.et_error_word_size), etWordSize, tilWordSize);
        etWordSize.addTextChangedListener(new TextInputElementWatcher(tieWordSize));
    }

    private void loadSavedPreferences() {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String alphabetValue = prefs.getString(KEY_PREF_ALPHABET, StringUtils.EMPTY);
        final String wordSizeValue = prefs.getString(KEY_PREF_WORD_SIZE, StringUtils.EMPTY);
        if(!StringUtils.isEmpty(alphabetValue)) this.tieAlphabet.setInput(alphabetValue);
        if(!StringUtils.isEmpty(wordSizeValue)) this.tieWordSize.setInput(wordSizeValue);
    }

    private boolean isInputValid() {
        final List<TextInputElement> elementList = new ArrayList<TextInputElement>() {
            {
                add(tieAlphabet);
                add(tieWordSize);
            }
        };

        final Optional<TextInputElement> firstInvalidElement = elementList.stream()
                .filter(e -> !e.isValid())
                .findFirst();

        if(firstInvalidElement.isPresent()) {
            elementList.stream()
                    .filter(e -> !e.isValid())
                    .forEach(TextInputElement::showValidationError);
            firstInvalidElement.get().setFocus();
            return false;
        }
        return true;
    }

    private ProgressDialog createProgressDialog() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.progress_dialog_message));
        return progressDialog;
    }
}
