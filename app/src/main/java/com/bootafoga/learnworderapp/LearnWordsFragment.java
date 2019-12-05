package com.bootafoga.learnworderapp;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.Random;


public class LearnWordsFragment extends Fragment implements View.OnClickListener {

    private boolean empty;
    private boolean type;
    private int bound;

    private OnFragmentInteractionListener mListener;
    private TextView word, translate;
    DatabaseConnection connect;
    private Cursor cursor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null){
            type = bundle.getBoolean("type");
            empty = bundle.getBoolean("empty");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_learn_words, container, false);
        ImageButton showTranslate, checkAnswer, nextWord;

        checkAnswer = (ImageButton)layout.findViewById(R.id.check_answer_button);
        nextWord = (ImageButton)layout.findViewById(R.id.next_word_button);
        showTranslate = (ImageButton)layout.findViewById(R.id.show_answer_button);

        checkAnswer.setOnClickListener(this);
        nextWord.setOnClickListener(this);;
        showTranslate.setOnClickListener(this);

        word = (TextView)layout.findViewById(R.id.textview_word);
        translate = (TextView) layout.findViewById(R.id.textview_translate);

        connect = new DatabaseConnection(inflater.getContext());
        cursor = connect.getCursorAllNotLearned();

        if (!empty) {
            bound = connect.getCountNotLearned();
            cursor.moveToFirst();
            onClickNextWord();
        }

        return layout;
    }

    interface OnFragmentInteractionListener {
        void onFragmentInteraction(int answer);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }

    public static LearnWordsFragment newInstance(boolean type, boolean empty) {
        LearnWordsFragment learnWordsFragment = new LearnWordsFragment();
        Bundle args = new Bundle();
        args.putBoolean("type", type);
        args.putBoolean("empty", empty);
        learnWordsFragment.setArguments(args);
        return learnWordsFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_answer_button:
                onClickShowTranslate();
                break;
            case R.id.check_answer_button:
                onClickCheckTranslate();
                break;
            case R.id.next_word_button:
                onClickNextWord();
                break;
        }
    }


    private void onClickCheckTranslate(){
        if (empty) return;
        String userInput;
        userInput = translate.getText().toString();

        if (!type){

            if (userInput.trim().equalsIgnoreCase(cursor.getString(1)))
                //pic.setImageResource(imageTrue);
                mListener.onFragmentInteraction(1);
            else //pic.setImageResource(imageFalse);
                mListener.onFragmentInteraction(2);
        } else {
            Log.d("TAG", "HUI3");
            if (userInput.trim().equalsIgnoreCase(cursor.getString(2)))
                //pic.setImageResource(imageTrue);
                mListener.onFragmentInteraction(1);
            else //pic.setImageResource(imageFalse);
                mListener.onFragmentInteraction(2);
        }
    }

    private void onClickShowTranslate() {
        if (empty) return;
        if (!type)
            translate.setText(cursor.getString(1));
        else
            translate.setText(cursor.getString(2));
    }

    private void onClickNextWord(){
        if (empty) return;
        cursor.moveToFirst();
        mListener.onFragmentInteraction(0);
        translate.setText("");
        Random r = new Random();

        int position = r.nextInt(bound);
        int current = 0;

        while (current != position){
            cursor.moveToNext();
            current++;
        }

        if (type)
            word.setText(cursor.getString(1));
        else
            word.setText(cursor.getString(2));
    }
}
