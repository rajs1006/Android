package app.com.alertme.db.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import app.com.alertme.db.schemas.DisasterSchema;

/**
 * Created by 21701234 on 10/6/2017.
 */

public class DisasterHelper extends Helper {


    public DisasterHelper(Context context) {
        super(context);
    }

    public boolean insertdata(String name, String details, String country, String region, String emergencyContact)

    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DisasterSchema.Tableinfo.disasterName, name);
        cv.put(DisasterSchema.Tableinfo.disasterDescription, details);
        cv.put(DisasterSchema.Tableinfo.country, country);
        cv.put(DisasterSchema.Tableinfo.region, region);
        cv.put(DisasterSchema.Tableinfo.emergency, emergencyContact);
        Long result = db.insert(DisasterSchema.Tableinfo.Table_Name, null, cv);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updatedata(String disasterId, String name, String details, String country, String region, String emergencyContact)

    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DisasterSchema.Tableinfo.disasterName, name);
        cv.put(DisasterSchema.Tableinfo.disasterDescription, details);
        cv.put(DisasterSchema.Tableinfo.country, country);
        cv.put(DisasterSchema.Tableinfo.region, region);
        cv.put(DisasterSchema.Tableinfo.emergency, emergencyContact);
        int result = db.update(DisasterSchema.Tableinfo.Table_Name, cv, DisasterSchema.Tableinfo.disasterId + "=" + disasterId, null);

        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor getDisastersByCountryAndRegion(String country, String region) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] passColumn = {DisasterSchema.Tableinfo.disasterId, DisasterSchema.Tableinfo.disasterName, DisasterSchema.Tableinfo.disasterDescription,
                DisasterSchema.Tableinfo.country, DisasterSchema.Tableinfo.region, DisasterSchema.Tableinfo.emergency};

        String selectionArgument = DisasterSchema.Tableinfo.country + " = '" + country + "' AND " + DisasterSchema.Tableinfo.region + " = '" + region + "'";
        String orderBy = DisasterSchema.Tableinfo.disasterId + " Desc ";

        return db.query(DisasterSchema.Tableinfo.Table_Name, passColumn, selectionArgument, null, null, null, orderBy, "4");

    }

    public Cursor getDisastersById(String disasterId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] passColumn = {DisasterSchema.Tableinfo.disasterId, DisasterSchema.Tableinfo.disasterName, DisasterSchema.Tableinfo.disasterDescription,
                DisasterSchema.Tableinfo.country, DisasterSchema.Tableinfo.region, DisasterSchema.Tableinfo.emergency};

        String selectionArgument = DisasterSchema.Tableinfo.disasterId + " = '" + disasterId + "'";
        String orderBy = DisasterSchema.Tableinfo.disasterId + " Desc ";

        return db.query(DisasterSchema.Tableinfo.Table_Name, passColumn, selectionArgument, null, null, null, null);

    }
}
