package com.ekeitho.data;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;
import java.util.Random;

/**
 * Created by Keithmaynn on 9/5/14.
 */
public class TestDatabaseActivity extends ListActivity {

    private CommentsDataSource dataSource;
    private ArrayAdapter<Comment> adapter;

    EditText editText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sqlite_helper);
        editText = (EditText) findViewById(R.id.editText);

        dataSource = new CommentsDataSource(this);
        dataSource.open();

        List<Comment> values = dataSource.getAllComments();
        ArrayAdapter<Comment> adapterCreate =
                new ArrayAdapter<Comment>(this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapterCreate);

        adapter = (ArrayAdapter<Comment>) getListAdapter();
    }


    private void setLongClick() {
        this.getListView().setLongClickable(true);
        this.getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                Comment comment = (Comment) getListAdapter().getItem(position);
                dataSource.deleteComment(comment);
                adapter.remove(comment);
                return true;
            }
        });
    }




    public void onClick(View view) {
        Comment comment = null;
        switch( view.getId() ) {
            case R.id.add:
                comment = dataSource.createComment(editText.getText().toString());
                adapter.add(comment);
                editText.setText("");
                break;
            case R.id.send_data_button:
                Intent intent = new Intent(Intent.ACTION_SEND);
                Log.v("Text", editText.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT, editText.getText().toString());
                intent.setType("text/plain");
                Intent chooser = Intent.createChooser(intent, "Send data to");
                if( chooser.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        dataSource.open();
        setLongClick();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }
}
