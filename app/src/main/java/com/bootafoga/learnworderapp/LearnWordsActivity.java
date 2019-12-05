package com.bootafoga.learnworderapp;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;

public class LearnWordsActivity extends AppCompatActivity implements LearnWordsFragment.OnFragmentInteractionListener {

    private Cursor cursor;
    private SQLiteDatabase db;
    boolean empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_words);
        getSupportActionBar().setHomeButtonEnabled(true);

        DatabaseConnection connect = new DatabaseConnection(this);
        db = connect.getDbReadable();
        cursor = connect.getCursorAllNotLearned();

        if (!cursor.moveToLast()){
            AllertMessage();
            empty = true;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        LearnWordsFragment learnWordsFragment;
        learnWordsFragment = LearnWordsFragment.newInstance(true, empty);
        ft.replace(R.id.fragment_container, learnWordsFragment);
        ft.commit();
    }

    private void AllertMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getLayoutInflater().getContext());
        builder.setTitle(R.string.attention)
                .setMessage(Html.fromHtml("<font color='#4E4E4E'>Ваш словарь пуст, ничего выведено не будет. Вернитесь на главную страницу и добавьте слова</font>"))
                //.setMessage(R.string.empty_base)
                .setCancelable(false)
                .setNegativeButton(R.string.OK,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onFragmentInteraction(int answer) {
        ImageView imageView = findViewById(R.id.answerImageView);

        switch (answer){
            case 0: imageView.setImageResource(0); break;
            case 1: imageView.setImageResource(R.drawable.buttontrue); break;
            case 2: imageView.setImageResource(R.drawable.buttonfalse); break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        cursor.close();
    }
}
