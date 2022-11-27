package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c =null;

    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context){
        if(instance==null){
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open(){
        this.db=openHelper.getWritableDatabase();
    }

    public void close(){
        if(db!=null){
            this.db.close();
        }
    }

    public String getElevator(String station){
        c=db.rawQuery("select DISTINCT elevator from myFacility where start='"+station+"'",new String[]{});
        StringBuffer buffer = new StringBuffer();

        while(c.moveToNext()){
            String elevator = c.getString(0);
            buffer.append(""+elevator);
        }
        return buffer.toString();
    }
    public String getToilet(String station){
        c=db.rawQuery("select DISTINCT toilet from myFacility where start='"+station+"'",new String[]{});
        StringBuffer buffer = new StringBuffer();

        while(c.moveToNext()){
            String elevator = c.getString(0);
            buffer.append(""+elevator);
        }
        return buffer.toString();
    }
    public String getAirCondition(String station){
        c=db.rawQuery("select DISTINCT temperature from myFacility where start='"+station+"'",new String[]{});
        StringBuffer buffer = new StringBuffer();

        while(c.moveToNext()){
            String elevator = c.getString(0);
            buffer.append(""+elevator);
        }
        return buffer.toString();
    }
    public String getWheelchair(String station){
        c=db.rawQuery("select DISTINCT wheelchair from myFacility where start='"+station+"'",new String[]{});
        StringBuffer buffer = new StringBuffer();

        while(c.moveToNext()){
            String elevator = c.getString(0);
            buffer.append(""+elevator);
        }
        return buffer.toString();
    }

    public boolean IsFacility(String station){ //올바른 역을 입력했는지를 확인하는 함수
        c=db.rawQuery("select start from myFacility where start= '"+station+"'",null);
        int count = c.getCount();
        if (count<=0){
            return false;
        } else{
            return true;
        }


    }

}
