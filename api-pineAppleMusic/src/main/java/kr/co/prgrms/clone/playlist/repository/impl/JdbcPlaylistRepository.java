package kr.co.prgrms.clone.playlist.repository.impl;

import kr.co.prgrms.clone.playlist.domain.Playlist;
import kr.co.prgrms.clone.playlist.exception.impl.PlaylistCreateException;
import kr.co.prgrms.clone.playlist.exception.impl.PlaylistNotFoundException;
import kr.co.prgrms.clone.playlist.repository.PlaylistRepository;
import kr.co.prgrms.clone.song.domain.Song;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static kr.co.prgrms.clone.global.error.ErrorCode.NOT_ADD_IN_PLAYLIST;
import static kr.co.prgrms.clone.global.error.ErrorCode.NOT_CREATE_PLAYLIST;
import static kr.co.prgrms.clone.global.error.ErrorCode.NOT_FOUND_PLAYLIST;

@Repository
public class JdbcPlaylistRepository implements PlaylistRepository {
    private static final String INSERT_NEW_PLAYLIST
            = "INSERT INTO playlists (user_id, title) VALUES (:userId, :title)";
    private static final String FIND_BY_USER_ID
            = "SELECT * FROM playlists WHERE user_id = :userId";
    private static final String INSERT_NEW_SONG
            = "INSERT INTO songs_playlists (song_id, playlist_id) VALUES (:songId, :playlistId)";
    private static final String FIND_ALL_BY_ID = "SELECT * FROM songs_playlists AS A JOIN songs AS B " +
            "ON A.song_id = B.id WHERE A.playlist_id = :playlistId";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcPlaylistRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createPlaylist(Integer userId, String title) {
        var paramMap
                = Map.of("userId", userId, "title", title);
        try {
            jdbcTemplate.update(INSERT_NEW_PLAYLIST, paramMap);
        } catch (DataAccessException e) {
            throw new PlaylistCreateException(NOT_CREATE_PLAYLIST);
        }
    }

    @Override
    public List<Playlist> findByUserId(Integer userId) {
        var paramMap = Collections.singletonMap("userId", userId);
        try {
            return jdbcTemplate.query(FIND_BY_USER_ID, paramMap, playlistRowMapper);
        } catch (DataAccessException e) {
            throw new PlaylistNotFoundException(NOT_FOUND_PLAYLIST);
        }
    }

    @Override
    public void addNewSong(Integer songId, Integer playlistId) {
        var paramMap
                = Map.of("songId", songId, "playlistId", playlistId);
        try {
            jdbcTemplate.update(INSERT_NEW_SONG, paramMap);
        } catch (DataAccessException e) {
            throw new PlaylistCreateException(NOT_ADD_IN_PLAYLIST);
        }
    }

    @Override
    public List<Song> findAllSongById(Integer playlistId) {
        var paramMap = Collections.singletonMap("playlistId", playlistId);
        try {
            return jdbcTemplate.query(FIND_ALL_BY_ID, paramMap, songRowMapper);
        } catch (DataAccessException e) {
            throw new PlaylistNotFoundException(NOT_FOUND_PLAYLIST);
        }
    }

    RowMapper<Playlist> playlistRowMapper = (rs, num) -> {
        int id = rs.getInt("id");
        int userId = rs.getInt("user_id");
        String title = rs.getString("title");

        return new Playlist(id, userId, title);
    };

    RowMapper<Song> songRowMapper = (rs, num) -> {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        int genreId = rs.getInt("genre_id");
        float playTime = rs.getFloat("play_time");
        String lyrics = rs.getString("lyrics");
        String url = rs.getString("url");
        int price = rs.getInt("price");

        return new Song(id, title, genreId,
                playTime, lyrics, url, price);
    };
}
