package de.christiani.benjamin.wordgen.common.form;


import android.support.annotation.NonNull;

import java.io.Serializable;

public class FormInputData implements Serializable{

    private String alphabet;
    private String wordSize;

    public FormInputData(@NonNull String alphabet, @NonNull String wordSize) {
        this.alphabet = alphabet;
        this.wordSize = wordSize;
    }

    public String getAlphabet() {
        return alphabet;
    }

    public String getWordSize() {
        return wordSize;
    }
}
