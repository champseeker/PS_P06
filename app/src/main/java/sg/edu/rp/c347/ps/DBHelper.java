package sg.edu.rp.c347.ps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "tasks.db";

    private static final String TABLE_TASK = "task";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_TASK +  "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT )";
        db.execSQL(createTableSql);
        Log.i("info" ,"created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);

        onCreate(db);

    }

    public void insertTask(String name, String description){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        db.insert(TABLE_TASK, null, values);
        db.close();

    }

    public String getLastTask() {

        String name = "";

        String selectQuery = "SELECT " + COLUMN_NAME
                + " FROM " + TABLE_TASK;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToLast()) {
            name = cursor.getString(0);
        }

        cursor.close();
        db.close();

        return name;
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        String selectQuery = "SELECT " + COLUMN_ID + ", "
                + COLUMN_NAME + ", "
                + COLUMN_DESCRIPTION
                + " FROM " + TABLE_TASK;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                Task obj = new Task(id, name, description);
                tasks.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }

}
