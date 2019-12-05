package com.bootafoga.learnworderapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AddWordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    public void onClickAddWord(View view) {
        new AddNewWord().execute();
    }

    private class AddNewWord extends AsyncTask<Void, Void, Boolean>{

        String usersWord;
        String usersTranslate;
        SQLiteDatabase db;

        protected void onPreExecute() {
            TextView word = (TextView)findViewById(R.id.textview_word);
            TextView translate = (TextView)findViewById(R.id.textview_translate);
            usersWord = word.getText().toString();
            usersTranslate = translate.getText().toString();

            word.setText("");
            translate.setText("");

            DatabaseConnection connect = new DatabaseConnection(AddWordActivity.this);
            db = connect.getDbWritable();
        }

        protected Boolean doInBackground(Void ... voids) {

            if (!usersWord.trim().equals("") && !usersTranslate.trim().equals("")){
                ContentValues newWord = new ContentValues();
                newWord.put("WORD", usersWord);
                newWord.put("TRANSLATE", usersTranslate);
                newWord.put("FLAG", 0);
                db.insert("DICTIONARY", null, newWord);
                return true;
            } else {
                return false;
            }
        }

        protected void onPostExecute(Boolean success) {
            Toast toast;
            if (success) toast = Toast.makeText(AddWordActivity.this, R.string.done, Toast.LENGTH_SHORT);
            else toast = Toast.makeText(AddWordActivity.this, R.string.incorrect_input, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
