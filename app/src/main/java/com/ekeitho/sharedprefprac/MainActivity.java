package com.ekeitho.sharedprefprac;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity implements View.OnClickListener{

    private TextView view;
    private EditText editTextKey, editTextValue;
    private Button getButton, storeButton, clearButton;
    private SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();

        //set listeners
        getButton.setOnClickListener(this);
        storeButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);


    }

    void initialize() {
        //get views
        view = (TextView) findViewById(R.id.textView);
        editTextKey = (EditText) findViewById(R.id.editTextKey);
        editTextValue = (EditText) findViewById(R.id.editTextValue);
        getButton = (Button) findViewById(R.id.getButton);
        storeButton = (Button) findViewById(R.id.storeButton);
        clearButton = (Button) findViewById(R.id.clearButton);
        //initiate shared preferences
        pref = this.getSharedPreferences("com.ekeitho.sharedprefprac", MODE_PRIVATE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch( v.getId() ) {
            case R.id.getButton:
                view.setText(pref.getString(
                        editTextKey.getText().toString(), "error. no key match."));
                /* if user clicks retrieve the erase if anything in value edit text */
                editTextValue.setText("");
                break;

            case R.id.storeButton:
                pref.edit().putString(editTextKey.getText().toString(),
                                editTextValue.getText().toString()).apply();
                break;

            case R.id.clearButton:
                pref.edit().clear().apply();
                view.setText("Shared Preferences \n Deleted.");
                break;
        }

    }

}
