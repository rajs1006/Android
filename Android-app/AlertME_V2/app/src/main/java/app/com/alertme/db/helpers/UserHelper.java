package app.com.alertme.db.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import app.com.alertme.db.schemas.UserSchema;

public class UserHelper extends Helper {


    public UserHelper(Context context) {
        super(context);
    }

    public boolean insertdata(String name, String email, String password, String phone, String emergencyContact1, String emergencyContact2, String country, String region) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UserSchema.Tableinfo.userName, name);
        cv.put(UserSchema.Tableinfo.emailId, email);
        cv.put(UserSchema.Tableinfo.password, password);
        cv.put(UserSchema.Tableinfo.phone, phone);
        cv.put(UserSchema.Tableinfo.emergencyContact1, emergencyContact1);
        cv.put(UserSchema.Tableinfo.emergencyContact2, emergencyContact2);
        cv.put(UserSchema.Tableinfo.country, country);
        cv.put(UserSchema.Tableinfo.region, region);
        Long result = db.insert(UserSchema.Tableinfo.Table_Name, null, cv);


        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updatedata(String userId, String name, String email, String password, String phone, String emergencyContact1, String emergencyContact2, String country, String region) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UserSchema.Tableinfo.userName, name);
        cv.put(UserSchema.Tableinfo.emailId, email);
        cv.put(UserSchema.Tableinfo.password, password);
        cv.put(UserSchema.Tableinfo.phone, phone);
        cv.put(UserSchema.Tableinfo.emergencyContact1, emergencyContact1);
        cv.put(UserSchema.Tableinfo.emergencyContact2, emergencyContact2);
        cv.put(UserSchema.Tableinfo.country, country);
        cv.put(UserSchema.Tableinfo.region, region);
        int result = db.update(UserSchema.Tableinfo.Table_Name, cv, UserSchema.Tableinfo.userId + "=" + userId, null);


        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor getUserByEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] passColumn = {UserSchema.Tableinfo.userId, UserSchema.Tableinfo.userName, UserSchema.Tableinfo.emailId, UserSchema.Tableinfo.password,
                UserSchema.Tableinfo.phone, UserSchema.Tableinfo.emergencyContact1, UserSchema.Tableinfo.emergencyContact2,
                UserSchema.Tableinfo.country, UserSchema.Tableinfo.region};

        return db.query(UserSchema.Tableinfo.Table_Name, passColumn, UserSchema.Tableinfo.emailId + " = '" +
                email + "'", null, null, null, null);
    }

    public Cursor getRowCountByEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] passColumn = {UserSchema.Tableinfo.userId};

        return db.query(UserSchema.Tableinfo.Table_Name, passColumn, UserSchema.Tableinfo.emailId + " = '" +
                email + "'", null, null, null, null);
    }
}
