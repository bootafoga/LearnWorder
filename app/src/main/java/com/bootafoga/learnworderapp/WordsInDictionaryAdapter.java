package com.bootafoga.learnworderapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class WordsInDictionaryAdapter extends RecyclerView.Adapter<WordsInDictionaryAdapter.ViewHolder> {

    private LinkedList<String> wordsInDictionary;
    private LinkedList<String> translatesInDictionary;
    private Listener listener;

    interface Listener {
        void onClick(int position);
    }

    @Override
    public int getItemCount(){
        return wordsInDictionary.size();
    }

    @Override
    public WordsInDictionaryAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.words_card, parent, false);
        return new ViewHolder(cv);
    }

    public WordsInDictionaryAdapter(LinkedList<String> words, LinkedList<String> translates){
        this.wordsInDictionary = words;
        this.translatesInDictionary = translates;
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //Определить представление для каждого элемента данных
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        CardView cardView = holder.cardView;

        TextView textView = (TextView)cardView.findViewById(R.id.wordInDictionary1);
        textView.setText(wordsInDictionary.get(position));

        TextView textView2 = (TextView)cardView.findViewById(R.id.translateInDictionary1);
        textView2.setText(translatesInDictionary.get(position));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }
}

