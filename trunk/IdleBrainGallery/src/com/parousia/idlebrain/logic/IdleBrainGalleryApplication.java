package com.parousia.idlebrain.logic;

import android.app.Application;

import com.parousia.idelbrain.db.IdleBrainDatabaseManager;

public class IdleBrainGalleryApplication extends Application {

	private IdleBrainDatabaseManager dbManager;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		setDbManager(new IdleBrainDatabaseManager(this));

	}

	public IdleBrainDatabaseManager getDbManager() {
		return dbManager;
	}

	public void setDbManager(IdleBrainDatabaseManager dbManager) {
		this.dbManager = dbManager;
	}
}
