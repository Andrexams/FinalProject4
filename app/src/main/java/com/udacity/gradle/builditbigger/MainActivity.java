package com.udacity.gradle.builditbigger;

import com.udacity.gradle.builditbigger.rest.MyApiRequest;
import com.udacity.gradle.builditbigger.utils.NetworkUtils;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.martins.jokedisplaylib.JokeActivity;


public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private TextView mTextViewError;
    private RelativeLayout mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mTextViewError = (TextView) findViewById(R.id.tv_error_message_display);
        mFragment = (RelativeLayout) findViewById(R.id.fragment);
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
        if (NetworkUtils.isConnectOnNetwork(this)) {
            mProgressBar.setVisibility(View.VISIBLE);

            final MyApiRequest request = new MyApiRequest(this);
            request.setDelegate(new MyApiRequest.MyApiResponseHandler() {
                @Override
                public void onResponse(String result) {
                    if (result != null) {
                        showJokeActivity(result);
                    } else {
                        showErrorMessage(getString(R.string.error_msg));
                    }
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            });
            request.execute(MyApiRequest.apiMethods.doJoke.name());

        } else {
            showErrorMessage(getString(R.string.no_network_msg));
        }
    }

    private void showJokeActivity(String joke) {
        Intent intent = new Intent(this, JokeActivity.class);
        intent.putExtra(JokeActivity.JOKE_TEXT_EXTRA,joke);
        startActivity(intent);
    }

    private void showErrorMessage(String message){
        mTextViewError.setVisibility(View.VISIBLE);
        mTextViewError.setText(message);
        showSnackRetry();
    }
    private void hideErrorMessage(){
        mTextViewError.setVisibility(View.INVISIBLE);
        mTextViewError.setText("");
    }

    private void showSnackRetry(){
        Snackbar snackbar = Snackbar
                .make(mFragment, null, Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hideErrorMessage();
                        tellJoke(view);
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }
}
