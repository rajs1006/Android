package app.com.alertme.db.schemas;

import android.provider.BaseColumns;

/**
 * Created by 21701234 on 20/09/2017.
 */

public class UserSchema extends Schema {
    public UserSchema() {

    }


    public static abstract class Tableinfo implements BaseColumns {
        public static final String Table_Name = "User_Details";
        public static final String userId = "userId";
        public static final String userName = "userName";
        public static final String emailId = "emailId";
        public static final String password = "password";
        public static final String phone = "phone";
        public static final String emergencyContact1 = "emergencyContact1";
        public static final String emergencyContact2 = "emergencyContact2";
        public static final String country = "country";
        public static final String region = "region";

    }
}
