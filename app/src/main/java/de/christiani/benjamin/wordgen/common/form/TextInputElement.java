package de.christiani.benjamin.wordgen.common.form;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;

/**
 * Wrapper Klasse, welche ein {@link EditText}- und {@link TextInputLayout}-Referenz hält.
 * Die Klasse kapselt einige nützliche Funktionen wie das Ein- und Ausblenden von Hinweistexten und
 * der kompletten Komponente.
 *
 * @author benjamin christiani
 */
public class TextInputElement {

    private boolean valid;
    private String errorText;
    private EditText editText;
    private TextInputLayout textInputLayout;

    public TextInputElement(@NonNull final String errorText,
                            @NonNull final EditText editText,
                            @NonNull final TextInputLayout textInputLayout) {
        this.errorText = errorText;
        this.editText = editText;
        this.textInputLayout = textInputLayout;
    }

    public void showElement() {
        this.editText.setVisibility(View.VISIBLE);
        this.textInputLayout.setVisibility(View.VISIBLE);
    }

    public void hideElement() {
        this.editText.setVisibility(View.GONE);
        this.textInputLayout.setVisibility(View.GONE);
    }

    public void hideValidationError() {
        this.textInputLayout.setError(null);
        this.textInputLayout.setErrorEnabled(false);
    }

    public void showValidationError() {
        this.textInputLayout.setErrorEnabled(true);
        this.textInputLayout.setError(this.errorText);
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public String getInput() {
        return this.editText.getText().toString();
    }

    public void setInput(final String text) {
        this.editText.setText(text);
    }

    public void setFocus() {
        this.editText.requestFocus();
    }
}
