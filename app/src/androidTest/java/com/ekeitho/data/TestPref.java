package com.ekeitho.data;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

public class TestPref extends AndroidTestCase {

    private static final String LOG_TAG = TestPref.class.getSimpleName();

    public void testCreateDb() {
        mContext.deleteDatabase(MySQLiteHelper.TABLE_NAME);
        SQLiteDatabase db = new MySQLiteHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        db.close();
    }

    public void testInsertReadDB() {

        MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(this.mContext);
        SQLiteDatabase db = mySQLiteHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.CONTENT, "Keith you are the best!");
        long insertId = db.insert(MySQLiteHelper.TABLE_NAME, null, values);
        assertTrue(insertId != -1);

        Cursor commentCursor = db.query(
                MySQLiteHelper.TABLE_NAME,
                null, //returns all the columns
                null, //col for wheres
                null, // values for where if used ?
                null, // group by
                null, // filter by
                null //sort
        );

        assertTrue(commentCursor.moveToFirst());

        assertEquals(commentCursor.getString(1), "Keith you are the best!");
        assertEquals(insertId, commentCursor.getLong(0));
        commentCursor.close();


    }


}