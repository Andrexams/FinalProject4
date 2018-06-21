package com.udacity.gradle.builditbigger;

import com.udacity.gradle.builditbigger.rest.MyApiRequest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import br.com.martins.jokedisplaylib.JokeActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        MyApiRequest request = new MyApiRequest(this);
        request.setDelegate(new MyApiRequest.MyApiResponseHandler() {
            @Override
            public void onResponse(String result) {
                showJokeActivity(result);
            }
        });
        request.execute(MyApiRequest.apiMethods.doJoke.name());
    }

    private void showJokeActivity(String joke) {
        Intent intent = new Intent(this, JokeActivity.class);
        intent.putExtra(JokeActivity.JOKE_TEXT_EXTRA,joke);
        startActivity(intent);
    }
}
