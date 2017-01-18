package example.aakash.samplecollector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dellpc on 17-Dec-16.
 */


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SampleProjectManager";
    private static final String TABLE_CODES = "sprojects";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "date";
    private static final String KEY_LOC = "location";
    private static final String KEY_DESCR = "descr";
    private static final String KEY_SAMPS = "samples";

    private static final String TAG = "ProjectsDB";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance  
    }

    // Creating Tables  
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CODES_TABLE = "CREATE TABLE " + TABLE_CODES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_DATE + " TEXT," + KEY_LOC + " TEXT," + KEY_DESCR + " TEXT," + KEY_SAMPS + " TEXT" + ")";
        db.execSQL(CREATE_CODES_TABLE);
        Log.d(TAG, "DB Created");
    }

    // Upgrading database  
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed  
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CODES);

        // Create tables again  
        onCreate(db);
        Log.d(TAG, "DB Upgraded");
    }


    // code to add the new contact  
    void addCodeFormat(ProjectFormat contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // CodeFormat Code
        values.put(KEY_DATE, contact.getDate()); // CodeFormat Response
        values.put(KEY_LOC, contact.getLocation());
        values.put(KEY_DESCR, contact.getDescription());
        values.put(KEY_SAMPS, contact.getSamples());
        // Inserting Row  
        db.insert(TABLE_CODES, null, values);
        //2nd argument is String containing nullColumnHack  
        db.close(); // Closing database connection

        Log.d(TAG, "DB Item added");
    }

    // code to get the single contact  
    ProjectFormat getCodeFormat(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CODES, new String[]{KEY_ID,
                        KEY_NAME, KEY_DATE, KEY_LOC, KEY_DESCR, KEY_SAMPS}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ProjectFormat project = new ProjectFormat();
        try {
            project.setName((cursor.getString(1)));
            //  project.setCode(cursor.getString(1));
            //  project.setResponse(cursor.getString(2));
            // return contact
        } catch (Exception e) {
            e.printStackTrace();
        }
        return project;
    }

    // code to get all CODES in a list view
    public List<ProjectFormat> getAllprojFormats() {
        List<ProjectFormat> contactList = new ArrayList<ProjectFormat>();
        // Select All Query  
        String selectQuery = "SELECT  * FROM " + TABLE_CODES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list  
        if (cursor.moveToFirst()) {
            do {
                ProjectFormat contact = new ProjectFormat();
                contact.setName(cursor.getString(1));
                contact.setDate(cursor.getString(2));
                contact.setLocation(cursor.getString(3));
                contact.setDescription(cursor.getString(4));
                contact.setSamples(Integer.parseInt(cursor.getString(5)));
                // Adding contact to list  
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        Log.d(TAG, "DB returned all values");
        // return contact list  
        return contactList;
    }

    // code to update the single contact  
    public int updateCodeFormat(ProjectFormat contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        //   values.put(KEY_RESPONSE, contact.getResponse());

        //   Log.d(TAG, "ID: " + contact.getId());
        //   Log.d(TAG, "CODE: " + contact.getCode());
        //   Log.d(TAG, "RESP: " + contact.getResponse());

        Log.d(TAG, "DB  row updated");
        // updating row  
        return db.update(TABLE_CODES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getName())});
    }

    // Deleting single contact  
    public void deleteCodeFormat(ProjectFormat contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CODES, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getName())});
        Log.d(TAG, "DB item deleted");
        db.close();
    }

    // Getting contacts Count  
    public int getCodeFormatsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CODES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count  
        return cursor.getCount();
    }

} 