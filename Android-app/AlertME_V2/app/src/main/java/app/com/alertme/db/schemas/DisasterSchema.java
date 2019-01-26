package app.com.alertme.db.schemas;

import android.provider.BaseColumns;

/**
 * Created by 21701234 on 10/6/2017.
 */

public class DisasterSchema extends  Schema{

    public DisasterSchema() {

    }


    public static abstract class Tableinfo implements BaseColumns {
        public static final String Table_Name = "Disaster_Details";
        public static final String disasterId = "disasterId";
        public static final String disasterName = "disasterName";
        public static final String disasterDescription = "disasterDescription";
        public static final String country = "country";
        public static final String region = "region";
        public static final String emergency = "emergency";

    }
}
