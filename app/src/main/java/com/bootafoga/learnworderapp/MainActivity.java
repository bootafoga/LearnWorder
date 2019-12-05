package com.bootafoga.learnworderapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String EXTRA_MESSAGE = "num_page";
    TextView progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        progress = findViewById(R.id.progress);
        new ProgressTask().execute(this);
    }


    public void onClickLearnButton(View view) {
        Intent intent = new Intent(this, LearnWordsActivity.class);
        startActivity(intent);
    }

    public void onClickDictionaryButton(View view) {
        Intent intent = new Intent(this, DictionaryActivity.class);
        intent.putExtra(EXTRA_MESSAGE, 0);
        startActivity(intent);
    }


    class ProgressTask extends AsyncTask<Context, Context, Context> {
        private DatabaseConnection connect;

        @Override
        protected Context doInBackground(Context... context) {
            connect = new DatabaseConnection(context[0]);
            return(context[0]);
        }
        @Override
        protected void onProgressUpdate(Context... items) {

        }
        @Override
        protected void onPostExecute(Context unused) {
            progress.setText("Уже выучено слов: " + connect.getCountLearned());

        }
    }
}
