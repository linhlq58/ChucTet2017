package com.tetstudio.linhlee.chuctet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tetstudio.linhlee.chuctet.models.ContentText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by lequy on 1/18/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "";
    private static String DB_NAME ="data_newyear_2015.sqlite";
    private static int DB_VERSION = 1;

    private Context context;
    private SQLiteDatabase mDataBase;

    private static String TABLE_TET = "tet";
    private static String KEY_ID = "tet_id";
    private static String KEY_CATEGORY_ID = "tet_category_id";
    private static String KEY_CONTENT = "tet_content";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;

        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TET_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_TET + " ("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_CATEGORY_ID + " INTEGER, "
                + KEY_CONTENT + " TEXT)";

        db.execSQL(CREATE_TET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TET);

        this.onCreate(db);
    }

    public void addFavorite(ContentText content) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, content.getId());
        values.put(KEY_CATEGORY_ID, content.getCategoryId());
        values.put(KEY_CONTENT, content.getContent());

        db.insert(TABLE_TET, null, values);

        db.setTransactionSuccessful();
        db.endTransaction();

    }

    public ArrayList<ContentText> getAllFavorite() {
        ArrayList<ContentText> listFavorite = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_TET;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                ContentText content = new ContentText();
                content.setId(cursor.getInt(0));
                content.setCategoryId(cursor.getInt(1));
                content.setContent(cursor.getString(2));

                listFavorite.add(content);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return listFavorite;
    }

    public void deleteFavorite(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_TET, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public boolean isFavorite(int id) {
        ArrayList<ContentText> listFavorite = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_TET
                + " WHERE tet_id = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                ContentText content = new ContentText();
                content.setId(cursor.getInt(0));
                content.setCategoryId(cursor.getInt(1));
                content.setContent(cursor.getString(2));

                listFavorite.add(content);
            } while (cursor.moveToNext());
        }

        cursor.close();

        if (listFavorite.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void createDataBase() throws IOException {
        //If the database does not exist, copy it from the assets.

        boolean mDataBaseExist = checkDataBase();
        if(!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                //Copy the database from assests
                copyDataBase();
                //Log.e(TAG, "createDatabase database created");
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() throws IOException {
        InputStream mInput = context.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    //Open the database, so we can query it
    public boolean openDataBase() throws SQLException {
        String mPath = DB_PATH + DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);

        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if(mDataBase != null)
            mDataBase.close();
        super.close();
    }
}
