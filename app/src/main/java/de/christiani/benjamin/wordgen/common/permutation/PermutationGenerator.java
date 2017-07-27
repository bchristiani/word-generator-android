package de.christiani.benjamin.wordgen.common.permutation;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;


public class PermutationGenerator {
    private String string;
    private ArrayList<String> permutations;
    private int limit;

    public PermutationGenerator(@NonNull String string, int limit) {
        this.string = string;
        this.limit = limit;
        this.permutations = new ArrayList<>();
    }

    public ArrayList<String> getPermutations() {
        permutation(StringUtils.EMPTY, this.string);
        return this.permutations;
    }

    private void permutation(String prefix, String str) {
        final int n = str.length();
        if(this.permutations.size() == limit) return;
        if (n == 0) {
            this.permutations.add(prefix);
        } else {
            for (int i = 0; i < n; i++) {
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
            }
        }
    }
}
