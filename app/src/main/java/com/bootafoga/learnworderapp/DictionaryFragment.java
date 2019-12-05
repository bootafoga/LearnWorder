package com.bootafoga.learnworderapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;


public class DictionaryFragment extends DialogFragment {

    private SQLiteDatabase db;
    private Cursor cursor;
    private LinkedList<String> wordsInDictionary = new LinkedList<>();
    private LinkedList<String> translatesInDictionary = new LinkedList<>();
    private String type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null){
            type = bundle.getString("type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView wordsRecycler = (RecyclerView)inflater.inflate(
                R.layout.fragment_dictionary, container, false);

        DatabaseConnection connect = new DatabaseConnection(inflater.getContext());
        db = connect.getDbReadable();
        if (type.equalsIgnoreCase("not_learned")) cursor = connect.getCursorAllNotLearned();
        else cursor = connect.getCursorAllLearned();

        while (cursor.moveToNext()){
            if (cursor != null) {
                wordsInDictionary.add(cursor.getString(1));
                translatesInDictionary.add(cursor.getString(2));
            }
        }

        WordsInDictionaryAdapter adapter = new WordsInDictionaryAdapter(wordsInDictionary, translatesInDictionary);
        wordsRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        wordsRecycler.setLayoutManager(layoutManager);

        adapter.setListener(new WordsInDictionaryAdapter.Listener() {
            public void onClick(int position) {
                changeWord(position);
            }
        });
        return wordsRecycler;
    }

    public void changeWord(int position){
        DialogFragment newFragment = new ChangeCardDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("type", type);
        newFragment.setArguments(bundle);
        newFragment.show(getFragmentManager(), "missiles");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
