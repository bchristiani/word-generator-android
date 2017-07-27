package de.christiani.benjamin.wordgen.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import de.christiani.benjamin.wordgen.R;
import de.christiani.benjamin.wordgen.common.form.FormInputData;
import de.christiani.benjamin.wordgen.common.permutation.PermutationGenerator;
import static de.christiani.benjamin.wordgen.fragments.SettingsFragment.KEY_PREF_LIMIT;


public class ResultFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_result, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Intent intent = getActivity().getIntent();
        final Bundle bundle = intent.getExtras();
        final FormInputData formInputData = (FormInputData) bundle.getSerializable(getString(R.string.key_bundle_form_input_data));
        if(formInputData == null) {
            throw new IllegalStateException("Could not serialize form input data from bundle.");
        }

        final String resultLimit = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(KEY_PREF_LIMIT, null);
        if( resultLimit == null) {
            throw new IllegalStateException("Could not find preference key " + KEY_PREF_LIMIT + " in shared preferences.");
        }

        final PermutationGenerator generator = new PermutationGenerator(formInputData.getAlphabet(), Integer.parseInt(resultLimit));
        final ArrayList<String> results = generator.getPermutations();
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar!=null) actionBar.setTitle(results.size() + " " + getString(R.string.toolbar_title_result));

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, results);
        setListAdapter(adapter);
    }
}
