package com.parousia.idelbrain.db;

import com.parousia.idelbrain.common.IdleBrainApplicationConstants;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseManager {
	private MySQLiteHelper sqliteHelper;
	private SQLiteDatabase idleBrainDBWrite, idleBrainDBRead;

	public DatabaseManager(Context context) {
		sqliteHelper = new MySQLiteHelper(context);
		openWritableDB();
		openReadableDB();
	}

	public Cursor getValues(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {

		return idleBrainDBRead.query(table, columns, selection, selectionArgs,
				groupBy, having, orderBy);
	}

	public DatabaseManager openWritableDB() {
		idleBrainDBWrite = sqliteHelper.getWritableDatabase();
		return this;
	}

	public DatabaseManager openReadableDB() {
		idleBrainDBRead = sqliteHelper.getReadableDatabase();
		return this;
	}

	public void closeDB() {
		sqliteHelper.close();
	}

	public long insertValues(ContentValues values, String tableName) {
		return idleBrainDBWrite.insert(tableName, null, values);
	}

	public int deleteValues(String table, String whereClause, String[] whereArgs) {
		return idleBrainDBWrite.delete(table, whereClause, whereArgs);
	}

	public long updateValues(ContentValues values, String tableName,
			String whereClause, String[] whereArgs) {
		return idleBrainDBWrite.update(tableName, values, whereClause, whereArgs);
	}

	public Cursor executeRawSql(String sql) {
		return idleBrainDBRead.rawQuery(sql, null);
	}

	public Cursor getLastId(String tableName, String idCol) {
		String[] columns = { "MAX(" + idCol + ")" };
		return idleBrainDBRead.query(tableName, columns, null, null, null, null,
				null);
	}
}

class MySQLiteHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "idlebrain.db";

	MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		System.out.println("ACCESSING DATABASE............");
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		System.out.println("OPENING DATABASE.......");
		super.onOpen(db);

		System.out.println("DATABASE UPDATE...");
		Log.d(IdleBrainApplicationConstants.LOGTAG, "DATABASE UPDATE...");

		this.createTables(db);

		System.out.println("DATABASE HAS BEEN UPDATED SUCCESSFULLY");
		Log.d(IdleBrainApplicationConstants.LOGTAG,
				"DATABASE HAS BEEN UPDATED SUCCESSFULLY");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("CREATING DATABASE.......");
		Log.d(IdleBrainApplicationConstants.LOGTAG, "CREATING DATABASE.......");

		this.createTables(db);

		System.out.println("DATABASE HAS BEEN CREATED SUCCESSFULLY");
		Log.d(IdleBrainApplicationConstants.LOGTAG,
				"DATABASE HAS BEEN CREATED SUCCESSFULLY");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.setVersion(newVersion);
	}

	private void createTables(SQLiteDatabase db) {
		db.execSQL(IdleBrainApplicationConstants.CREATE_HEROINES_TB);
		db.execSQL(IdleBrainApplicationConstants.CREATE_HEROINES_LINKS_TB);
	}

}
