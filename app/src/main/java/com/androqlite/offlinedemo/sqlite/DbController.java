package com.androqlite.offlinedemo.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.androqlite.offlinedemo.datamodels.MessageDataModel;

import java.util.ArrayList;

/**
 * Created by emran
 */

public class DbController {

    private SQLiteDatabase mDB;

    public DbController(Context context) {
        mDB = DbHelper.getInstance(context).getWritableDatabase();
    }

	/*###########################*/
    /*########## Message ########*/
	/*###########################*/

    public int insertMessageData(MessageDataModel messageDataModel) {
        ContentValues values = new ContentValues();
        values.put(DbConstants.MESSAGE_COLUMN_M_TITLE, messageDataModel.getMTitle());
        values.put(DbConstants.MESSAGE_COLUMN_M_MESSAGE, messageDataModel.getMMessage());
        values.put(DbConstants.MESSAGE_COLUMN_M_DATE_TIME, messageDataModel.getMDateTime());
        values.put(DbConstants.MESSAGE_COLUMN_M_FROM, messageDataModel.getMFrom());

        return (int) mDB.insert(
                DbConstants.TABLE_MESSAGE,
                DbConstants.COLUMN_NAME_NULLABLE, values);
    }

    public int updateMessageData(MessageDataModel messageDataModel) {
        ContentValues values = new ContentValues();
        values.put(DbConstants.MESSAGE_COLUMN_M_TITLE, messageDataModel.getMTitle());
        values.put(DbConstants.MESSAGE_COLUMN_M_MESSAGE, messageDataModel.getMMessage());
        values.put(DbConstants.MESSAGE_COLUMN_M_DATE_TIME, messageDataModel.getMDateTime());
        values.put(DbConstants.MESSAGE_COLUMN_M_FROM, messageDataModel.getMFrom());

        String selection = DbConstants.MESSAGE_COLUMN_M_TITLE + " = ?";
		/*
		String selection = DbConstants.MESSAGE_COLUMN_M_TITLE + " LIKE ?";
		*/
        String[] selectionArgs = {String.valueOf(messageDataModel.getMTitle())};

        return mDB.update(
                DbConstants.TABLE_MESSAGE,
                values,
                selection,
                selectionArgs
        );
    }

    public int deleteMessageData(String deleteId) {

        String selection = DbConstants.MESSAGE_COLUMN_M_TITLE + " = ?";

        String[] selectionArgs = {String.valueOf(deleteId)};

        return mDB.delete(
                DbConstants.TABLE_MESSAGE,
                selection,
                selectionArgs
        );
    }

    public ArrayList<MessageDataModel> getAllMessageData() {
        ArrayList<MessageDataModel> messageDataModelArrayList = new ArrayList<>();
        String[] projection = {
                DbConstants.MESSAGE_COLUMN_M_TITLE,
                DbConstants.MESSAGE_COLUMN_M_MESSAGE,
                DbConstants.MESSAGE_COLUMN_M_DATE_TIME,
                DbConstants.MESSAGE_COLUMN_M_FROM,
        };

        Cursor c = mDB.query(
                DbConstants.TABLE_MESSAGE,        // The table name to query
                projection,                        //
                null,                            // The columns to return
                null,                            // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                            // don't group the rows
                null                            // The sort order
        );

        if (c.moveToFirst()) {
            do {
                String m_title = c.getString(c.getColumnIndexOrThrow(DbConstants.MESSAGE_COLUMN_M_TITLE));
                String m_message = c.getString(c.getColumnIndexOrThrow(DbConstants.MESSAGE_COLUMN_M_MESSAGE));
                String m_date_time = c.getString(c.getColumnIndexOrThrow(DbConstants.MESSAGE_COLUMN_M_DATE_TIME));
                String m_from = c.getString(c.getColumnIndexOrThrow(DbConstants.MESSAGE_COLUMN_M_FROM));

                messageDataModelArrayList.add(new MessageDataModel(
                        m_title, m_message, m_date_time, m_from
                ));
            } while (c.moveToNext());

        }//End if

        return messageDataModelArrayList;
    }//End getAll MEthod

    public ArrayList<MessageDataModel> getAllMessageData(String valueId) {
        ArrayList<MessageDataModel> messageDataModelArrayList = new ArrayList<>();
        String[] projection = {
                DbConstants.MESSAGE_COLUMN_M_TITLE,
                DbConstants.MESSAGE_COLUMN_M_MESSAGE,
                DbConstants.MESSAGE_COLUMN_M_DATE_TIME,
                DbConstants.MESSAGE_COLUMN_M_FROM,
        };


        String whereClause = DbConstants.MESSAGE_COLUMN_M_TITLE + " = ?";

        String[] whereArgs = {String.valueOf(valueId)};
        Cursor c = mDB.query(
                DbConstants.TABLE_MESSAGE,        // The table name to query
                projection,                        //
                whereClause,                    // The columns to return
                whereArgs,                        // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                            // don't group the rows
                null                            // The sort order
        );

        if (c.moveToFirst()) {
            do {
                String m_title = c.getString(c.getColumnIndexOrThrow(DbConstants.MESSAGE_COLUMN_M_TITLE));
                String m_message = c.getString(c.getColumnIndexOrThrow(DbConstants.MESSAGE_COLUMN_M_MESSAGE));
                String m_date_time = c.getString(c.getColumnIndexOrThrow(DbConstants.MESSAGE_COLUMN_M_DATE_TIME));
                String m_from = c.getString(c.getColumnIndexOrThrow(DbConstants.MESSAGE_COLUMN_M_FROM));

                messageDataModelArrayList.add(new MessageDataModel(
                        m_title, m_message, m_date_time, m_from
                ));
            } while (c.moveToNext());

        }//End if

        return messageDataModelArrayList;
    }//End getAll MEthod

    public ArrayList<MessageDataModel> getAllMessageData(String fieldName, String valueId) {
        ArrayList<MessageDataModel> messageDataModelArrayList = new ArrayList<>();
        String[] projection = {
                DbConstants.MESSAGE_COLUMN_M_TITLE,
                DbConstants.MESSAGE_COLUMN_M_MESSAGE,
                DbConstants.MESSAGE_COLUMN_M_DATE_TIME,
                DbConstants.MESSAGE_COLUMN_M_FROM,
        };


        String whereClause = fieldName + " = ?";

        String[] whereArgs = {String.valueOf(valueId)};
        Cursor c = mDB.query(
                DbConstants.TABLE_MESSAGE,        // The table name to query
                projection,                        //
                whereClause,                    // The columns to return
                whereArgs,                        // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                            // don't group the rows
                null                            // The sort order
        );

        if (c.moveToFirst()) {
            do {
                String m_title = c.getString(c.getColumnIndexOrThrow(DbConstants.MESSAGE_COLUMN_M_TITLE));
                String m_message = c.getString(c.getColumnIndexOrThrow(DbConstants.MESSAGE_COLUMN_M_MESSAGE));
                String m_date_time = c.getString(c.getColumnIndexOrThrow(DbConstants.MESSAGE_COLUMN_M_DATE_TIME));
                String m_from = c.getString(c.getColumnIndexOrThrow(DbConstants.MESSAGE_COLUMN_M_FROM));

                messageDataModelArrayList.add(new MessageDataModel(
                        m_title, m_message, m_date_time, m_from
                ));
            } while (c.moveToNext());

        }//End if

        return messageDataModelArrayList;
    }//End getAll MEthod

	/*###############################*/
	/*########## End Message ########*/
	/*###############################*/


    public void closeDB() {
        if (mDB != null && mDB.isOpen())
            mDB.close();
    }


    /*################################*/
	/*########## Delete Table ########*/
	/*################################*/
    public void deleteTbls(String tableName) {
        mDB.delete(tableName, null, null);
    }
}
