package br.com.martins.jokedisplaylib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    public static final String JOKE_TEXT_EXTRA = "JOKE_TEXT_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        Intent intent = getIntent();
        if(getIntent().hasExtra(JOKE_TEXT_EXTRA)){
            fill();
        }
    }

    private void fill() {
        TextView textViewJoke = (TextView) findViewById(R.id.joke_text);
        textViewJoke.setText(getIntent().getStringExtra(JOKE_TEXT_EXTRA));
    }
}
