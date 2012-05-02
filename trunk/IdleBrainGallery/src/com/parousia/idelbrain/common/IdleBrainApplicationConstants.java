package com.parousia.idelbrain.common;

public class IdleBrainApplicationConstants {

	public static final String LOGTAG = "IdlebrainLOG";
	
	/**DB Stuff **/
	public static final String UPDATE_PROGRESS = "Updating database....";
	public static final String TABLE_SQLITE_SEQUENCE = "sqlite_sequence";
	public static final String TABLE_HEROINES = "heroines";
	public static final String TABLE_HEROINES_LINKS = "heroine_links";

	public static final String CREATE_HEROINES_TB = "create table IF NOT EXISTS "
			+ TABLE_HEROINES
			+ "("
			+ "_id integer,"
			+ "   name  text not null,"
			+ "   PRIMARY KEY (_id))";

	public static final String CREATE_HEROINES_LINKS_TB = "create table IF NOT EXISTS "
			+ TABLE_HEROINES_LINKS
			+ "("
			+ "_id integer primary key autoincrement, "
			+ "   heroine_id   integer not null,"
			+ "   name  text not null,"
			+ "   link  text not null,"
			+ "   FOREIGN KEY(heroine_id) REFERENCES TABLE_HEROINES(_id))";

}
