package de.christiani.benjamin.wordgen.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

    private static final int TIME_INTERVAL = 2000;
    private boolean doubleBackToQuitPressedOnce = false;
    private ProgressDialog progressDialog;
    private Handler handler;
    private List<TextInputElement> textInputElements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.initInputFields();
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

    private void initInputFields() {
        this.textInputElements = new ArrayList<>();

        final EditText etAlphabet = (EditText) findViewById(R.id.et_alphabet);
        final TextInputLayout tilAlphabet = (TextInputLayout) findViewById(R.id.til_alphabet);
        final TextInputElement tieAlphabet = new TextInputElement(getString(R.string.et_error_alphabet), etAlphabet, tilAlphabet);
        etAlphabet.addTextChangedListener(new TextInputElementWatcher(tieAlphabet));
        this.textInputElements.add(tieAlphabet);

        final EditText etWordSize = (EditText) findViewById(R.id.et_word_size);
        final TextInputLayout tilWordSize = (TextInputLayout) findViewById(R.id.til_word_size);
        final TextInputElement tieWordSize = new TextInputElement(getString(R.string.et_error_word_size), etWordSize, tilWordSize);
        etWordSize.addTextChangedListener(new TextInputElementWatcher(tieWordSize));
        this.textInputElements.add(tieWordSize);
    }

    private boolean isInputValid() {

        final Optional<TextInputElement> firstInvalidElement = this.textInputElements.stream()
                .filter(e -> !e.isValid())
                .findFirst();

        if(firstInvalidElement.isPresent()) {
            this.textInputElements.stream()
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
