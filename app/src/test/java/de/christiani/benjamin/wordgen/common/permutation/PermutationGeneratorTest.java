package de.christiani.benjamin.wordgen.common.permutation;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Unit Tests für die Klasse {@link PermutationGenerator}.
 *
 * @author benjamin christiani.
 */
public class PermutationGeneratorTest {

    private final String ALPHABET = "abc";
    private final int RESULT_LIMIT = 500;

    @Test
    public void generateAllPermutations() throws Exception {
        final String [] permutations = {"abc","acb","bac","bca","cab","cba"};
        final PermutationGenerator generator = new PermutationGenerator(ALPHABET, RESULT_LIMIT);
        final List<String> result = generator.getPermutations();
        assertThat(result.size(), is(6));
        assertThat(StringUtils.join(result,","), is(StringUtils.join(permutations,",")));
    }

    @Test
    public void resultLimitIsLowerThanResultSize() throws Exception {
        final String [] permutations = {"abc","acb","bac"};
        final PermutationGenerator generator = new PermutationGenerator(ALPHABET, 3);
        final List<String> result = generator.getPermutations();
        assertThat(result.size(), is(3));
        assertThat(StringUtils.join(result,","), is(StringUtils.join(permutations,",")));
    }
}