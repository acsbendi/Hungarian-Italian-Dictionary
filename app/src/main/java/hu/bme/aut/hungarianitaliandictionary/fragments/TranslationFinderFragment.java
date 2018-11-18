package hu.bme.aut.hungarianitaliandictionary.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import hu.bme.aut.hungarianitaliandictionary.data.HungarianWord;
import hu.bme.aut.hungarianitaliandictionary.data.ItalianWord;

import static hu.bme.aut.hungarianitaliandictionary.data.TranslationDirection.*;

public class TranslationFinderFragment extends TranslationDirectionSettableFragment {

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
                onSearchButtonClick();
            }
        });

        initTranslationDirectionSwitchButton(rootView);

        this.rootView = rootView;

        return rootView;
    }

    private void onSearchButtonClick(){
        String searchText = searchEditText.getText().toString();
        if(selectedTranslationDirection == HUNGARIAN_TO_ITALIAN)
            loadItalianTranslationsInBackground(searchText);
        else if(selectedTranslationDirection == ITALIAN_TO_HUNGARIAN)
            loadHungarianTranslationsInBackground(searchText);
    }

    @SuppressWarnings("unchecked")
    private void loadItalianTranslationsInBackground(String hungarianWord) {
        initItalianRecyclerView();

        List<ItalianWord> italianTranslations = activity.findItalianTranslationsFor(hungarianWord);

        for(ItalianWord italianWord : italianTranslations)
            adapter.addItem(italianWord);
    }


    private void initItalianRecyclerView() {
        adapter = new TranslationAdapter<>(activity.getNewItalianWordChangeListener());
        initRecyclerView();
    }


    @SuppressWarnings("unchecked")
    private void loadHungarianTranslationsInBackground(String italianWord) {
        initHungarianRecyclerView();

        List<HungarianWord> hungarianTranslations = activity.findHungarianTranslationsFor(italianWord);

        for(HungarianWord hungarianWord : hungarianTranslations)
            adapter.addItem(hungarianWord);
    }

    private void initHungarianRecyclerView() {
        adapter = new TranslationAdapter<>(activity.getNewHungarianWordChangeListener());
        initRecyclerView();
    }

    private void initRecyclerView(){
        recyclerView = rootView.findViewById(R.id.MainRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);
    }
}
