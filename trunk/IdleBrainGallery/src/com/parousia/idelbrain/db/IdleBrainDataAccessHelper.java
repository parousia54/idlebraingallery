package com.parousia.idelbrain.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Handler;

import com.parousia.idelbrain.common.IdleBrainApplicationConstants;
import com.parousia.idlebrain.data.IdleBrainHeroine;
import com.parousia.idlebrain.data.IdleBrainHeroineLink;
import com.parousia.idlebrain.data.IdleBrainHeroineList;

public class IdleBrainDataAccessHelper {

	public static int getTypeID(String table, String name,
			DatabaseManager dbManager) {
		Cursor cursor = dbManager.getValues(table, null, "name='" + name + "'",
				null, null, null, null);
		cursor.moveToFirst();
		return cursor.getInt(cursor.getColumnIndex("_id"));
	}

	public static ArrayList<IdleBrainHeroine> getHeroinesListFromDB(
			DatabaseManager dbManager) {
		ArrayList<IdleBrainHeroine> heroineList = new ArrayList<IdleBrainHeroine>();
		Cursor cursor = getAllHeroinesCursor(dbManager);

		while (cursor.moveToNext()) {
			IdleBrainHeroine heroine = new IdleBrainHeroine();
			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			heroine.setId(id);
			heroine.setName(cursor.getString(cursor.getColumnIndex("name")));
			ArrayList<IdleBrainHeroineLink> propertyList = getHeroineLinksFromCursor(getHeroineLinksByHeroineId(
					dbManager, id));
			heroineList.add(heroine);
		}
		return heroineList;
	}

	private static Cursor getAllHeroinesCursor(DatabaseManager dbManager) {
		return dbManager.getValues(
				IdleBrainApplicationConstants.TABLE_HEROINES, null, null, null,
				null, null, null);
	}

	public static Cursor getHeroineLinksByHeroineId(DatabaseManager dbManager,
			int id) {
		return dbManager.getValues(
				IdleBrainApplicationConstants.CREATE_HEROINES_LINKS_TB, null,
				"heroine_id=" + id, null, null, null, null);
	}

	public static ArrayList<IdleBrainHeroineLink> getHeroineLinksFromCursor(
			Cursor cursor) {
		ArrayList<IdleBrainHeroineLink> heroineLinksList = new ArrayList<IdleBrainHeroineLink>();
		while (cursor.moveToNext()) {
			IdleBrainHeroineLink heroineLinks = new IdleBrainHeroineLink();
			heroineLinks
			.setName(cursor.getString(cursor.getColumnIndex("name")));
			heroineLinks.setUrl(cursor.getString(cursor.getColumnIndex("url")));
			heroineLinksList.add(heroineLinks);
		}
		return heroineLinksList;
	}

	public static boolean updateHeroineDatabase(
			IdleBrainHeroineList idleBrainHeroineList,
			DatabaseManager dbManager, Handler handler) {

		System.out.println("UPDATING PRODUCT DATABASE................");
		if (cleanDatabase(dbManager)) {
			cleanDatabase(dbManager);
			List<IdleBrainHeroine> heroineList = idleBrainHeroineList
					.getIdleBrainHeroinesList();
			System.out.println("UPDATING PRODUCT TABLE................");
			updateTableHeroines(dbManager, heroineList, handler);

			System.out
			.println("UPDATING PRODUCT DATABASE COMPLETED................");
			return true;
		} else {
			return false;
		}
	}

	private static boolean updateTableHeroines(DatabaseManager dbManager,
			List<IdleBrainHeroine> list, Handler handler) {
		try {
			int productListSize = list.size();
			for (int i = 0; i < productListSize; i++) {
				IdleBrainHeroine heroine = list.get(i);
				List<IdleBrainHeroineLink> heroineLinkList = heroine.getLinks();
				ContentValues values = new ContentValues();
				values.put("_id", heroine.getId());
				values.put("name", heroine.getName());
				dbManager.insertValues(values,
						IdleBrainApplicationConstants.TABLE_HEROINES);
				int linkListSize = heroineLinkList.size();
				for (int y = 0; y < linkListSize; y++) {
					IdleBrainHeroineLink heroineLink = heroineLinkList.get(y);
					ContentValues values2 = new ContentValues();
					values2.put("heroine_id", heroineLink.getId());
					values2.put("name", heroineLink.getName());
					values2.put("url", heroineLink.getUrl());
					dbManager
					.insertValues(
							values2,
							IdleBrainApplicationConstants.CREATE_HEROINES_LINKS_TB);
				}
				System.out.println("PRODUCT NUMBER " + (i + 1)
						+ " HAS BEEN INSERTED TO DATABSE......");
				handler.sendEmptyMessage(1);
			}
		} catch (SQLException sqlE) {
			sqlE.printStackTrace();
			return false;
		}
		return true;
	}

	private static boolean cleanDatabase(DatabaseManager dbMananger) {
		try {
			dbMananger.deleteValues(
					IdleBrainApplicationConstants.CREATE_HEROINES_LINKS_TB,
					null, null);
			dbMananger.deleteValues(
					IdleBrainApplicationConstants.CREATE_HEROINES_TB, null,
					null);

			dbMananger.deleteValues(
					IdleBrainApplicationConstants.TABLE_SQLITE_SEQUENCE, null,
					null);

			return true;
		} catch (SQLException sqlE) {
			sqlE.printStackTrace();
			return false;
		}
	}

	// public static Cursor getSelectedProductCursor(DatabaseManager dbManager,
	// int typeId, int categoryId) {
	// Cursor cursor =
	// dbManager.getValues(IdleBrainApplicationConstants.TABLE_HEROINES,
	// null, "type_id=" + typeId + " and category_id=" + categoryId
	// + " and is_primary=1", null, null, null, null);
	// // cursor.moveToFirst();
	// return cursor;
	// }

	// public static Cursor getSelectedProductCursor(DatabaseManager dbManager,
	// int typeId, int categoryId, int category2Id) {
	// Cursor cursor =
	// dbManager.getValues(IdleBrainApplicationConstants.TABLE_HEROINES,
	// null, "type_id=" + typeId + " and category_id=" + categoryId
	// + " and category2_id=" + category2Id
	// + " and is_primary=1", null, null, null, null);
	// // cursor.moveToFirst();
	// return cursor;
	// }

	// public static Cursor getSelectedHeroineCursor(DatabaseManager dbManager,
	// int typeId) {
	// Cursor cursor =
	// dbManager.getValues(IdleBrainApplicationConstants.TABLE_HEROINES,
	// null, "type_id=" + typeId + " and is_primary=1", null, null,
	// null, null);
	// // cursor.moveToFirst();
	// return cursor;
	// }

}
