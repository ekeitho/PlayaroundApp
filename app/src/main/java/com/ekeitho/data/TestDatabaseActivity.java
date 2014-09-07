package com.ekeitho.data;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
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
    final Context context = this;

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
                CharSequence[] sequences = {"Delete", "Send to another app"};
                /* need to have final position for alert inner class */
                final int pos = position;

                AlertDialog.Builder alertDialogBuilder =
                        new AlertDialog.Builder(context);
                alertDialogBuilder
                        .setTitle("What would you like to do?")
                        .setSingleChoiceItems(sequences, 2, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /* delete */
                                if (which == 0) {
                                    Comment comment = (Comment) getListAdapter().getItem(pos);
                                    dataSource.deleteComment(comment);
                                    adapter.remove(comment);
                                } else {
                                    Intent intent = new Intent(Intent.ACTION_SEND);
                                    intent.putExtra(Intent.EXTRA_TEXT, editText.getText().toString());
                                    intent.setType("text/plain");
                                    Intent chooser = Intent.createChooser(intent, "Send data to");
                                    if (chooser.resolveActivity(getPackageManager()) != null) {
                                        startActivity(chooser);
                                    }
                                }
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .create().show();

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
