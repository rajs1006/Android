package app.com.alertme.db.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import app.com.alertme.db.schemas.DisasterSchema;
import app.com.alertme.db.schemas.Schema;
import app.com.alertme.db.schemas.UserSchema;

/**
 * Created by 21701234 on 10/10/2017.
 */

public class Helper extends SQLiteOpenHelper {

    private static final String USER_TABLE_CREATE =
            "create table " + UserSchema.Tableinfo.Table_Name + "(" + UserSchema.Tableinfo.userId + " integer primary key autoincrement, "
                    + UserSchema.Tableinfo.userName + " text, "
                    + UserSchema.Tableinfo.emailId + " text, "
                    + UserSchema.Tableinfo.password + " text, "
                    + UserSchema.Tableinfo.phone + " text, "
                    + UserSchema.Tableinfo.emergencyContact1 + " text, "
                    + UserSchema.Tableinfo.emergencyContact2 + " text, "
                    + UserSchema.Tableinfo.country + " text, "
                    + UserSchema.Tableinfo.region + " text ) ";

    private static final String DISASTER_TABLE_CREATE =
            "create table " + DisasterSchema.Tableinfo.Table_Name + "(" + DisasterSchema.Tableinfo.disasterId + " integer primary key autoincrement, "
                    + DisasterSchema.Tableinfo.disasterName + " text, "
                    + DisasterSchema.Tableinfo.disasterDescription + " text, "
                    + DisasterSchema.Tableinfo.country + " text, "
                    + DisasterSchema.Tableinfo.region + " text, "
                    + DisasterSchema.Tableinfo.emergency + " text )";

    public Helper(Context context) {
        super(context, Schema.Tableinfo.DB_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DISASTER_TABLE_CREATE);
        db.execSQL(USER_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DisasterSchema.Tableinfo.Table_Name);
        db.execSQL("DROP TABLE IF EXISTS " + UserSchema.Tableinfo.Table_Name);
        //drop current table
        //receate table based on the schema
        //populate with new data
    }
}
