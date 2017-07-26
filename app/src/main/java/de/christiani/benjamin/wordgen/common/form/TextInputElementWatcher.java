package de.christiani.benjamin.wordgen.common.form;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import org.apache.commons.lang3.StringUtils;

/**
 * Ein konkreter {@link TextWatcher} für ein {@link TextInputElement}. Validiert die Eingabe und
 * blendet einen Hinweistext ein oder aus.
 *
 * @author bchristiani
 */
public class TextInputElementWatcher implements TextWatcher {

    private TextInputElement element;

    public TextInputElementWatcher(@NonNull final TextInputElement element) {
        this.element = element;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        final boolean valid = !StringUtils.isEmpty(s.toString());
        if (valid) {
            this.element.hideValidationError();
        } else {
            this.element.showValidationError();
        }
        this.element.setValid(valid);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

}
