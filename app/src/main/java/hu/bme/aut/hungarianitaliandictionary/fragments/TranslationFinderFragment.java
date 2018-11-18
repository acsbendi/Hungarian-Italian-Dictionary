package hu.bme.aut.hungarianitaliandictionary.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.List;

import hu.bme.aut.hungarianitaliandictionary.MainActivity;
import hu.bme.aut.hungarianitaliandictionary.R;
import hu.bme.aut.hungarianitaliandictionary.adapter.TranslationAdapter;
import hu.bme.aut.hungarianitaliandictionary.data.ItalianWord;

public class TranslationFinderFragment extends Fragment {

    public static String TAG = "TranslationFinderFragment";

    private EditText searchEditText;
    private RecyclerView recyclerView;
    private TranslationAdapter adapter;
    private MainActivity activity;
    private View rootView;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.activity = (MainActivity)context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = View.inflate(getContext(), R.layout.fragment_translation_finder, null);

        ImageButton searchButton = rootView.findViewById(R.id.searchButton);
        searchEditText = rootView.findViewById(R.id.searchEditText);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadItalianTranslationsInBackground(searchEditText.getText().toString());
            }
        });

        this.rootView = rootView;

        return rootView;
    }

    private void initRecyclerView() {
        recyclerView = rootView.findViewById(R.id.MainRecyclerView);
        adapter = new TranslationAdapter<>(activity.getNewItalianWordChangeListener());
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);
    }

    @SuppressWarnings("unchecked")
    private void loadItalianTranslationsInBackground(String hungarianWord) {
        initRecyclerView();

        List<ItalianWord> italianTranslations = activity.findItalianTranslationsFor(hungarianWord);

        for(ItalianWord italianWord : italianTranslations)
            adapter.addItem(italianWord);
    }
}
