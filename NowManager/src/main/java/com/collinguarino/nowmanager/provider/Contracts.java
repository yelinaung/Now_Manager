package com.collinguarino.nowmanager.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class Contracts {

    public static final class TimeCards implements BaseColumns {

        /**
         * The authority for the contacts provider
         */
        public static final String TABLE_NAME = "timeCards";
        /**
         * A uri to the authority for this table
         */
        public static final Uri CONTENT_URI = Uri.parse("content://" + NowManagerProvider.AUTHORITY + "/" + TABLE_NAME);
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/timeCards";
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/timeCard";

        public static final String C_EVENT_NAME_INPUT = "eventNameInput";
        public static final String C_TIMESTAMP = "timestamp";
        public static final String C_IS_TALLY = "isTally";
        public static final String C_IS_THIRD_PARTY = "isThirdParty";

        public static final int I_ID = 0;
        public static final int I_EVENT_NAME_INPUT = 1;
        public static final int I_TIMESTAMP = 2;
        public static final int I_IS_TALLY = 3;
        public static final int I_IS_THIRD_PARTY = 4;

        // A string that defines the SQL statement for creating a table
        static final String SQL_CREATE_MAIN = "CREATE TABLE " +
                "timeCards " +                       // Table's name
                "(" +                           // The columns in the table
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                C_EVENT_NAME_INPUT + " TEXT, " +
                C_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                C_IS_TALLY + " INTEGER DEFAULT 0, " + C_IS_THIRD_PARTY + " INTEGER DEFAULT 0)";

        // These are the Contacts rows that we will retrieve.
        public static final String[] SELECT_ALL_PROJECTION = new String[]{
                _ID,
                C_EVENT_NAME_INPUT,
                C_TIMESTAMP,
                C_IS_TALLY,
                C_IS_THIRD_PARTY
        };

        /**
         * Helper method to get a content values object for creating a new time card.
         *
         * @param eventNameInput Default text (Optional)
         * @param isTally        Is this a tally or a standard event.
         * @return Non-null object
         */
        public static ContentValues getInsertValues(final String eventNameInput, final boolean isTally, final boolean isThirdparty) {
            final ContentValues values = new ContentValues();
            if (eventNameInput != null) {
                values.put(C_EVENT_NAME_INPUT, eventNameInput);
            }
            values.put(C_TIMESTAMP, System.currentTimeMillis());
            values.put(C_IS_TALLY, isTally);
            values.put(C_IS_THIRD_PARTY, isThirdparty);
            return values;
        }

        /**
         * Helper method to build a content values object to update a time card.
         * @param eventNameInput event name text
         * @return Non-null object.
         */
        public static ContentValues getUpdateValues(final String eventNameInput) {
            final ContentValues values = new ContentValues();
            values.put(C_EVENT_NAME_INPUT, eventNameInput);
            return values;
        }

        /**
         * Helper method to get all of the time cards that are classified as a tally
         *
         * @return Cursor with all tally time cards.
         */
        public static Cursor getTallyTimeCards(final Context context) {
            final String[] projection = {_ID};
            final String selection = C_IS_TALLY + "=1";
            return context.getContentResolver().query(CONTENT_URI, projection, selection, null, null);
        }

        /**
         * Helper method to get the count of all time cards that are classified as a tally
         *
         * @return number of tally time cards.
         */
        public static int getTallyTimeCardCount(final Context context) {
            final Cursor cursor = getTallyTimeCards(context);
            if (cursor == null) {
                return 0;
            }
            return cursor.getCount();
        }
    }
}
