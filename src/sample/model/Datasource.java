package sample.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Datasource {
	public static final Logger logger = LogManager.getLogger(DataSource.class);
	public static final String DB_NAME = "music.db";
	public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\freddy.martinez.ASI-REM-0015\\OneDrive - Andromeda Systems Inc\\Personal\\java\\DataBaseDemoFx\\" + DB_NAME;
	public static final String TABLE_ALBUMS = "albums";
	public static final String COLUMN_ALBUM_ID = "_id";
	public static final String COLUMN_ALBUM_NAME = "name";
	public static final String COLUMN_ALBUM_ARTIST = "artist";
	public static final int INDEX_ALBUM_ID = 1;
	public static final int INDEX_ALBUM_NAME = 2;
	public static final int INDEX_ALBUM_ARTIST  = 3;

	public static final String TABLE_ARTISTS = "artists";
	public static final String COLUMN_ARTISTS_ID = "_id";
	public static final String COLUMN_ARTISTS_NAME = "name";
	public static final int INDEX_ARTISTS_ID  = 1;
	public static final int INDEX_ARTISTS_NAME  = 2;

	public static final String TABLE_SONGS = "songs";
	public static final String COLUMN_SONG_ID = "_id";
	public static final String COLUMN_SONG_TRACK = "track";
	public static final String COLUMN_SONG_TITLE = "title";
	public static final String COLUMN_SONG_ALBUM = "album";
	public static final int INDEX_SONG_ID  = 1;
	public static final int INDEX_SONG_TRACK  = 2;
	public static final int INDEX_SONG_TITLE  = 3;
	public static final int INDEX_SONG_ALBUM  = 4;

	public static final int ORDER_BY_NONE = 1;
	public static final int ORDER_BY_ASC = 2;
	public static final int ORDER_BY_DESC = 3;


	public static final String QUERY_ALBUMS_BY_ARTIST_START = "SELECT " + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " FROM " +
		TABLE_ALBUMS + " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ARTISTS + "." + COLUMN_ARTISTS_ID + " = " + TABLE_ALBUMS +
		"." + COLUMN_ALBUM_ARTIST + " WHERE " + TABLE_ARTISTS + "."  + COLUMN_ARTISTS_NAME  + " = ";

	public static final String QUERY_ALBUMS_BY_ARTIST_SORT = " ORDER BY "  + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " COLLATE NOCASE ";


	//	SELECT artists.name, songs.track, albums.name FROM songs  INNER JOIN albums ON songs.album = albums._id INNER JOIN artists ON albums.artist  = artists._id WHERE songs.title = "Heartless";
	public static final String QUERY_ARTISTS_FOR_SONG_START =
		"SELECT " + TABLE_ARTISTS + "." + COLUMN_ARTISTS_NAME + ", " +
				TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + ", " +
				TABLE_SONGS + "." + COLUMN_SONG_TRACK +

		" FROM " + TABLE_SONGS + " " +
		" INNER JOIN " + TABLE_ALBUMS +
		" ON " + TABLE_SONGS + "." + COLUMN_SONG_ALBUM + " = " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ID +
		" INNER JOIN "+ TABLE_ARTISTS + " ON " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST + " = " +  TABLE_ARTISTS + "."  + COLUMN_ARTISTS_ID +
		" WHERE " + TABLE_SONGS + "." + COLUMN_SONG_TITLE + "=";

	public static final String QUERY_ARTISTS_BY_SONG_SORT = " ORDER BY "  + TABLE_ARTISTS + "." + COLUMN_ARTISTS_NAME + ", " +
		TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME +  " COLLATE NOCASE ";

	public static final String TABLE_ARTIST_SONG_VIEW = "artist_list";

	//	CREATE VIEW IF NOT EXISTS artist_list
	//	AS SELECT artists.name, albums.name AS album, songs.track , songs.title
	//	FROM songs INNER JOIN albums ON songs.album = albums._id
	//	INNER JOIN artists ON albums.artist = artists._id
	//	ORDER BY artists.name , albums.name, songs.track
	public static final String CREATE_ARTIST_FOR_SONG_VIEW = "CREATE VIEW IF NOT EXISTS " +
		TABLE_ARTIST_SONG_VIEW + " AS SELECT " + TABLE_ARTISTS + "." + COLUMN_ARTISTS_NAME + ", " +
		TABLE_ALBUMS + "." +  COLUMN_ALBUM_NAME +  " AS " + COLUMN_SONG_ALBUM + ", " +
		TABLE_SONGS + "." + COLUMN_SONG_TRACK + ", " + TABLE_SONGS + ". " + COLUMN_SONG_TITLE +
		" FROM " + TABLE_SONGS +
		" INNER JOIN " + TABLE_ALBUMS + " ON " + TABLE_SONGS +
		"." + COLUMN_SONG_ALBUM + " = " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ID +
		" INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST +
		" = " + TABLE_ARTISTS + "." + COLUMN_ARTISTS_ID +
		" ORDER BY " +
		TABLE_ARTISTS + "." + COLUMN_ARTISTS_NAME + ", " +
		TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + ", " +
		TABLE_SONGS + "." + COLUMN_SONG_TRACK;


	// SELECT name, album, track FROM artist_list WHERE title = "song title"
	public static final String QUERY_VIEW_SONG_INFO = "SELECT " + COLUMN_ARTISTS_NAME + "," + COLUMN_SONG_ALBUM + ", " + COLUMN_SONG_TRACK +
			" FROM " + TABLE_ARTIST_SONG_VIEW +
			" WHERE " + COLUMN_SONG_TITLE + " = " ;

	//"SELECT name, album, track FROM artist_list WHERE title = ? "
	public static final String QUERY_VIEW_SONG_INFO_PREP = "SELECT " + COLUMN_ARTISTS_NAME + ", " + COLUMN_SONG_ALBUM + ", " + COLUMN_SONG_TRACK
			+ " FROM "  + TABLE_ARTIST_SONG_VIEW + " WHERE " + COLUMN_SONG_TITLE + " = ?";



	public static final String INSERT_ARTIST = "INSERT INTO " + TABLE_ARTISTS +
			" (" + COLUMN_ARTISTS_NAME +  ") " + "VALUES (?)" ;
	public static final String INSERT_ALBUMS = "INSERT INTO " + TABLE_ALBUMS +
			" (" + COLUMN_ALBUM_NAME + ", " + COLUMN_ALBUM_ARTIST + ") " + " VALUES (?, ?) " ;
	public static final String INSERT_SONGS = "INSERT INTO " + TABLE_SONGS +
			" (" + COLUMN_SONG_TRACK + ", " + COLUMN_SONG_TITLE + "," +  COLUMN_SONG_ALBUM + ") " + " VALUES (?, ?, ?) " ;

	public static final String QUERY_ARTIST = "SELECT " + COLUMN_ARTISTS_ID + " FROM " + TABLE_ARTISTS + " WHERE " + COLUMN_ARTISTS_NAME + "= ? ";
	public static final String QUERY_ALBUM = "SELECT " + COLUMN_ALBUM_ID + " FROM " + TABLE_ALBUMS + " WHERE " + COLUMN_ALBUM_NAME + "= ? ";
	// TODO query_song

	private static Datasource instance = new Datasource(); // thread safe


	private Datasource() {
	}

	public static Datasource getInstance(){
		return instance;
		// Datasource.getInstance().methodName();
	}

	public static final String QUERY_ALBUMS_BY_ARTIST_ID = "SELECT * FROM " + TABLE_ALBUMS + " WHERE " + COLUMN_ALBUM_ARTIST + " = ? " +
			" ORDER BY " + COLUMN_ALBUM_NAME + " COLLATE NOCASE ";

	private Connection conn;

	private PreparedStatement querySongInfoView;
	private PreparedStatement insertIntoArtist;
	private PreparedStatement insertIntoAlbums;
	private PreparedStatement insertIntoSongs;
	private PreparedStatement queryArtist;
	private PreparedStatement queryAlbum;
	private PreparedStatement queryAlbumByArtistId;

	public boolean open(){
		logger.info("open(): start ");
		System.out.println("open(): start ");
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
			querySongInfoView = conn.prepareStatement(QUERY_VIEW_SONG_INFO_PREP);
			insertIntoArtist = conn.prepareStatement(INSERT_ARTIST, Statement.RETURN_GENERATED_KEYS);
			insertIntoAlbums = conn.prepareStatement(INSERT_ALBUMS, Statement.RETURN_GENERATED_KEYS);
			insertIntoSongs = conn.prepareStatement(INSERT_SONGS);
			queryArtist = conn.prepareStatement(QUERY_ARTIST);
			queryAlbum = conn.prepareStatement(QUERY_ALBUM);
			queryAlbumByArtistId = conn.prepareStatement(QUERY_ALBUMS_BY_ARTIST_ID);
			// todo query song


			return true;
		} catch (SQLException e) {

			logger.debug("Couldn't connect to to database: " + e.getMessage());
			return false;
		}
	}


	public void close(){
		try {
			if (conn != null) {
				conn.close();
			}

			if (insertIntoArtist != null){
				insertIntoArtist.close();
			}

			if (insertIntoAlbums != null){
				insertIntoAlbums.close();
			}

			if (insertIntoSongs != null){
				insertIntoSongs.close();
			}

			if (queryArtist != null){
				queryArtist.close();
			}

			if (queryAlbum != null){
				queryAlbum.close();
			}

			// todo query song
		} catch (SQLException e) {
			logger.debug("Couldn't close database: " + e.getMessage());
		}

	}


	// TODO: refactor to use constants like <code> queryAlbumsForArtists <code>

	/**
	 * @param sortOrder
	 * @return List<Artist2>
	 *
	 */
	public List<Artist> queryArtists(int sortOrder){
		StringBuilder sb = new StringBuilder("SELECT * FROM ");
		sb.append(TABLE_ARTISTS);
		if (sortOrder != ORDER_BY_NONE){
			sb.append(" ORDER BY ");
			sb.append(COLUMN_ARTISTS_NAME);
			sb.append(" COLLATE NOCASE ");
			if (sortOrder == ORDER_BY_DESC){
				sb.append(" DESC ");
			} else {
				sb.append(" ASC ");
			}
		}
		logger.debug("SQL Statement: " + sb.toString());
		try (Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery(sb.toString())){
			List<Artist> artists = new ArrayList<>();
			while (results.next()) {
				Artist artist = new Artist();
				artist.setId(results.getInt(INDEX_ARTISTS_ID));
				artist.setName(results.getString(INDEX_ARTISTS_NAME));
				artists.add(artist);
			}
			return artists;

		} catch (SQLException e){
			System.out.println("Query Fail: " + e.getMessage());
			return null;
		}
	}


	/**
	 * @param artistName name of artist to be queried
	 * @param sortOrder order chosen by client, ASC , DESC, NONE,
	 *                  if invalid selection is given, sort order defaults to ASC
	 * @return List<String>
	 * @throws SQLException if there was  problem with the query string
	 */
	public List<String> queryAlbumsForArtists(String artistName, int sortOrder){
		StringBuilder sb = new StringBuilder(QUERY_ALBUMS_BY_ARTIST_START);
		sb.append('"')
		.append(artistName)
		.append('"');

		setSortOrder(sortOrder, sb);


		logger.debug("SQL Statement: " + sb.toString());
		try (Statement statement = conn.createStatement();
			 ResultSet results = statement.executeQuery(sb.toString())){
			List<String> albums = new ArrayList<>();
			while (results.next()) {
				albums.add(results.getString(1));
			}
			return albums;
		} catch (SQLException e){
			System.out.println("Query Fail: " + e.getMessage());
			return null;
		}
	}




	// TODO methods for other queries, check first video of section

	private void setSortOrder(int sortOrder, StringBuilder sb) {
		if (sortOrder != ORDER_BY_NONE){
			sb.append(QUERY_ARTISTS_BY_SONG_SORT);
			if (sortOrder == ORDER_BY_DESC){
				sb.append(" DESC ");
			} else {
				sb.append(" ASC ");
			}
		}
	}


	public void querySongsMetaData(){
		String sql = "SELECT * FROM " + TABLE_SONGS;

		try (Statement statement = conn.createStatement();
				ResultSet results = statement.executeQuery(sql)){

			ResultSetMetaData meta = results.getMetaData();
			int numColumns = meta.getColumnCount();
			for (int i = 1; i <= numColumns; i++){
				System.out.format("Column %d in songs table is names %s\n",
						i, meta.getColumnName(i));
			}
		} catch (SQLException e){
			logger.error(e.getMessage());
		}
	}

	public int getCount(String table){
		String sql = "SELECT COUNT(*) AS count, MIN(_id) AS min_id FROM " + table;
		try {
			Statement statement = conn.createStatement();
			ResultSet results = statement.executeQuery(sql);
			int count = results.getInt("count");
//			int min = results.getInt("min_id"); // or 2
//			System.out.format("Count = %d, Min = %d ", count, min);
			System.out.format("Count = %d\n", count);
//			logger.info(count);
			return count;
		} catch (SQLException e){
			logger.error("Query Failed: " + e.getMessage());
			return -1;
		}
	}

	public boolean createViewForSongArtists(){
		try (Statement statement = conn.createStatement()){
			statement.execute(CREATE_ARTIST_FOR_SONG_VIEW);
			logger.info("main.view created successfully");
			return true;
		} catch (SQLException e){
			logger.error("Could not create main.view: " + e.getMessage());
			return false;
		}
	}




	// SELECT * FROM artist_list WHERE title = "Go Your Own Way"
	// without prepared statement
//	public List<SongArtist> querySongInfoView(String title){
//		StringBuilder sb = new StringBuilder(QUERY_VIEW_SONG_INFO);
//		sb.append('"');
//		sb.append(title);
//		sb.append('"');
//		logger.info(sb);
//
//		try (Statement statement = conn.createStatement();
//			 ResultSet results = statement.executeQuery(sb.toString())){
//
//			List<SongArtist> songArtists = new ArrayList<>();
//			while(results.next()) {
//				SongArtist songArtist = new SongArtist();
//				songArtist.setArtistName(results.getString(1));
//				songArtist.setAlbumName(results.getString(2));
//				songArtist.setTrack(results.getInt(3));
//
//				songArtists.add(songArtist);
//
//
//			}
//			return songArtists;
//		} catch (SQLException e){
//			logger.error("Could not create main.view: " + e.getMessage());
//			return null;
//		}
//
//	}


//	public boolean insertArtist(String artist){
//		try {
//
//			insertIntoArtist.setString(1, artist);
//			insertIntoArtist.execute();
//			logger.info("insert '" + artist + "' successful. ");
//			return true;
//		} catch (Exception e){
//			logger.error(e.getMessage());
//			return false;
//
//		}
//
//	}


	private int insertArtist(String name) throws SQLException{
		queryArtist.setString(1, name);
		ResultSet results = queryArtist.executeQuery();
		if (results.next()) {
			logger.info("Artist2 already exists. ");
			return results.getInt(1);
		} else {
			// insert artist
			insertIntoArtist.setString(1, name);
			int affectedRows = insertIntoArtist.executeUpdate();

			if (affectedRows != 1) {
				throw new SQLException("Couldn't insert artist");

			}

			ResultSet generatedKeys = insertIntoArtist.getGeneratedKeys();
			if (generatedKeys.next()) {
				return generatedKeys.getInt(1);
			} else {
				throw new SQLException("Couldn't get _id for artist");

			}


		}
	}

	private int insertAlbum(String name, int artistId) throws SQLException{
		queryAlbum.setString(1, name);
		ResultSet results = queryAlbum.executeQuery();
		if (results.next()) {
			logger.info("Album already exists. ");
			return results.getInt(1);
		} else {
			// insert album
			insertIntoAlbums.setString(1, name);
			insertIntoAlbums.setInt(2, artistId);
			int affectedRows = insertIntoAlbums.executeUpdate();

			if (affectedRows != 1) {
				throw new SQLException("Couldn't insert album");

			}

			ResultSet generatedKeys = insertIntoAlbums.getGeneratedKeys();
			if (generatedKeys.next()) {
				return generatedKeys.getInt(1);
			} else {
				throw new SQLException("Couldn't get _id for album");

			}


		}
	}


	public  void insertSong(String title, String artist, String album, int track) {
		try {
			conn.setAutoCommit(false);


			int artistId = insertArtist(artist);
			int albumId = insertAlbum(album, artistId);
			insertIntoSongs.setInt(1, track);
			insertIntoSongs.setString(2, title);
			insertIntoSongs.setInt(3, albumId);
			int affectedRows = insertIntoSongs.executeUpdate();

			if (affectedRows == 1) {

				conn.commit();
			} else {
				logger.info("Song already exists. ");
				throw new SQLException("Couldn't get _id for album");
			}
		} catch (SQLException | ArrayIndexOutOfBoundsException e){ // can also be Exception e
			System.out.println("Insert song exception " + e.getMessage());
			try {
				System.out.println("Performing rollback");
				conn.rollback();
			} catch (Exception ex){
				System.out.println("Really bad error "  + ex.getMessage());
			}
		} finally {
			try {

				System.out.println("Resetting default commit behavior");
				conn.setAutoCommit(true);
			} catch (SQLException ex){
				System.out.println("Couldn't reset auto commit");
			}
		}


		}
	}
