package com.tetstudio.linhlee.chuctet.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.tetstudio.linhlee.chuctet.models.ContentText;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by lequy on 1/18/2017.
 */

public class DatabaseAdapter {
    private Context mContext;
    private SQLiteDatabase mDb;
    private DatabaseHelper mDbHelper;

    public DatabaseAdapter(Context mContext) {
        this.mContext = mContext;
        mDbHelper = new DatabaseHelper(mContext);
    }

    public DatabaseAdapter createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        }
        catch (IOException mIOException) {
            throw new Error("UnableToCreateDatabase");
        }

        return this;
    }

    public DatabaseAdapter open() throws SQLException {
        try {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public ArrayList<ContentText> getContentTextByCategory(int categoryId) {
        ArrayList<ContentText> listContent = new ArrayList<>();

        String query = "SELECT * FROM sms WHERE catId = " + categoryId;

        Cursor cursor = mDb.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                ContentText contentText = new ContentText();
                contentText.setId(cursor.getInt(0));
                contentText.setCategoryId(cursor.getInt(1));
                contentText.setContent(cursor.getString(2));

                listContent.add(contentText);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return listContent;
    }

    public void addFavorite(ContentText content) {
        mDbHelper.addFavorite(content);
    }

    public ArrayList<ContentText> getAllFavorite() {
        return mDbHelper.getAllFavorite();
    }

    public void deleteFavorite(int id) {
        mDbHelper.deleteFavorite(id);
    }

    public boolean isFavorite(int id) {
        return mDbHelper.isFavorite(id);
    }

}
