package com.androqlite.offlinedemo.sqlite;

public class DbConstants {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String COLUMN_NAME_NULLABLE = null;
    public static final String _ID = "_id";
    public static final String _COUNT = "_count";

	/*###########################*/
    /*########## Message ########*/
	/*###########################*/

    public static final String TABLE_MESSAGE = "message";

    public static final String MESSAGE_COLUMN_M_TITLE = "m_title";
    public static final String MESSAGE_COLUMN_M_MESSAGE = "m_message";
    public static final String MESSAGE_COLUMN_M_DATE_TIME = "m_date_time";
    public static final String MESSAGE_COLUMN_M_FROM = "m_from";

    public static final String SQL_CREATE_MESSAGE_ENTRIES =
            "CREATE TABLE " + TABLE_MESSAGE + " (" +
                    _ID + " INTEGER PRIMARY KEY,"
                    + MESSAGE_COLUMN_M_TITLE + TEXT_TYPE + COMMA_SEP +
                    MESSAGE_COLUMN_M_MESSAGE + TEXT_TYPE + COMMA_SEP +
                    MESSAGE_COLUMN_M_DATE_TIME + TEXT_TYPE + COMMA_SEP +
                    MESSAGE_COLUMN_M_FROM + TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_MESSAGE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_MESSAGE;

	/*------------------------------*/


}