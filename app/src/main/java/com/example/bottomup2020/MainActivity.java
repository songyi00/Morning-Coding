package com.example.bottomup2020;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    String name;
    String email;
    String num;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
   /* public String showDatabase(Cursor cursor){
        while(cursor.moveToNext()){
            id = cursor.getInt(0);
            name= cursor.getString(1);
            email = cursor.getString(2);
            num= cursor.getString(3);
        }
        return id+" "+name+" "+email+" "+num;
    }*/
}


class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "MorningCoding";
    public static final String TABLE_NAME= "userProfile";
    public static final String COL_1="ID";
    public static final String COL_2="name";
    public static final String COL_3="email";
    public static final String COL_4="number";

    public DBHelper(@Nullable Context context){
        super(context,DATABASE_NAME,null,2);

    }

    //실행할 때 테이블 최초 생성
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " +TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT , email TEXT, number TEXT)");
    }

    //버전 업그레이드
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //테이블에 정보 넣기
    public boolean insertData(String name, String email, String number){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,email);
        contentValues.put(COL_4,number);
        long result = db.insert(TABLE_NAME,null,contentValues);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    //선택한 데이터만 조회
    public Cursor getOneData(String email){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME+ " where email='"+email+"'",null);
        return res;
    }

    //데이터 전체 조회
    public Cursor getAllData(){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME ,null);
        return res;
    }


    //데이터 수정
    public boolean updateData(String id,String name,String email,String number){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,email);
        contentValues.put(COL_4,number);
        db.update(TABLE_NAME,contentValues,"ID=?",new String[]{id});
        return true;
    }
    //테이블 삭제
    public int deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID=?",new String[] {id});
    }


}