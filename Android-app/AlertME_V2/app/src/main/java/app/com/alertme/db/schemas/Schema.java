package app.com.alertme.db.schemas;

import android.provider.BaseColumns;

/**
 * Created by 21701234 on 10/10/2017.
 */

public class Schema {

    public Schema() {

    }


    public static abstract class Tableinfo implements BaseColumns {
        public static final String DB_Name = "AlertME.db";
    }
}
