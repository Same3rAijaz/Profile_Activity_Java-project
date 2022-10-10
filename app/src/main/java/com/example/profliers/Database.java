package com.example.profliers;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;


import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Database extends SQLiteOpenHelper {

    public static final String DB_NAME = "profiledb";
    public static final String TABLE_NAME = "profile_table";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "profile_id";
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    public static final int DB_VERSION = 1;

    public  Database(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
            String sttmnt1 = "CREATE TABLE IF NOT EXISTS profile_table (profile_id INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT,Surname TEXT NOT NULL,Name TEXT NOT NULL,Student_id TEXT NOT NULL,GPA TEXT NOT NULL,Creation_Database TEXT NOT NULL);";
            String sttmnt2 = "CREATE TABLE IF NOT EXISTS activity_table (access_id INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT,Access_type TEXT NOT NULL,time_spam TEXT NOT NULL,profile_id INTEGER NOT NULL,FOREIGN KEY(profile_id) REFERENCES profile_table(profile_id));";
            db.execSQL(sttmnt1);
            db.execSQL(sttmnt2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String sql1 = "DROP TABLE IF EXISTS profile_table";
            String sql2 = "DROP TABLE IF EXISTS activity_table";
            db.execSQL(sql1);
            db.execSQL(sql2);
            onCreate(db);
    }

    public boolean addtoactivity(String Access_type,Integer profile_id){
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("profile_id",profile_id);
            contentValues.put("Access_type",Access_type);
            contentValues.put("profile_id",profile_id);
            contentValues.put("time_spam",dtf.format(now).toString());
            return (sqLiteDatabase.insert("activity_table",null,contentValues) != -1);
    };


    public boolean addtoprofile(String Surname, String Name, String Student_id, String GPA){
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Surname",Surname);
        contentValues.put("Name",Name);
        contentValues.put("Student_id",Student_id);
        contentValues.put("GPA",GPA);
        contentValues.put("Creation_Database", dtf.format(now).toString());
        if(sqLiteDatabase.insert("profile_table",null,contentValues) != -1){
            String sqle = "SELECT last_insert_rowid() FROM profile_table;";
            Cursor c = sqLiteDatabase.rawQuery(sqle,null);
            c.moveToFirst();
            int id = c.getInt(0);
            id += 1;
            addtoactivity("Created",id);
        }
        return true;
    }
     Cursor getProfiles(){
        String sql = "SELECT * FROM profile_table;";
        Cursor cursor = null;
        SQLiteDatabase sqLiteDatabase =this.getReadableDatabase();
        if (sqLiteDatabase !=null){
            cursor =sqLiteDatabase.rawQuery(sql,null);
        }
        return cursor;
    }
    Cursor getProfileActivity(Integer profileid){
        String sql = "SELECT * FROM activity_table WHERE profile_id="+ profileid + ";";
        Cursor cursor = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        if(sqLiteDatabase !=null){
            cursor = sqLiteDatabase.rawQuery(sql,null);
        }
        return cursor;
    }
    Cursor getProfileact(Integer profileid){
        String sql = "SELECT * FROM profile_table WHERE profile_id="+ profileid + ";";
        Cursor cursor = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        if(sqLiteDatabase !=null){
            cursor = sqLiteDatabase.rawQuery(sql,null);
        }
        return cursor;
    }
}
